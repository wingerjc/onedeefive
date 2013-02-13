package onedeefive.functions;

import java.util.*;
import java.io.*;
import onedeefive.*;

/** REQUIRED Function for interpreting integer literals.
*/
public class IntegerFunction extends SystemFunction implements LangDef
{
    @Override
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        int val = Integer.parseInt(cmd);
        stack.push(new StackFrame("" + val,val));
    }
    
    @Override
    public String matchString()
    {
        // match any regular integer (no leading 0s allowed)
        return "-?((0)|([1-9][0-9]*))";
    }
    
}
