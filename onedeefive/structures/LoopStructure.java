package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class LoopStructure extends ControlStructure implements LangDef
{
    public void execute(
        Script script,
        String initial,
        Stack<StackFrame> stack,
        Map<String,StackFrame> context
    )
    {
        if(stack.size() < 1)
        {
            throw new IndexOutOfBoundsException(
                "ERROR: Repeat must have repeat length on stack, none found."
            );
        }
        
        int times = stack.pop().value();
        
        for(int i = 0; i < times; i++)
        {
            script.execute(stack,context);
        }
    }
    
    public String startPattern()
    {
        return LOOP_STRING;
    }
    
    public String startName()
    {
        return "repeat";
    }
    
    public String endPattern()
    {
        return ENDLOOP_STRING;
    }
    
    public String endName()
    {
        return "end loop";
    }
}
