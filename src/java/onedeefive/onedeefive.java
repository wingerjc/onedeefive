package onedeefive;

import java.util.*;
import java.util.regex.*;
import java.io.*;

/** This program houses the Main function for onedeefive the scriptable
 * dice rolling program. This class allows for 1D5 to be run from the command
 * line as an interactive scripting environment.
 *
 * The program can also be run with specified input and output files.
*/
public class onedeefive
{
    /** Pattern for the start of a multi-liine script. **/
    private static final String START_READ = "(?i)script(?i)";
    
    /** Pattern for the end of a multi-line script. **/
    private static final String END_READ = "(?i)end script(?i)";
    
    /** Pattern for the string to exit interactive mode. **/
    private static final String EXIT_STRING = "(?i)exit(?i)";
    
    /** Version string: major.minor.bugfix/MMYY **/
    private static final String VERSION = "1.0.0/0213";
    
    /** The basic command line program.
    */
    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            consoleMode();
        }else
        {
            int i = 0;
            File infile = null;
            File outfile = null;
            for(i = 0; i < args.length; i++)
            {
                if(args[i].equalsIgnoreCase("-i"))
                {
                    ++i;
                    infile = new File(args[i]);
                    if(!infile.exists())
                    {
                        System.err.println(
                            "ERROR Input file does not exist: " +
                            infile.getPath());
                        System.exit(1);
                    }
                    if(!infile.canRead())
                    {
                        System.err.println(
                            "ERROR Input file canot be read from: " +
                            infile.getPath());
                        System.exit(1);
                    }
                }else if(args[i].equalsIgnoreCase("-o"))
                {
                    ++i;
                    outfile = new File(args[i]);
                    
                    if(outfile.exists() && !outfile.canWrite())
                    {
                        System.err.println(
                            "ERROR Output file canot be written to: " +
                            outfile.getPath());
                        System.exit(1);
                    }
                }else if(args[i].equalsIgnoreCase("--help"))
                {
                    System.out.println("1D5 Version: " + VERSION + "\n" +
                    "The scriptable dice roller.\n"+
                    "Usage: java -jar onedeefive.jar [options]\n\n"+
                    "Options:\n"+
                    "-i <file name>\n"+
                    "\tRead input from the specified file.\n\n"+
                    "-o <file name>\n"+
                    "\twrite output to the specified file.\n\n"+
                    "--help\n"+
                    "\tPrint this message."
                    );
                    System.exit(0);
                }
            }
                
            // setup output
            if(outfile != null)
            {
                try
                {
                    PrintStream ostream = new PrintStream(outfile);
                    Script.setOutput(ostream);
                }catch(IOException ioe)
                {
                    System.err.println("ERROR Could not open output file:");
                    ioe.printStackTrace(System.err);
                    System.exit(1);
                }
            }
            
            // decid input method
            if(infile == null)
            {
                consoleMode();
            }else
            {
                try
                {
                    Script scr = new Script(infile.getPath(),true);
                    scr.execute(new Stack<StackFrame>());
                }catch(Exception e)
                {
                    System.err.println("ERROR Could not open input file:");
                    e.printStackTrace(System.err);
                    System.exit(1);
                }
            }
        }
    }
    
    /** consoleMode() runs the program as an interactive scripting
     * environment that takes input from System.in and print all output
     * to Script.output() (usually system.out). 
     *
     * All errors are printed to System.err, and the script tries to continue.
     * The script will not continue after a n invalid INCLUDE statement, but
     * the console will return.
    **/
    public static void consoleMode()
    {
        Scanner scan = new Scanner(System.in);
        
        String command = "";
        boolean reading = false;
        Script script;
        Stack<StackFrame> stack = new Stack<StackFrame>();
        Map<String,StackFrame> context = new HashMap<String,StackFrame>();
        
        while(true)
        {
            if(reading)
            {
                String tmp = scan.nextLine();
                if(Pattern.matches(END_READ,tmp.trim()))
                {
                    script = new Script(command);
                    try
                    {
                        script.execute(stack, context);
                    }catch(Exception e)
                    {
                        System.err.println(
                            "The script had an error. It may not have\n" +
                            "finished and so you may get bad results after this.\n" +
                            "All I know is: " + e.getMessage() 
                        );
                    }
                    reading = false;
                }else
                {
                    command += tmp + "\n";
                }
            }else
            {
                command = scan.nextLine();
                if(Pattern.matches(START_READ,command.trim()))
                {
                    // read in a multiline command
                    command = "";
                    reading = true;
                }else if(Pattern.matches(EXIT_STRING,command.trim()))
                {
                    // exit the program
                    break;
                }else // try to execute straight away!
                {
                    script = new Script(command);
                    try
                    {
                        script.execute(stack,context);
                    }catch(Exception e)
                    {
                        System.err.println(
                            "The script had an error. It may not have\n" +
                            "finished and so you may get bad results after this.\n" +
                            "All I know is: " + e.getMessage() 
                        );
                    }
                }
            }
        }
    }
}
