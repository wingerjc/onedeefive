package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class BooleanFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        // deal with the single argument "not"
        if(Pattern.matches(NOT_STRING,cmd))
        {
            checkStackHeight(stack,1,cmd);
            int tmp = stack.pop().value();
            stack.push(new StackFrame(tmp + " " + cmd, (tmp == 0)? 1 : 0));
            return;
        }
        
        // now deal with the 2 arg boolean ops
        checkStackHeight(stack,2,cmd);
        
        int arg1 = stack.pop().value();
        int arg2 = stack.pop().value();
        int ret = 0;
        String desc = "";
        
        if(Pattern.matches(AND_STRING,cmd))
        {
            ret = ((arg1 == 0)? 0 : 1) & ((arg2 == 0)? 0 : 1);
            desc = arg2 + " " + arg1 + cmd;
        }else if(Pattern.matches(OR_STRING,cmd))
        {
            ret = ((arg1 == 0)? 0 : 1) | ((arg2 == 0)? 0 : 1);
            desc = arg2 + " " + arg1 + cmd;
        }else if(Pattern.matches(XOR_STRING,cmd))
        {
            ret = ((arg1 == 0)? 0 : 1) ^ ((arg2 == 0)? 0 : 1);
            desc = arg2 + " " + arg1 + cmd;
        }else
        {
            throw new InputMismatchException(
                "ERROR: " + cmd + " is NOT a valid boolean operation"
            );
        }
        
        stack.push(new StackFrame(desc,ret));
    }
    
    public String matchString()
    {
        return BOOLEAN_STRING;
    }
}
