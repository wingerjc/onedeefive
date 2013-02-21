## @package onedeefive
#
# @brief The main executable package for 1D5
#
# This package holds the main executable section of the program.
# It is basically an interactive console that can be run with i/o redirection
# from specified script files.
#


import sys
import re

import LangDef

from Script import Script,setOutput


## @brief Pattern to match console command exit
EXIT_STRING = '^[ \t]*(?i)exit(?i)[ \t]*$'

## @brief Pattern to match console command start multi-line script
READ_STRING = '^[ \t]*(?i)script(?i)[ \t]*$'

## @brief Pattern to match console command end multi-line script
ENDREAD_STRING = '^[ \t]*(?i)end script(?i)[ \t]*$'

## @brief Run 1D5 with command line arguments
#
# Runs 1D5 and reads arguments passed via command line (if any). These arguments
# allow you to set an input script file, an output text file, or ask for the
# cli help string to be printed.
#
def main():
    VERSION = '1.0.0/0213'
    if len(sys.argv) == 1 :
        consoleMode()
    else :
        outFile = None
        inFile = None
        for i in xrange(1,len(sys.argv)) :
            if sys.argv[i] == '-i' :
                inFile = Script(sys.argv[i+1],True)
            elif sys.argv[i] == '-o' :
                try:
                    outFile = open(sys.argv[i+1],'w')
                except Exception as e :
                    sys.stderr.write('ERROR I cannot open: ' + sys.argv[i+1] +
                                     '\nThe error was:\n')
                    sys.stderr.write(str(e) + '\n')
                    return
                
            elif sys.argv[i] == '--help' :
                sys.stdout.write(
                    "1D5 Version: " + VERSION + "\n" +
                    "The scriptable dice roller.\n"+
                    "Usage: python onedeefive.py [options]\n\n"+
                    "Options:\n"+
                    "-i <file name>\n"+
                    "\tRead input from the specified file.\n\n"+
                    "-o <file name>\n"+
                    "\twrite output to the specified file.\n\n"+
                    "--help\n"+
                    "\tPrint this message.\n"
                    )
        
        if not outFile is None :
            setOutput(outFile)
        
        if inFile is None :
            consoleMode()
        else :
            inFile.execute()
    
## @brief Run 1D5 as an interactive console.
#
# Input is read directly from sys.stdin and executed line by line unless a
# multi-line block is entered. Output is written by the scripts objects
# themselves (default: sys.stdout, see package Script for more information).
#
def consoleMode():
    # initialize variables
    reading = False
    line = ''
    stack = list()
    context = dict()
    
    # read from the console
    while True:
        if reading:
            # we are reading a multi-line segment
            cur = sys.stdin.readline()
            if re.match(ENDREAD_STRING, cur) :
                reading = False
                script = Script(line)
                script.execute(stack,context)
            else :
                line = line + cur + '\n'
        else :
            # execute the single line we just read
            line = sys.stdin.readline()
            if re.match(READ_STRING,line) :
                reading = True
                line = ''
            elif re.match(EXIT_STRING,line):
                return
            else :
                script = Script(line)
                script.execute(stack,context)
    

# default call to main if we're at the top level
if __name__ == "__main__" :
    main()
