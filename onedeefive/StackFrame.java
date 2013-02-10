package onedeefive;

import java.util.*;

public class StackFrame
{
    private int _value = 0;
    private ArrayList<Integer> _rolls = null;
    private String _explanation = "";
    private ArrayList<StackFrame> _params = null;
    
    public StackFrame(String desc,int val)
    {
        _value = val;
        _explanation = desc;
    }
    
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
    
    public int value()
    {
        return _value;
    }
    
    public boolean isRoll()
    {
        return _rolls != null;
    }
    
    public ArrayList<Integer> rolls()
    {
        return _rolls;
    }
    
    public String explain()
    {
        return _explanation;
    }
}
