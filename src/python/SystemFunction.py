
import re
import random
import LangDef
import Script

from StackFrame import StackFrame


class SystemFunction:
    def execute(self, cmd, stack, context):
        pass
    
    def matchString(self):
        return ""
    
    def checkStackHeight(self, stack, minHeight, command):
        if len(stack) < minHeight :
            raise IndexError("ERROR Command " + command + " requires at least "
                             + str(minHeight) + " parameters. " + 
                             str(len(stack)) + " found")


# +++++++++++++++++++++++++++
class PrintFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        if re.match(LangDef.ENDL_STRING,cmd):
            Script.output().write('\n')
        elif re.match(LangDef.OUT_STRING,cmd):
            Script.output().write(' ' + str(stack.pop().value()))
        elif re.match(LangDef.EXPLAIN_STRING,cmd):
            Script.output().write(stack.pop().explain())
        elif re.match(LangDef.PEEK_STRING,cmd):
            Script.output().write(' ' + str(stack[-1].value()))
    
    def matchString(self):
        return LangDef.OUTPUT_STRING;


# +++++++++++++++++++++++++++
class IntegerFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        stack.append(StackFrame(cmd + ': ' + cmd,int(cmd)))
    
    def matchString(self):
        return LangDef.INTEGER_STRING
# +++++++++++++++++++++++++++
class DiceFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        dice = 0
        sides = 0
        
        if(re.match(LangDef.DICEROLL_STRING,cmd)):
            var = re.split(LangDef.DIE_SEPARATOR,cmd)
            dice = int(var[0])
            sides = int(var[1])
        else :
            self.checkStackHeight(stack,2,cmd)
            sides = stack.pop().value()
            dice = stack.pop().value()
        
        if dice < 0 :
            raise RuntimeError('ERROR cannot roll less than 0 dice')
        
        if sides < 1 :
            raise RuntimeError('ERROR dice must have at least one side')
        
        rolls = list()
        expl = '('
        for i in xrange(dice):
            rolls.append(random.randint(1,sides))
            if i > 0 :
                expl += ', '
            expl += str(rolls[-1])
        
        if dice == 0:
            expl.append('0')
            rolls.append(0)
        
        expl += '): ' + str(sum(rolls))
        stack.append(StackFrame(expl,rolls=rolls))
    
    def matchString(self):
        return LangDef.ROLL_STRING
# +++++++++++++++++++++++++++
# +++++++++++++++++++++++++++
# +++++++++++++++++++++++++++
# +++++++++++++++++++++++++++
# +++++++++++++++++++++++++++
# +++++++++++++++++++++++++++

