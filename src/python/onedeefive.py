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

from Script import Script


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
# @todo Read and change i/o sources based from cli arguments
#
def main():
    consoleMode()
    
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
