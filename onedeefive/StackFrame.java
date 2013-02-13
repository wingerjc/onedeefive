package onedeefive;

import java.util.*;

/** This class represents the standard data type that is stored on the 
* stack of 1D5. In short, each item represents a number and text explanation,
* and may have an optional list of values.
*/
public class StackFrame
{
/* +++++++++++++++   INSTANCE MEMBERS +++++++++++++++++++++ */
    /** The numeric value associated with this stack entry */
    private int _value = 0;
    
    /** The list of numbers (if any) associated with this object */
    private ArrayList<Integer> _rolls = null;
    
    /** The explanation of how this entry was calculated */
    private String _explanation = "";
    
/* +++++++++++++++   CONSTRUCTORS +++++++++++++++++++++ */
    /** Create a new StackFrame object without a list value.
    *
    * @param desc
    *        The explanation of how this entry was calculated
    * @param val
    *        The numeric value of this entry
    */
    public StackFrame(String desc,int val)
    {
        _value = val;
        _explanation = desc;
    }
    
    /** Create a new StackFrame object with a list value. This constructor
    * automatically calculates the value of the entry.
    *
    * @param desc
    *        The explanation of how the value was computed
    * @param rolls
    *        The ordered list of values that this item is made of.
    */
    public StackFrame(String desc, ArrayList<Integer> rolls)
    {
        _rolls = new ArrayList<Integer>();
        _rolls.addAll(rolls);
        
        _explanation = desc;
        
        _value = 0;
        for(Integer i : _rolls)
        {
            _value += i;
        }
    }
    
/* +++++++++++++++   INSTANCE METHODS (ACCESSORS) +++++++++++++++++++++ */
    /** Access the numeric value of this entry.
    *
    * @return The numeric value of this entry.
    */
    public int value()
    {
        return _value;
    }
    
    /** Discern whether this entry has a list of values.
    *
    * @return true whenever rolls() returns a non-null value.
    */
    public boolean isRoll()
    {
        return _rolls != null;
    }
    
    /** Return ths list of numbers that make up this entry or null if
    * this is not a list item.
    *
    * @return the list of values (or null) that represent this data item
    */
    public ArrayList<Integer> rolls()
    {
        return _rolls;
    }
    
    /** Access th explanation of this stack entry.
    *
    * @return The explanatin of how the value of this item was computed.
    */
    public String explain()
    {
        return _explanation;
    }
}
