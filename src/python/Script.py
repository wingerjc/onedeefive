
import re
import sys

import LangDef
import SystemFunction
import ControlStructure

from StackFrame import StackFrame

class Script:
    _controlStructures = None
    _systemFunction = None
    _userFunctions = None
    
    _output = sys.stdout
    
    def __init__(self, input, isFile=False):
        if(isFile):
            # TODO: need error handling for file not found...
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
                    self.processTokens(i, stack, context)
    
    def processTokens(self, lineNumber, stack, context):
        tokens = re.split(LangDef.TOKEN_SEPARATOR,self._script[lineNumber])
        for token in tokens :
            processed = False
            
            for func in Script._systemFunctions :
                if not processed and re.match(func.matchString(), token):
                    processed = True
                    func.execute(token, stack, context)
            
            if not processed and token in Script._userFunctions:
                Script._userFunctions[token].execute(stack, context)
                
    def makeFunction(self, name):
        Script._userFunctions[name] = self
    
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
        
    Script._userFunctions = dict()
        
    
def output():
    return Script._output
        
instantiateSystem()
