package onedeefive.structures;

import java.util.*;
import java.util.regex.*;
import onedeefive.*;

/** This class provides a template for creating new control structures for the
* language that 1D5 understands. The main functions that need to be defined
* have to do with identifying beginning and ending patterns of a script.
*
* If the ControlStructure is only a single line (INCLUDE, PRINT, etc.), then
* scriptEnd(String[], int) should be overwritten to return the integer passed
* and both endName and endPattern may return null.
*
* Currently, each ControlStructure should have unique pattern strings for its
* startPattern and endPattern (i.e. not shared with any other structure).
* Otherwise structure nesting may be compromised.
*/
public abstract class ControlStructure
{
    /** Execute this control structure. The current stack and scope are passed
    * in as parameters alon with the opneing line of this structure and the
    * script that it encompasses.
    *
    * @param script
    *        The Script object that lies between the starting and ending lines
    *        of the current structure.
    * @param initial
    *        The opening (or only) line of this structure. This line can be used
    *        to pass parameters to the structure.
    * @param stack
    *        The current program stack that is in use when this structure is
    *        called.
    * @param context
    *        The current variable context/scope
    */
    public abstract void execute(
        Script script,
        String initial,
        Stack<StackFrame> stack,
        Map<String,StackFrame> context
    );
    
    /** Returns the regex/patten string associated with the start of
    * this structure
    */
    public abstract String startPattern();
    
    /** Returns the name of the opening half of this structure for error
    * reporting purposes.
    */
    public abstract String startName();
    
    /** Returns the regex/patten string associated with the end of
    * this structure
    */
    public abstract String endPattern();
    
    /** Returns the name of the opening half of this structure for error
    * reporting purposes.
    */
    public abstract String endName();
    
    /** Calculate the end of the script based on the startPatttern() and 
    * endPattern() functions. Usually you should not have to override this
    * method, but if the structure is only one line, this function should
    * be overridden to return the 'start' parameter.
    * 
    * @param script
    *        The strings that make up the script.
    * @param start
    *        The index of the first line of the script after the initial line.
    *
    * @return The index of the closing line of the structure.
    *         (e.g. the closing line of an if would be "END IF")
    */
    public int scriptEnd(String[] script, int start)
    {
        int balance = 1;
        int end = start;
        
        while(balance > 0 && end < script.length)
        {
            end++;
            
            if(Pattern.matches(startPattern(),script[end]))
            { balance++; }
            
            if(Pattern.matches(endPattern(),script[end]))
            { balance--; }
        }
        
        if(end == script.length)
        {
            throw new IndexOutOfBoundsException(
                "ERROR: \"" + startName() + "\" without \"" + endName()
            );
        }
        
        return end;
    }
}
