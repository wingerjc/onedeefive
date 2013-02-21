## @package Script
#
# @brief Package for executable 1D5 scripts.
#
# @todo make output stream mutable
#
import re
import sys

import LangDef
import SystemFunction
import ControlStructure

from StackFrame import StackFrame

## @brief An executable 1D5 Script
#
# Script files can be created from srings or from a file. They run by using
# the execute() function and only need to be passed a 
class Script:
    
    ## @brief The language control structures available to users.
    #
    # See @ref ControlStructure for the default list (includes all members of
    # the ControlStructure package).
    #
    _controlStructures = None
    
    ## @brief The set of system functinos available to use.
    #
    # See @ref SystemFunction for the default list (includes all members of the
    # SystemFunction package).
    #
    _systemFunction = None
    
    ## @brief The map of user function names (str) to Script objects.
    _userFunctions = None
    
    ## @brief The output stream for all currently running scripts
    _output = sys.stdout
    
    ## @brief Create a new Script from file or string.
    #
    # @param input
    #        The input string that is this script or name of a script file.
    # @param isFile
    #        Whether input should be treated as a file name
    #
    # @todo Need error handling for file not found...
    #
    def __init__(self, input, isFile=False):
        if(isFile):
            self._script = file(input).readlines()
        else:
            self._script = re.split(LangDef.ENDLINE_STRING,input)
        
        for i in xrange(len(self._script)):
            self._script[i] = re.split(LangDef.COMMENT_STRING,self._script[i])[0]
            if re.match(LangDef.PRINT_STRING,self._script[i]) :
                self._script[i] = re.split(LangDef.ENDLINE_STRING,
                                           self._script[i].lstrip())[0]
            else:
                self._script[i] = self._script[i].strip()
                self._script[i] = re.sub('[ \t]',LangDef.TOKEN_SEPARATOR,
                                         self._script[i])
    
    ## @brief Execute this scripts with the given stack and context.
    #
    # @todo Need to print out line errors to sys.stderr
    #
    def execute(self,stack=[],context=dict()):
        skip = -1;
        for i in xrange(len(self._script)):
            if(i > skip):
                line = self._script[i]
                processed = False
                
                # try to interpret as a control structure
                for struct in Script._controlStructures:
                    if not processed and re.match(struct.startPattern(),line):
                        processed = True
                        skip = struct.scriptEnd(self._script, i)
                        
                        subscript = '';
                        if skip > i:
                            for line in self._script[i+1:skip]:
                                subscript = subscript + line + '\n'
                        struct.execute(Script(subscript),
                                       self._script[i],
                                       stack,
                                       context)
                # not a control structure, must be a list of tokens (functions)
                if not processed :
                    self._processTokens(i, stack, context)
    
    ## @brief Process individual tokens as functions on a script line.
    #
    # @todo raise exception for token that is not recognized
    #
    def _processTokens(self, lineNumber, stack, context):
        
        tokens = re.split(LangDef.TOKEN_SEPARATOR,self._script[lineNumber])
        
        for token in tokens :
            processed = False
            
            # try to process it as a regular system function
            for func in Script._systemFunctions :
                if not processed and re.match(func.matchString(), token):
                    processed = True
                    func.execute(token, stack, context)
            
            # try to process it as a user defined function
            if not processed and token in Script._userFunctions:
                Script._userFunctions[token].execute(stack, context)
            
            # try to process it as a variable
            if not processed and re.match(LangDef.VARIABLE_STRING, token) :
                if token in context :
                    stack.append(context[token])
                else :
                    stack.append(StackFrame(token,0))
                processed = True
            
            # todo: raise exception, illegal token
    
    ## @brief Add the current 
    #
    # @param name
    #        The name the newly defined function will be called by
    #
    def makeFunction(self, name):
        Script._userFunctions[name] = self
    
## Instantiate all required class level variables for Scripts.
#
# This automatically loads the default ControlStructure and SystemFunction
# objects for Scripts removing all 
def instantiateSystem():
    Script._controlStructures = list()
    Script._controlStructures.append(ControlStructure.IfStructure())
    Script._controlStructures.append(ControlStructure.LoopStructure())
    Script._controlStructures.append(ControlStructure.IncludeStructure())
    Script._controlStructures.append(ControlStructure.PrintStructure())
    Script._controlStructures.append(ControlStructure.FunctionStructure())
    #Script._controlStructures.append()
        
    Script._systemFunctions = list()
    Script._systemFunctions.append(SystemFunction.IntegerFunction())
    Script._systemFunctions.append(SystemFunction.PrintFunction())
    Script._systemFunctions.append(SystemFunction.DiceFunction())
    Script._systemFunctions.append(SystemFunction.ComparisonFunction())
    Script._systemFunctions.append(SystemFunction.MathFunction())
    Script._systemFunctions.append(SystemFunction.BooleanFunction())
    Script._systemFunctions.append(SystemFunction.GroupFunction())
    Script._systemFunctions.append(SystemFunction.SizeFunction())
    Script._systemFunctions.append(SystemFunction.MinMaxFunction())
    Script._systemFunctions.append(SystemFunction.AssignFunction())
    Script._systemFunctions.append(SystemFunction.StackFunction())
    Script._systemFunctions.append(SystemFunction.DupFunction())
        
    Script._userFunctions = dict()
        
    
## Get the current output stream for Scripts
def output():
    return Script._output
    
# Go ahead and load the basics
instantiateSystem()
