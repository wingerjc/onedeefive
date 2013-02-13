package onedeefive;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import onedeefive.functions.*;
import onedeefive.structures.*;

/** This clas represents the heart of 1D5. 
*/
public class Script implements LangDef
{
/* +++++++++++++++   STATIC MEMBERS +++++++++++++++++++++ */
    /** Internal list of available system functions. */
    private static List<SystemFunction> _systemFunctions = null;
    
    /** Internal list of available control structures. */
    private static List<ControlStructure> _controlStructures = null;
    
    /** Internal map of user defined functions */
    private static Map<String,Script> _userFunctions = null;
    
    /** internal output stream for all scripts */
    private static PrintStream STD_OUT = null;
    
    
/* +++++++++++++++   INSTANCE MEMBERS +++++++++++++++++++++ */
    /** The internal list of strings that make up a script.
    * Each string can be a ControlStructure statement or a list of function
    * calls.
    */
    private String[] _script;
    
    
/* +++++++++++++++   CONSTRUCTORS +++++++++++++++++++++ */
    /** Create a new script directly from an input string.
    * This constructor is equivalent to Script(script, false).
    *
    * @param script
    *        The string that makes up the script.
    */
    public Script(String script)
    {
        this(script,false);
    }
    
    /** Create a new script. If 'file' is true, input is treated as a file name
    * and shte script is read from that file. If 'file' is false, input is
    * treated as the script text.
    *
    * @param input
    *        Either an input file to read a script from, or a script.
    * @param file
    *        Wheter 'input' is a file name (true), or a script (false)
    */
    public Script(String input, boolean file)
    {
        // make sure we have all the system functions
        if(_systemFunctions == null)
        {
            instantiateSystem();
        }
        
        if(file)
        {
            // read the script from a file ...
            Scanner scan = null;
            
            try
            {
                scan = new Scanner(new File(input));
            }catch(IOException ioe)
            {
                throw new IllegalArgumentException(
                    "Could not open file: " + input +
                    "\n" + ioe.getMessage() + "\n" +
                    ioe.getStackTrace()
                );
            }
            
            StringBuilder sb = new StringBuilder();
            while(scan.hasNextLine())
            {
                sb.append(scan.nextLine());
                sb.append("\n");
            }
            
            input = sb.toString();
            scan.close();
        }
        
        // clean the script a little
        // remove extra white space
        _script = input.split(ENDLINE_STRING);
        
        // remove comments
        for(int i = 0; i < _script.length; i++)
        {
            _script[i] = _script[i].split(COMMENT_STRING)[0];
            if(Pattern.matches(PRINT_STRING,_script[i].trim()))
            {
                _script[i] = _script[i].replaceFirst("[ \t]*","");
            }else
            {
                _script[i] = _script[i].replaceAll("[ \t]+",TOKEN_SEPARATOR).trim();
            }
        }
    }
    
/* +++++++++++++++   INSTANCE METHODS +++++++++++++++++++++ */
    /** Execute this script using a new system Stack and return a reference to
    * the stack used. A nes variable mapping context/scope is created for this
    * script execution.
    *
    * @return The stack after the completion of the script
    */
    public Stack<StackFrame> execute()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        execute(stack, new HashMap<String,StackFrame>());
        return stack;
    }
    
    /** Execute thie script with the given Stack and a new variable
    * scope/context.
    *
    * @param stack
    *        The system stack to use.
    */
    public void execute(Stack<StackFrame> stack)
    {
        execute(stack, new HashMap<String,StackFrame>());
    }
    
    /** Execute this script using the given Stack and variable context/scope.
    *
    * @param stack
    *        The stack that this script uses for passing values to functions.
    * @param context
    *        The context/scope that matches variable names to values for this
    *        script execution.
    */
    public void execute(Stack<StackFrame> stack, Map<String,StackFrame> context)
    {
        int i;
        for(i = 0; i < _script.length; i++)
        {
            boolean processed = false;
            
            // try to process if/repeat/defun/include/etc.
            for(ControlStructure cs : _controlStructures)
            {
                if(!processed && Pattern.matches(cs.startPattern(),_script[i]))
                {
                    processed= true;
                    int end = cs.scriptEnd(_script,i);
                    Script tmp = subscript(i,end);
                    cs.execute(tmp, _script[i], stack, context);
                    i = end;
                }
            }
            
            try
            {
                // must be an expression, process it
                if(!processed && _script[i].length() > 0)
                {
                    processTokens(_script[i],stack,context);
                }
            }catch(Exception e)
            {
                System.err.println(
                    "Oh no! I had an error running: \n\t" +
                    _script[i] + "\nThe error is: "
                );
                
                System.err.println(e.getMessage());
                System.err.println("Attempting to continue," +
                                   "results may be garbage!");
            }
        }
    }
    
    /** Process an individual line of a script as a list of tokens. Tokens can
    * either be system functions of user defined functions.
    *
    * @param script
    *        The script line to process
    * @param stack
    *        The current program stack
    * @param context
    *        The current name/variable mapping for the script's scope.
    */
    private void processTokens(
        String script,
        Stack<StackFrame> stack,
        Map<String,StackFrame> context
    )
    {
        String[] tokens = script.split(TOKEN_SEPARATOR);
        int i;
        boolean processed;
        
        for(i = 0; i < tokens.length; i++)
        {
            processed = false;
            
            // try to run system function on token
            for(SystemFunction func : _systemFunctions)
            {
                if(!processed && Pattern.matches(func.matchString(),tokens[i]))
                {
                    processed = true;
                    func.execute(tokens[i],stack,context);
                }
            }
            
            // run a user function
            if(!processed && _userFunctions.keySet().contains(tokens[i]))
            {
                _userFunctions.get(tokens[i]).execute(stack);
                processed = true;
            }
            
            if(!processed)
            {
                throw new IllegalArgumentException(
                    "ERROR: \"" + tokens[i] + "\" is not a valid keyword, "
                    + "variable name, or defined user function."
                );
            }
        }
    }
    
    /** Define this script as a user function named 'name'.
    *
    * @param name
    *        The name that this script is referred to as a user defined function.
    */
    public void makeFunction(String name)
    {
        _userFunctions.put(name,this);
    }
    
    /** Create a script that spans the lines start (inclusive) to end
    * (exclusive) of this script.
    *
    * @param start
    *        The starting index of the subscript, inclusive
    * @param end
    *        The index of the last line of the subscript, exclusive
    * @return The new script based on lines [start,end)
    */
    private Script subscript(int start, int end)
    {
        Script ret = new Script("");
        
        if(end > start + 1)
        {
            start++;
            ret._script = new String[end-start];
            for(int i = start; i < end; i++)
            {
                ret._script[i-start] = _script[i];
            }
        }
        
        return ret;
    }
    
