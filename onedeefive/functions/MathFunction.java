package onedeefive.functions;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import onedeefive.*;

public class MathFunction extends SystemFunction implements LangDef
{
    public void execute(
        String cmd,
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    )
    {
        String expl = "";
        int arg1, arg2, ret;
        
        checkStackHeight(stack,2,cmd);
        
        arg1 = stack.pop().value();
        arg2 = stack.pop().value();
        ret = 0;
        
        if(Pattern.matches(ADD_STRING,cmd))
        {
            ret = arg1 + arg2;
            expl = arg2 + " " + arg1 + " " + cmd; 
            stack.push(new StackFrame(expl,ret));
        }else if(Pattern.matches(SUB_STRING,cmd))
        {
            ret = arg2 - arg1;
            expl = arg2 + " " + arg1 + " " + cmd; 
            stack.push(new StackFrame(expl,ret));
        }else if(Pattern.matches(MUL_STRING,cmd))
        {
            ret = arg1 * arg2;
            expl = arg2 + " " + arg1 + " " + cmd; 
            stack.push(new StackFrame(expl,ret));
        }else if(Pattern.matches(DIV_STRING,cmd))
        {
            ret = arg2 / arg1;
            expl = arg2 + " " + arg1 + " " + cmd; 
            stack.push(new StackFrame(expl,ret));
        }else if(Pattern.matches(MOD_STRING,cmd))
        {
            ret = arg2 % arg1;
            expl = arg2 + " " + arg1 + " " + cmd; 
            stack.push(new StackFrame(expl,ret));
        }else
        {
            throw new InputMismatchException(
                "ERROR: \"" + cmd + "\" is not a known math function"
            );
        }
    }
    
    public String matchString()
    {
        return MATH_STRING;
    }
}
