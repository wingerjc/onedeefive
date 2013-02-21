

class StackFrame:
    
    """A stack frame object for 1D5's program stack.
    
    A stack frame is the basic type for 1d5. It always has at least an integer
    value and string explanation. There is also an optional list of integers
    whose sum is the integer value of the list. The list is made if the frame
    was made from a roll or a group function (min, max, group, etc.). Acessing
    this value will provide the list or None if the frame is not a list. You
    can also check this by using isRoll() which returns a boolean value
    describing if the frame is a group.
    
    """
    
    def __init__(self, explanation, value=0, rolls=None):
        """Create a new StackFrame object, must have at least an explanation.
        
        keyword arguments
        -- explanation = The string explanation of that this item is.
        -- value = the integer value of this object
        -- rolls = the list of integers that make up this object
        
        Either value or rolls is required. Using both will have value
        overridden with the sum of the values in rolls.
        
        """
        self._descr = explanation
        self._value = value
        
        if(not (rolls is None)):
            self._rolls = rolls[:]
            self._value = 0
            
            for i in self._rolls:
                self._value += i
        else :
            self._rolls = None
    
    def value(self):
        """Return the integer value of this StackFrame (int)"""
        return self._value
    
    def explain(self):
        """Return the explanation string of this StackFrame (str)"""
        return self._descr
    
    def isRoll(self):
        """Return whether this StackFrame is a list/roll. (boolean)"""
        return not (self._rolls is None)
    
    def rolls(self):
        """Return the list of numbers that make up this 
        StackFrame, or None. (list->int)
        """
        return self._rolls
