package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Integer comparison functions. (&lt;, &gt;, =, !=, &lt;=, &gt;=)
*/
public class CompareFunction extends SystemFunction implements LangDef
{
    @Override
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        checkStackHeight(stack,2,cmd);
        
        int arg1 = stack.pop().value();
        int arg2 = stack.pop().value();
        int ret = 0;
        String desc = "";
        
        if(Pattern.matches(LT_STRING,cmd))
        {
            ret = (arg2 < arg1)? 1 : 0;
            desc = arg2 + "<" + arg1;
        }else if(Pattern.matches(GT_STRING,cmd))
        {
            ret = (arg2 > arg1)? 1 : 0;
            desc = arg2 + ">" + arg1;
        }else if(Pattern.matches(EQ_STRING,cmd))
        {
            ret = (arg2 == arg1)? 1 : 0;
            desc = arg2 + "==" + arg1;
        }else if(Pattern.matches(LTE_STRING,cmd))
        {
            ret = (arg2 <= arg1)? 1 : 0;
            desc = arg2 + "<=" + arg1;
        }else if(Pattern.matches(GTE_STRING,cmd))
        {
            ret = (arg2 >= arg1)? 1 : 0;
            desc = arg2 + ">=" + arg1;
        }else if(Pattern.matches(NEQ_STRING,cmd))
        {
            ret = (arg2 != arg1)? 1 : 0;
            desc = arg2 + "!=" + arg1;
        }else
        {
            throw new InputMismatchException(
                "ERROR: \"" + cmd + "\" is not a known comparison function"
            );
        }
        
        stack.push(new StackFrame(desc,ret));
    }
    
    @Override
    public String matchString()
    {
        return COMPARE_STRING;
    }
}
 
