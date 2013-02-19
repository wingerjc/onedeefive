
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
            expl += '0'
            rolls.append(0)
        
        expl += '): ' + str(sum(rolls))
        stack.append(StackFrame(expl,rolls=rolls))
    
    def matchString(self):
        return LangDef.ROLL_STRING

# +++++++++++++++++++++++++++
class ComparisonFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        self.checkStackHeight(stack,2,cmd)
        
        arg2 = stack.pop().value()
        arg1 = stack.pop().value()
        ret = 0;
        
        if re.match(LangDef.LT_STRING,cmd):
            ret = int(arg1 < arg2)
        elif re.match(LangDef.LTE_STRING,cmd):
            ret = int(arg1 <= arg2)
        elif re.match(LangDef.GT_STRING,cmd):
            ret = int(arg1 > arg2)
        elif re.match(LangDef.GTE_STRING,cmd):
            ret = int(arg1 >= arg2)
        elif re.match(LangDef.EQ_STRING,cmd):
            ret = int(arg1 == arg2)
        elif re.match(LangDef.NEQ_STRING,cmd):
            ret = int(arg1 != arg2)
        
        stack.append(StackFrame(str(arg1) + ' ' + str(arg2) + ' ' +
                   cmd + ':' + str(ret), ret))
    
    def matchString(self):
        return LangDef.COMPARE_STRING

# +++++++++++++++++++++++++++
class MathFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        self.checkStackHeight(stack, 2, cmd)
        
        arg2 = stack.pop().value()
        arg1 = stack.pop().value()
        ret = 0
        
        if re.match(LangDef.ADD_STRING,cmd):
            ret = arg1 + arg2
        elif re.match(LangDef.SUB_STRING,cmd):
            ret = arg1 - arg2
        elif re.match(LangDef.MUL_STRING,cmd):
            ret = arg1 * arg2
        elif re.match(LangDef.DIV_STRING,cmd):
            ret = arg1 / arg2
        elif re.match(LangDef.MOD_STRING,cmd):
            ret = arg1 % arg2
        
        stack.append(StackFrame(str(arg1) + ' ' + str(arg2) + ' ' +
                   cmd + ':' + str(ret), ret))
        
    
    def matchString(self):
        return LangDef.MATH_STRING

# +++++++++++++++++++++++++++
class BooleanFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        
        if re.match(LangDef.NOT_STRING,cmd):
            self.checkStackHeight(stack,1,cmd)
            
            arg1 = stack.pop().value()
            ret = int(arg1 == 0)
            
            stack.append(StackFrame(str(arg1) + ' ' +
                         cmd + ':' + str(ret), ret))            
            return
        
        self.checkStackHeight(stack,2,cmd)
        
        arg2 = stack.pop().value()
        arg1 = stack.pop().value()
        
        if re.match(LangDef.AND_STRING,cmd):
            ret = int(arg1 != 0 and arg2 != 0)
        elif re.match(LangDef.OR_STRING,cmd):
            ret = int(arg1 != 0 or arg2 != 0)
        elif re.match(LangDef.XOR_STRING,cmd):
            ret = int((arg1 != 0 and arg2 == 0) or (arg1 == 0 and arg2 != 0))
        
        stack.append(StackFrame(str(arg1) + ' ' + str(arg2) + ' ' +
                     cmd + ':' + str(ret), ret))
    
    def matchString(self):
        return LangDef.BOOLEAN_STRING

# +++++++++++++++++++++++++++
class GroupFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        self.checkStackHeight(stack,1,cmd)
        
        count = stack.pop().value()
        
        self.checkStackHeight(stack,count,str(count) + ' ' + cmd)
        
        vals = list()
        descr = '('
        first = True
        
        for i in xrange(count):
            tmp = stack.pop()
            if tmp.isRoll() :
                for j in tmp.rolls() :
                    if first :
                        first = False
                    else :
                        descr += ', '
                    
                    vals.append(j)
                    descr += str(j)
            else :
                if first :
                    first = False
                else :
                    descr += ', '
                vals.append(tmp.value())
                descr += str(vals[-1])
        
        if len(vals) == 0 :
            vals.append(0)
            descr += '0'
        
        descr += '): ' + str(sum(vals))
        
        stack.append(StackFrame(descr,rolls=vals))
    
    def matchString(self):
        return LangDef.GROUP_STRING

