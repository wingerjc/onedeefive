package onedeefive.functions;

import java.util.*;
import onedeefive.*;

public abstract class SystemFunction
{
    public abstract void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    );
    
    public abstract String matchString();
    
    public static void checkStackHeight(
        Stack<StackFrame> stack,
        int minSize,
        String command)
    {
        if(stack.size() < minSize)
        {
            throw new IndexOutOfBoundsException(
                "Could not use operator \"" + command +
                "\". At least " + minSize + " parameters required."
            );
        }
    }
}
