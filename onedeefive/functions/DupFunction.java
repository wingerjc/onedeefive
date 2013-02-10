package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class DupFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        checkStackHeight(stack,1,cmd);
        
        StackFrame tmp = stack.peek();
        StackFrame toAdd = null;
        
        if(tmp.isRoll())
        {
            toAdd = new StackFrame(tmp.explain() + " DUP", tmp.rolls());
        }else
        {
            toAdd = new StackFrame(tmp.value() + " DUP",tmp.value());
        }
        
        stack.push(toAdd);
    }
    
    public String matchString()
    {
        return DUP_STRING;
    }
}
