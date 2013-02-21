## @package Script
#
# @brief Package for executable 1D5 Script objects.
#
import re
import sys

import LangDef
import SystemFunction
import ControlStructure

from StackFrame import StackFrame

## @brief The language control structures available to users.
#
# See ControlStructure for the default list (includes all members of
# the ControlStructure package).
# Treat as private to module.
#
_controlStructures = None

## @brief The set of system functinos available to use.
#
# See SystemFunction for the default list (includes all members of the
# SystemFunction package).
# Treat as private to module.
#
_systemFunction = None

## @brief The map of user function names (str) to Script objects.
# Treat as private to module.
_userFunctions = None

## @brief The output stream for all currently running scripts
# Treat as private to module.
_output = sys.stdout

## @brief An executable 1D5 Script
#
# Script files can be created from srings or from a file. They run by using
# the execute() function. Each script is a single executable unit and can be
# run independently. Scripts may also be share defined variable scope
# (called context) and a program stack. Inclusion of these two elements is done
# at execution time.
#
# Scripts do not check the format and content of their input until they are
# executed. This allows you to define a Script and Scripts it depends on
# (user defined functions, etc.) in any order and then executing them in
# dependency order.
#
class Script:
    
    ## @brief Create a new Script from file or string.
    #
    # This method creates a Script from either a file or a given input string.
    # The input is not processed beyond shortening whitespace to the defined
    # token separator and removing leading and trailing spaces.
    #
    # @param input
    #        The input string that is this script or name of a script file.
    # @param isFile
    #        Whether input should be treated as a file name
    #
    def __init__(self, input, isFile=False):
        # get the whole input script and stor it
        if(isFile):
            try:
                self._script = file(input).readlines()
            except Exception as e :
                sys.stderr.write('----------------ERROR---------------\n')
                sys.stderr.write('Could not open script file: ' + input + '\n')
                sys.stderr.write('Exception message:\n')
                sys.stderr.write(str(e) + '\n')
                self._script = ['']
        else:
            self._script = re.split(LangDef.ENDLINE_STRING,input)
        
        # remove comments and strip extra whitespace
        for i in xrange(len(self._script)):
            self._script[i] = re.split(LangDef.COMMENT_STRING,self._script[i])[0]
            if re.match(LangDef.PRINT_STRING,self._script[i]) :
                # print lines only remove leading spaces and extraneous \n
                self._script[i] = re.split(LangDef.ENDLINE_STRING,
                                           self._script[i].lstrip())[0]
            else:
                self._script[i] = self._script[i].strip()
                self._script[i] = re.sub('[ \t]',LangDef.TOKEN_SEPARATOR,
                                         self._script[i])
    
    ## @brief Execute this scripts with the given stack and context.
    #
    # Both stack and context are optional. If either is not included, then
    # a new empty stack and/or context is created specifically for this
    # execution and is not retrievable.
    #
    # @param stack
    #        list[] -> StackFrame, program stack used to pass variables to and
    #        store return values from functions.
    # @param context
    #        dict(str) -> StackFrame, Maps variable names to stored values.
    #
    def execute(self,stack=[],context=dict()):
        skip = -1;
        for i in xrange(len(self._script)):
            if(i > skip):
                line = self._script[i]
                processed = False
                
                # try to interpret as a control structure
                for struct in _controlStructures:
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
                try:
                    if not processed :
                        self._processTokens(i, stack, context)
                except Exception as e :
                    sys.stderr.write('----------------ERROR---------------\n')
                    sys.stderr.write('Sorry, I could not process the line:\n')
                    sys.stderr.write(line + '\n\n')
                    sys.stderr.write('I\'m trying to continue with the next ')
                    sys.stderr.write('line but script output may be garbage.\n')
                    sys.stderr.write('The specific error was:\n')
                    sys.stderr.write(str(e) + '\n\n')
    
    ## @brief Process individual tokens as functions on a script line.
    #
    def _processTokens(self, lineNumber, stack, context):
        
        tokens = re.split(LangDef.TOKEN_SEPARATOR,self._script[lineNumber])
        
        for token in tokens :
            processed = False
            
            # try to process it as a regular system function
            for func in _systemFunctions :
                if not processed and re.match(func.matchString(), token):
                    func.execute(token, stack, context)
                    processed = True
            
            # try to process it as a user defined function
            if not processed and token in _userFunctions:
                _userFunctions[token].execute(stack, context)
                processed = True
            
            # try to process it as a variable
            if not processed and re.match(LangDef.VARIABLE_STRING, token) :
                if token in context :
                    stack.append(context[token])
                else :
                    stack.append(StackFrame(token,0))
                processed = True
            
            if not processed and len(token) > 0 :
                raise RuntimeError('ERROR Could not understand command: ' 
                                   + token)
            
    
    ## @brief Add the list of currently available user functions
    #
    # Once added to the list of currently available functions, the script may
    # be then be called by the given name in any script object.
    #
    # @param name
    #        The name the newly defined function will be called by
    #
    def makeFunction(self, name):
        _userFunctions[name] = self
    
## Instantiate all required class level variables for Scripts.
#
# This automatically loads the default ControlStructure and SystemFunction
# objects for Scripts removing all 
#
def instantiateSystem():
    global _controlStructures
    global _systemFunctions
    global _userFunctions
    _controlStructures = list()
    _controlStructures.append(ControlStructure.IfStructure())
    _controlStructures.append(ControlStructure.LoopStructure())
    _controlStructures.append(ControlStructure.IncludeStructure())
    _controlStructures.append(ControlStructure.PrintStructure())
    _controlStructures.append(ControlStructure.FunctionStructure())
    #Script._controlStructures.append()
        
    _systemFunctions = list()
    _systemFunctions.append(SystemFunction.IntegerFunction())
    _systemFunctions.append(SystemFunction.PrintFunction())
    _systemFunctions.append(SystemFunction.DiceFunction())
    _systemFunctions.append(SystemFunction.ComparisonFunction())
    _systemFunctions.append(SystemFunction.MathFunction())
    _systemFunctions.append(SystemFunction.BooleanFunction())
    _systemFunctions.append(SystemFunction.GroupFunction())
    _systemFunctions.append(SystemFunction.SizeFunction())
    _systemFunctions.append(SystemFunction.MinMaxFunction())
    _systemFunctions.append(SystemFunction.AssignFunction())
    _systemFunctions.append(SystemFunction.StackFunction())
    _systemFunctions.append(SystemFunction.DupFunction())
        
    _userFunctions = dict()

## @brief Add a new SystemFunction to the global list.
#
# Lets developers add their own SystemFunctions to the list of available
# SystemFunctions that are automatically included. Useful for extensibility.
#
# @param function
#        A SystemFunction object that can be executed.
#
def addSystemFunction(function):
    if not function is None:
        _systemFunctions.append(function)

## Add a new ControlStructure to the global list.
#
# Lets developers add their own ControlStructure to the list of available
# SystemFunctions that are automatically included. Useful for extensibility.
#
# @param struct
#        A ControlStructure object that can be executed.
#
def addControlStructure(struct):
    if not struct is None:
        _controlStructures.append(struct)

## Set the standard script output stream.
#
# @param newOut
#        An output stream that can be written to by scripts. Must have similar
#        interface to sys.stdout.
#
def setOutput(newOut):
    global _output
    if not newOut is None:
        _output = newOut

## Get the current output stream for Scripts
def output():
    return _output
    
# Go ahead and load the basics
instantiateSystem()
