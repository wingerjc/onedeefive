package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class FunctionStructure extends ControlStructure
    implements LangDef
{
    public void execute(
        Script script,
        String initial,
        Stack<StackFrame> stack,
        Map<String,StackFrame> context
    )
    {
        String name = initial.split(TOKEN_SEPARATOR)[1];
        script.makeFunction(name);
    }
    
    public String startPattern()
    {
        return DEFUN_STRING;
    }
    
    public String startName()
    {
        return "fun";
    }
    
    public String endPattern()
    {
        return ENDFUN_STRING;
    }
    
    public String endName()
    {
        return "end fun";
    }
}

