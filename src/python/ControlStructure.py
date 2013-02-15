
import re

import LangDef

import Script

class ControlStructure:
    def execute(self, script, initial, stack, context):
        i = 1; # nop
    
    def startPattern(self):
        return "start"
    
    def startName(self):
        return "start"
    
    def endPattern(self):
        return "end"
    
    def endName(self):
        return "end"
    
    def scriptEnd(self, scriptList, start):
        balance = 1
        i = start
        while balance > 0 :
            i += 1
            if i >= len(scriptList):
                raise IndexError("ERROR: " + self.startName() + 
                                 " without " + self.endName())
            
            if re.match(self.startPattern(),scriptList[i]):
                balance += 1
            
            if re.match(self.endPattern(),scriptList[i]):
                balance -= 1
        
        return i

# +++++++++++++++++
class IfStructure(ControlStructure):
    def execute(self, script, initial, stack, context):
        if len(stack) == 0 :
            raise IndexError('ERROR No value for conditional operator to test!')
        
        value = stack.pop().value()
        if value != 0 :
            script.execute(stack,context)
    
    def startPattern(self):
        return LangDef.IF_STRING
    
    def startString(self):
        return "if"
    
    def endPattern(self):
        return LangDef.ENDIF_STRING
    
    def endString(self):
        return "end if"

# +++++++++++++++++
class PrintStructure(ControlStructure):
    def execute(self, script, initial, stack, context):
        line = initial.lstrip()
        
        if len(line) > LangDef.PRINT_LENGTH :
            line = line[LangDef.PRINT_LENGTH:]
            Script.output().write(line)
        else:
            Script.output().write("\n")
    
    def startPattern(self):
        return LangDef.PRINT_STRING
    
    def scriptEnd(self, inputLines, start):
        return start

# +++++++++++++++++
class LoopStructure(ControlStructure):
    def execute(self, script, initial, stack, context):
        if len(stack) == 0 :
            raise IndexError('ERROR No count value for repeat block')
        
        value = stack.pop().value()
        if value > 0:
            for i in xrange(value):
                script.execute(stack, context)
    
    def startPattern(self):
        return LangDef.LOOP_STRING
    
    def startName(self):
        return "repeat"
    
    def endPattern(self):
        return LangDef.ENDLOOP_STRING
    
    def endName(self):
        return "end repeat"

# +++++++++++++++++
class FunctionStructure(ControlStructure):
    def execute(self, script, initial, stack, context):
        name = initial.strip().split(LangDef.TOKEN_SEPARATOR)[1]
        script.makeFunction(name)
    
    def startPattern(self):
        return LangDef.DEFUN_STRING
    
    def startName(self):
        return "fun"
    
    def endPattern(self):
        return LangDef.ENDFUN_STRING
    
    def endName(self):
        return "end fun"

# +++++++++++++++++
class IncludeStructure(ControlStructure):
    def execute(self, script, initial, stack, context):
        fname = initial.split(LangDef.TOKEN_SEPARATOR)[1]
        s = Script.Script(fname,True)
        s.execute(stack,context)
    
    def startPattern(self):
        return LangDef.INCLUDE_STRING
    
    def scriptEnd(self, scriptLines, start):
        return start
