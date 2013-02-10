package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class IfStructure extends ControlStructure implements LangDef
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
                "ERROR: IF must have a condition on the stack."
            );
        }
        
        if(stack.pop().value() != 0)
        {
            script.execute(stack,context);
        }
    }
    
    public String startPattern()
    {
        return IF_STRING;
    }
    
    public String startName()
    {
        return "if";
    }
    
    public String endPattern()
    {
        return ENDIF_STRING;
    }
    
    public String endName()
    {
        return "end if";
    }
}

