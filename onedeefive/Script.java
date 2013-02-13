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
    private String[] _script;
    
    
/* +++++++++++++++   CONSTRUCTORS +++++++++++++++++++++ */
    public Script(String script)
    {
        this(script,false);
    }
    
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
    public Stack<StackFrame> execute()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        execute(stack, new HashMap<String,StackFrame>());
        return stack;
    }
    
    // for executing functions, etc. that get new scope
    public void execute(Stack<StackFrame> stack)
    {
        execute(stack, new HashMap<String,StackFrame>());
    }
    
    // execute the current script, give it the scope
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
    
    private boolean processTokens(
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
        
        return i > 0; // processed at least one token
    }
    
    public void makeFunction(String name)
    {
        _userFunctions.put(name,this);
    }
    
    public Script subscript(int start, int end)
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
    
    public static void addSystemFunction(SystemFunction func)
    {
        if(func != null)
        {
            _systemFunctions.add(func);
        }
    }
    
    public static void addControlStructure(ControlStructure struct)
    {
        if(struct != null)
        {
            _controlStructures.add(struct);
        }
    }
    
    public static PrintStream output()
    {
        checkOutput();
        return STD_OUT;
    }
    
    private static void checkOutput()
    {
        if(STD_OUT == null)
        {
            STD_OUT = System.out;
        }
    }
    
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
