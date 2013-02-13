package onedeefive.functions;

import java.util.*;
import onedeefive.*;

/** This class can be extended to create new classes of functions that can be
* used throughout a script and have more power than standard user defined
* functions.
*/
public abstract class SystemFunction
{
    /** Execute this function given its name, the current program stack and
    * the variable context/scope.
    *
    * @param cmd
    *        The actual function name that was invoked.
    * @param stack
    *        The current program stack, it can be read from or added to.
    * @param vars
    *        The mapping of variable names to values. 
    */
    public abstract void execute(
        String cmd, 
        Stack<StackFrame> stack,
        Map<String,StackFrame> vars
    );
    
    /** Access the pattern string that represents the set of functions this
    * object can interpret.
    *
    * @return A string regex pattern for matching all functions covered by this
    *         object.
    */
    public abstract String matchString();
    
    /** Standard function to do error checking and make sure that the stack has
    * enough parameters on it to execute a given function.
    *
    * @param stack
    *        The current program stack.
    * @param minSize
    *        The minimum number of parameters required to call the function.
    * @param command
    *        The command that the error occured on.
    *
    * @throws IndexOutOfBoundsException When there aren't enough parameters.
    */
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
