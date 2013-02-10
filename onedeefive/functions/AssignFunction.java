package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class AssignFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        checkStackHeight(stack,2,cmd);
        
        String name = stack.pop().explain();
        
        if(!Pattern.matches(VARIABLE_STRING,name))
        {
            throw new InputMismatchException(
                "ERROR: " + name + " is not a variable and cannot be "+
                "assigned to."
            );
        }
        
        StackFrame tmp = stack.pop();
        StackFrame toAdd = null;
        
        
        if(tmp.isRoll())
        {
            toAdd = new StackFrame(name + " ASSIGNED", tmp.rolls());
        }else
        {
            toAdd = new StackFrame(name + " ASSIGNED", tmp.value());
        }
        
        vars.put(name,toAdd);
        if(Pattern.matches(ASSIGN_STRING,cmd))
        {
            stack.push(toAdd);
        }
    }
    
    public String matchString()
    {
        return ASSIGN_STRING + "|" + SHOVE_STRING;
    }
}
