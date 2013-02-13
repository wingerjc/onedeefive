package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Structure for conditional statements
*/
public class IfStructure extends ControlStructure implements LangDef
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
                "ERROR: IF must have a condition on the stack."
            );
        }
        
        if(stack.pop().value() != 0)
        {
            script.execute(stack,context);
        }
    }
    
    @Override
    public String startPattern()
    {
        return IF_STRING;
    }
    
    @Override
    public String startName()
    {
        return "if";
    }
    
    @Override
    public String endPattern()
    {
        return ENDIF_STRING;
    }
    
    @Override
    public String endName()
    {
        return "end if";
    }
}

