package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Functions for rolling dice (xDy, dice)
*/
public class RollFunction extends SystemFunction implements LangDef
{
    @Override
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        int count = 0;
        int sides = 0;
        String desc = "";
        
        if(Pattern.matches(DICEROLL_STRING,cmd))
        {
            String[] split = cmd.split(DIE_SEPARATOR);
            count = Integer.parseInt(split[0]);
            sides = Integer.parseInt(split[1]);
        }else if(Pattern.matches(DICE_STRING,cmd))
        {
            checkStackHeight(stack,2,cmd);
            sides = stack.pop().value();
            count = stack.pop().value();
            
            if(sides < 1)
            {
                throw new InputMismatchException(
                    "A die can't have fewer than 1 side!"
                );
            }
            if(count < 0)
            {
                throw new InputMismatchException(
                    "Can't roll a negative number of dice!!"
                );
            }
        }else
        {
            throw new InputMismatchException(
                "ERROR: " + cmd + " is not a valid way to roll dice"
            );
        }
        
        ArrayList<Integer> ret = new ArrayList<Integer>();
        int total = 0;
        desc += count + "D" + sides + " (";
        
        for(int i = 0; i < count; i++)
        {
            int val = (int)Math.ceil(Math.random()*sides);
            ret.add(val);
            total += val;
            
            desc += "" + val;
            if(i < count - 1)
            {
                desc += ", ";
            }
        }
        
        if(count == 0)
        {
            ret.add(0);
            desc += "0";
        }
        
        desc += "): " + total;
        
        stack.push(new StackFrame(desc, ret));
    }
    
    @Override
    public String matchString()
    {
        return ROLL_STRING;
    }
    
}
