package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class MinMaxFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        checkStackHeight(stack,2,cmd);
        
        boolean min = Pattern.matches(MIN_STRING,cmd);
        int count = stack.pop().value();
        
        StackFrame tmp =  stack.pop();
        ArrayList<Integer> values = new ArrayList<Integer>();
        ArrayList<Integer> ret = new ArrayList<Integer>();
        
        String desc = "";
        
        if(!tmp.isRoll())
        {
            ret.add((count <= 0)? 0 : tmp.value());
            desc += "(" + ret.get(0) + "): " + ret.get(0);
        }else
        {
            values.addAll(tmp.rolls());
            Collections.sort(values);
            
            count = Math.min(Math.max(count,0),values.size());
            
            int i;
            int start = (min)? 0 : values.size()-1;
            int dir = (min)? 1 : -1;
            int end = start + (dir*count);
            int last = (min)? values.size() : -1;
            
            int sum = 0;
            desc += "(";
            for(i = start; i != end; i += dir)
            {
                int next = values.get(i);
                desc += "" + next;
                if(i != end - dir)
                {
                    desc += ", ";
                }
                sum += next;
                ret.add(next);
            }
            
            if(ret.size() == 0)
            {
                ret.add(0);
                desc += "0";
            }
            
            desc += ") [";
            
            for(; i != last; i += dir)
            {
                desc += "" + values.get(i) + ((i != last - dir)? ", " : "");
            }
            
            desc += "]: " + sum;
        }
        
        stack.push(new StackFrame(desc,ret));
    }
    
    public String matchString()
    {
        return "(" + MIN_STRING + ")|(" + MAX_STRING + ")";
    }
}

