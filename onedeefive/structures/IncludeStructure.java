package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class IncludeStructure extends ControlStructure implements LangDef
{
    public void execute(
        Script script,
        String initial,
        Stack<StackFrame> stack,
        Map<String,StackFrame> context
    )
    {
        String fname = initial.split(TOKEN_SEPARATOR)[1];
        
        Script s = new Script(fname,true);
        
        s.execute(stack,context);
    }
    
    public String startPattern()
    {
        return INCLUDE_STRING;
    }
    
    public String startName()
    {
        return "include";
    }
    
    public String endPattern()
    {
        return null;
    }
    
    public String endName()
    {
        return null;
    }
    
    public int scriptEnd(String[] script, int start)
    {
        return start;
    }
}
