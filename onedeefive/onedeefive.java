package onedeefive;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class onedeefive
{
    private static final String START_READ = "(?i)script(?i)";
    private static final String END_READ = "(?i)end script(?i)";
    private static final String EXIT_STRING = "(?i)exit(?i)";
    
    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            consoleMode();
        }else
        {
            // do file i/o and stuff based on input args
            
        }
    }
    
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
                    script.execute(stack, context);
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
                    script.execute(stack,context);
                }
            }
        }
    }
}
