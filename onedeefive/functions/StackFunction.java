package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Functions that remove items from the stack (pop, clear)
*/
public class StackFunction extends SystemFunction implements LangDef
{
    @Override
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        if(Pattern.matches(POP_STRING,cmd))
        {
            checkStackHeight(stack,1,cmd);
            stack.pop();
            
        }else if(Pattern.matches(CLEAR_STRING,cmd))
        {
            stack.clear();
        }else
        {
            throw new InputMismatchException(
                "ERROR: " + cmd + " is an invalid stack operation."
            );
        }
    }
    
    @Override
    public String matchString()
    {
        return STACK_STRING;
    }
}
