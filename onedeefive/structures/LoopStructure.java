package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Structure for finxed iteration loops.
*/
public class LoopStructure extends ControlStructure implements LangDef
{
    @Override
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
    
    @Override
    public String startPattern()
    {
        return LOOP_STRING;
    }
    
    @Override
    public String startName()
    {
        return "repeat";
    }
    
    @Override
    public String endPattern()
    {
        return ENDLOOP_STRING;
    }
    
    @Override
    public String endName()
    {
        return "end loop";
    }
}
