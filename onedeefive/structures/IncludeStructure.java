package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Structure for including files
*/
public class IncludeStructure extends ControlStructure implements LangDef
{
    @Override
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
    
    @Override
    public String startPattern()
    {
        return INCLUDE_STRING;
    }
    
    @Override
    public String startName()
    {
        return "include";
    }
    
    @Override
    public String endPattern()
    {
        return null;
    }
    
    @Override
    public String endName()
    {
        return null;
    }
    
    @Override
    public int scriptEnd(String[] script, int start)
    {
        return start;
    }
}