# +++++++++++++++++++++++++++
class SizeFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        self.checkStackHeight(stack, 1, cmd)
        
        val = stack.pop()
        if val.isRoll() :
            ret = len(val.rolls())
        else :
            ret = 1
        
        stack.append(StackFrame('SIZE: ' + str(ret),ret))
    
    def matchString(self):
        return LangDef.SIZE_STRING

# +++++++++++++++++++++++++++
class MinMaxFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        self.checkStackHeight(stack, 2, cmd)
        
        count = stack.pop().value()
        vals = stack.pop()
        
        if count <= 0 :
            stack.append(StackFrame('0 ' + cmd,rolls=[0]))
            return
        
        if not vals.isRoll() :
            stack.append(StackFrame(str(vals.value()) + ' ' + cmd,vals.value()))
            return
        
        sort = sorted(vals.rolls()) 
        if re.match(LangDef.MAX_STRING,cmd) :
            start = -1
            end = min(count, len(sort)) * -1 - 1
            step = -1
            last = len(sort) * -1 - 1
        else :
            start = 0
            end = min(count, len(sort))
            step = 1
            last = len(sort)
        
        retVals = list()
        descr = '('
        first = True
        
        for n in xrange(start,end,step) :
            if first :
                first = False
            else :
                descr += ', '
            retVals.append(sort[n])
            descr += str(sort[n])
            
        if len(retVals) == 0 :
            retVals.append(0)
            descr += '0'
        
        descr += '): ' + str(sum(retVals)) + ' '
        if end != last :
            descr += '['
            first = True
            for n in xrange(end,last,step) :
                if first :
                    first = False
                else :
                    descr += ', '
                descr += str(sort[n])
            descr += ']'
        
        stack.append(StackFrame(descr,rolls=retVals))
    
    def matchString(self):
        return LangDef.MIN_STRING + '|' + LangDef.MAX_STRING

# +++++++++++++++++++++++++++
class AssignFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        self.checkStackHeight(stack, 2, cmd)
        
        var = stack.pop()
        val = stack.pop()
        
        if not re.match(LangDef.VARIABLE_STRING, var.explain()) :
            raise RuntimeError(
                    "ERROR assign/put must be provided a variable: " +
                    var.explain()
                    )
        
        expl = val.explain() + ' ' + var.explain() + ' ' + cmd
        if val.isRoll() :
            context[var.explain()] = StackFrame(var.explain(),
                                                rolls=val.rolls())
        else :
            context[var.explain()] = StackFrame(var.explain(),val.value())
            
        if re.match(LangDef.ASSIGN_STRING, cmd) :
            if val.isRoll() :
                stack.append(StackFrame(expl,rolls=val.rolls()))
            else :
                stack.append(StackFrame(expl,val.value()))
    
    def matchString(self):
        return LangDef.ASSIGN_STRING + '|' + LangDef.SHOVE_STRING

# +++++++++++++++++++++++++++
class StackFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        if re.match(LangDef.POP_STRING,cmd) :
            self.checkStackHeight(stack,1,cmd)
            stack.pop()
        else :
            if len(stack) > 0 :
                stack[:] = []
    
    def matchString(self):
        return LangDef.STACK_STRING

# +++++++++++++++++++++++++++
class DupFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        self.checkStackHeight(stack, 1, cmd)
        
        if stack[-1].isRoll() :
            next = StackFrame(stack[-1].explain(), rolls=stack[-1].rolls())
        else :
            next = StackFrame(stack[-1].explain(), stack[-1].value())
        
        stack.append(next)
        
    
    def matchString(self):
        return LangDef.DUP_STRING

# +++++++++++++++++++++++++++
class BlankFunction(SystemFunction):
    def execute(self, cmd, stack, context):
        pass
    
    def matchString(self):
        pass

# +++++++++++++++++++++++++++

