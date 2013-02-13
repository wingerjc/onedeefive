package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** Control structure for printing out strings
*/
public class PrintStructure extends ControlStructure implements LangDef
{
    @Override
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
    
    @Override
    public String startPattern()
    {
        return PRINT_STRING;
    }
    
    @Override
    public String startName()
    {
        return "print";
    }
    
    @Override
    public String endPattern()
    {
        return null;
    }
    
    @Override
    public String endName()
    {
        return "print end";
    }
    
    @Override
    public int scriptEnd(String[] script, int start)
    {
        return start;
    }
}
