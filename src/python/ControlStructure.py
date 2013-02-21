## @package ControlStructure
# @brief Contains the base and standard ControlStructure classes

import re

import LangDef

import Script

## Language constructs that don't fit nicely with the stack oriented mindset.
#
# ControlStructures allow the definition of conditionals, loops, and other
# useful single and multi-line constructs. Currently, they support any single
# line construct or any construct with a matched start and end line. This
# limited structure is flexible, but precludes use of chained statements like
# if/else instead, the current way is to use if a .../ if !a ...
# 
class ControlStructure:
    ## Execute the control structure.
    #
    # This method is the heart of the ControlStructure class and is called at
    # execution time when a structure is detected in a Script using the value
    # return by startPattern()
    #
    # @param script
    #        The body of the structure (if any) as an executable Script
    # @param initial
    #        The first line of the script as a string. Could be used to pass
    #        information to the structure, especially if is only a single line.
    # @param stack
    #        The program stack.
    # @param context
    #        The dict mapping variable names to values in the current scope.
    def execute(self, script, initial, stack, context):
        pass
    
    ## The pattern that matches the first line of this structure.
    #
    # Used by Script to discern when a line should be executed as a scructure.
    #
    # @return The regex pattern string that matches single or opening lines of
    #         this structure
    #
    def startPattern(self):
        return "start"
    
    ## The name of the start of this structure. Used for error strings.
    #
    # @return The name of the opening part of this structure
    #
    def startName(self):
        return "start"
    
    ## Used by the scriptEnd method to find the last line of a structure.
    #
    # If you are using the default definition of scriptEnd make sure that
    # this string does not match endPatterns of other strings so that the
    # method can accurately read nesed structures.
    #
    # @return The regex pattern string that matches the closing lines of this
    #         structure.
    #
    def endPattern(self):
        return "end"
    
    ## The name of the end of this structure. Used for error strings.
    #
    # @return The name of the ending line of this structure
    #
    def endName(self):
        return "end"
    
    ## Determines the ending line index of the structure
    #
    # If you are defining a new structure that is only a single line you
    # should override this method to return start. Otherwise you should not
    # need to redefine this method. You should also not neet to use this method
    # in general extension creation.
    #
    # @param scriptList
    #        an ordered list of all the strings that make up the script
    # @param start
    #        The integer index of the first line of this structure.
    #
    # @return The index of scriptList that is the matched closing line of this
    #         structure
    #
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

# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
## Conditional statement structure
class IfStructure(ControlStructure):
    def execute(self, script, initial, stack, context):
        if len(stack) == 0 :
            raise IndexError('ERROR No value for conditional operator to test!')
        
        value = stack.pop().value()
        if value != 0 :
            script.execute(stack,context)
    
    def startPattern(self):
        return LangDef.IF_STRING
    
    def startName(self):
        return "if"
    
    def endPattern(self):
        return LangDef.ENDIF_STRING
    
    def endName(self):
        return "end if"

# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
## Structure for printing literal strings
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

# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
## Structure for executing loops
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

# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
## Structure for defining functions
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

# ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
## Structure for including external script files.
class IncludeStructure(ControlStructure):
    def execute(self, script, initial, stack, context):
        fname = initial.split(LangDef.TOKEN_SEPARATOR)[1]
        s = Script.Script(fname,True)
        s.execute(stack,context)
    
    def startPattern(self):
        return LangDef.INCLUDE_STRING
    
    def scriptEnd(self, scriptLines, start):
        return start
