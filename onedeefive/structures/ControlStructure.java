package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public abstract class ControlStructure
{
    public abstract void execute(
        Script script,
        String initial,
        Stack<StackFrame> stack,
        Map<String,StackFrame> context
    );
    
    public abstract String startPattern();
    public abstract String startName();
    public abstract String endPattern();
    public abstract String endName();
    
    public int scriptEnd(String[] script, int start)
    {
        int balance = 1;
        int end = start;
        
        while(balance > 0 && end < script.length)
        {
            end++;
            
            if(Pattern.matches(startPattern(),script[end]))
            { balance++; }
            
            if(Pattern.matches(endPattern(),script[end]))
            { balance--; }
        }
        
        if(end == script.length)
        {
            throw new IndexOutOfBoundsException(
                "ERROR: \"" + startName() + "\" without \"" + endName()
            );
        }
        
        return end;
    }
}
