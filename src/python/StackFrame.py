

class StackFrame:
    def __init__(self, explanation, value=0, rolls=None):
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
        return self._value
    
    def explain(self):
        return self._descr
    
    def isRoll(self):
        return not (self._rolls is None)
    
    def rolls(self):
        return self._rolls
