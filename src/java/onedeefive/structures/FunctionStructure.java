package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Control structure for defining functions.
*/
public class FunctionStructure extends ControlStructure
    implements LangDef
{
    @Override
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
    
    @Override
    public String startPattern()
    {
        return DEFUN_STRING;
    }
    
    @Override
    public String startName()
    {
        return "fun";
    }
    
    @Override
    public String endPattern()
    {
        return ENDFUN_STRING;
    }
    
    @Override
    public String endName()
    {
        return "end fun";
    }
}

