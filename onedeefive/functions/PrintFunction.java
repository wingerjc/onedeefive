package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Functions for printing stack values to the output stream.
*/
public class PrintFunction extends SystemFunction implements LangDef
{
    @Override
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        if(Pattern.matches(OUT_STRING,cmd))
        {
            checkStackHeight(stack,1,cmd);
            Script.output().print(" " + stack.pop().value());
        }else if(Pattern.matches(PEEK_STRING,cmd))
        {
            checkStackHeight(stack,1,cmd);
            Script.output().print(" " + stack.peek().value());
        }else if(Pattern.matches(ENDL_STRING,cmd))
        {
            Script.output().println();
        }else
        if(Pattern.matches(EXPLAIN_STRING,cmd))
        {
            checkStackHeight(stack,1,cmd);
            Script.output().print(" " + stack.pop().explain());
        }else
        {
            throw new InputMismatchException(
                "ERROR: " + cmd + " is not a valid output command"
            );
        }
    }
    
    @Override
    public String matchString()
    {
        return OUTPUT_STRING;
    }
}