/* +++++++++++++++   STATIC METHODS +++++++++++++++++++++ */
    /** Load all the standard SystemFunction and ControlStructure objects.
    */
    private static void instantiateSystem()
    {
        _systemFunctions = new ArrayList<SystemFunction>();
        // standard system functions
        _systemFunctions.add(new IntegerFunction());
        _systemFunctions.add(new MathFunction());
        _systemFunctions.add(new CompareFunction());
        _systemFunctions.add(new BooleanFunction());
        _systemFunctions.add(new RollFunction());
        _systemFunctions.add(new GroupFunction());
        _systemFunctions.add(new MinMaxFunction());
        _systemFunctions.add(new SizeFunction());
        _systemFunctions.add(new VariableFunction());
        _systemFunctions.add(new AssignFunction());
        _systemFunctions.add(new DupFunction());
        _systemFunctions.add(new StackFunction());
        _systemFunctions.add(new PrintFunction());
        
        _controlStructures = new ArrayList<ControlStructure>();
        // standard control structures
        _controlStructures.add(new PrintStructure());
        _controlStructures.add(new LoopStructure());
        _controlStructures.add(new IfStructure());
        _controlStructures.add(new FunctionStructure());
        _controlStructures.add(new IncludeStructure());
        
        _userFunctions = new HashMap<String,Script>();
    }
    
    /** Add a new SystemFunction to the list of available SystemFunctions.
    *
    * @param func
    *        The new functino to add, null will not be added to the list.
    */
    public static void addSystemFunction(SystemFunction func)
    {
        if(func != null)
        {
            _systemFunctions.add(func);
        }
    }
    
    /** Add a new ControlStructure to the list of available ControlStructures.
    *
    * @param struct
    *        The new structure to add, null will not be added to the list
    */
    public static void addControlStructure(ControlStructure struct)
    {
        if(struct != null)
        {
            _controlStructures.add(struct);
        }
    }
    
    /** Access the output stream currently in use by the Script class.
    *
    * @return The current Script output stream.
    */
    public static PrintStream output()
    {
        checkOutput();
        return STD_OUT;
    }
    
    /** On initialization of functions and structures, make sure that the 
    * output is not null. (if so, assign to System.out)
    */
    private static void checkOutput()
    {
        if(STD_OUT == null)
        {
            STD_OUT = System.out;
        }
    }
    
    /** Set the output of the Script class to the specified output stream.
    * You assume responsibility for closing this stream if reqquired.
    */
    public static boolean setOutput(PrintStream out)
    {
        if(out == null || out.checkError())
        {
            return false;
        }
        
        STD_OUT = out;
        return true;
    }
}
