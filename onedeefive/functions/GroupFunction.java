package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Function for grouping multiple values into one list (group)
*/
public class GroupFunction extends SystemFunction implements LangDef
{
    @Override
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        checkStackHeight(stack,1,cmd);
        
        int numGroups = stack.pop().value();
        
        checkStackHeight(stack,numGroups,numGroups + " " + cmd);
        
        ArrayList<Integer> ret = new ArrayList<Integer>();
        String desc = "(";
        
        while(numGroups > 0)
        {
            StackFrame tmp = stack.pop();
            if(tmp.isRoll())
            {
                ret.addAll(tmp.rolls());
            }else
            {
                ret.add(tmp.value());
            }
            numGroups--;
        }
        
        if(ret.size() == 0)
        {
            ret.add(0);
        }
        
        int sum = 0;
        for(int i = 0; i < ret.size(); i++)
        {
            sum += ret.get(i);
            desc += "" + ret.get(i);
            if(i < ret.size()-1)
            {
                desc += ", ";
            }
        }
        
        desc += "): " + sum;
        
        stack.push(new StackFrame(desc,ret));
    }
    
    @Override
    public String matchString()
    {
        return GROUP_STRING;
    }
}
