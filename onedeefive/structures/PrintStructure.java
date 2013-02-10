package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

public class PrintStructure extends ControlStructure implements LangDef
{
    public  void execute(
        Script script,
        String initial,
        Stack<StackFrame> stack,
        Map<String,StackFrame> context
    )
    {
        if(initial.length() < PRINT_LENGTH)
        {
            Script.output().println();
        }else
        {
            Script.output().print(initial.substring(PRINT_LENGTH));
        }
    }
    
    public String startPattern()
    {
        return PRINT_STRING;
    }
    
    public String startName()
    {
        return "print";
    }
    
    public String endPattern()
    {
        return null;
    }
    
    public String endName()
    {
        return "print end";
    }
    
    public int scriptEnd(String[] script, int start)
    {
        return start;
    }
}
