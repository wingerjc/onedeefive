## @package StackFrame
#
# @brief Wrapper package for StackFrame class.
#

## A stack frame object for 1D5's program stack.
#
# A stack frame is the basic type for 1d5. It always has at least an integer
# value and string explanation. There is also an optional list of integers
# whose sum is the integer value of the list. The list is made if the frame
# was made from a roll or a group function (min, max, group, etc.). Acessing
# this value will provide the list or None if the frame is not a list. You
# can also check this by using isRoll() which returns a boolean value
# describing if the frame is a group.
#
class StackFrame:
    ## Create a new StackFrame object, must have at least an explanation.
    #
    # Either value or rolls is required. Using both will have value
    # overridden with the sum of the values held in rolls.
    #
    # @param explanation The string explanation of that this item is.
    # @param value The integer value of this object
    # @param rolls The list of integers that make up this object
    #
    def __init__(self, explanation, value=0, rolls=None):
        self._descr = explanation
        self._value = value
        
        # copy values in rolls if any were passed
        if(not (rolls is None)):
            self._rolls = rolls[:]
            self._value = sum(rolls)
        else :
            self._rolls = None
    
    ## Return the integer value of this StackFrame (int)
    def value(self):
        return self._value
    
    ## Return the explanation string of this StackFrame (str)
    def explain(self):
        return self._descr
    
    ## Return whether this StackFrame is a list/roll. (boolean)
    def isRoll(self):
        return not (self._rolls is None)
    
    ## Return the list of numbers that make up this StackFrame, or None.
    # (list->int)
    def rolls(self):
        return self._rolls
