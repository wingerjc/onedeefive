package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class VariableFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        StackFrame toAdd = null;
        if(vars.keySet().contains(cmd))
        {
            StackFrame tmp = vars.get(cmd);
            if(tmp.isRoll())
            {
                toAdd = new StackFrame(cmd,tmp.rolls());
            }else
            {
                toAdd = new StackFrame(cmd,tmp.value());
            }
        }else
        {
            toAdd = new StackFrame(cmd,0);
        }
        
        stack.push(toAdd);
    }
    
    public String matchString()
    {
        return VARIABLE_STRING;
    }
}
