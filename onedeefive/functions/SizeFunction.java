package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class SizeFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        checkStackHeight(stack,1,cmd);
        
        StackFrame tmp = stack.pop();
        
        String desc = "";
        int size = 0;
        
        if(tmp.isRoll())
        {
            desc = tmp.explain() + " SIZE";
            size = tmp.rolls().size();
        }else
        {
            desc = tmp.value() + " SIZE";
            size = 1;
        }
        
        stack.push(new StackFrame(desc,size));
    }
    
    public String matchString()
    {
        return SIZE_STRING;
    }
}
