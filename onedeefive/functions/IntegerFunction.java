package onedeefive.functions;

import java.util.*;
import java.io.*;
import onedeefive.*;

public class IntegerFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        int val = Integer.parseInt(cmd);
        stack.push(new StackFrame("" + val,val));
    }
    
    public String matchString()
    {
        return "-?((0)|([1-9][0-9]*))";
    }
    
}
