
import sys
import re

import LangDef

from Script import Script

EXIT_STRING = '^[ \t]*(?i)exit(?i)[ \t]*$'
READ_STRING = '^[ \t]*(?i)script(?i)[ \t]*$'
ENDREAD_STRING = '^[ \t]*(?i)end script(?i)[ \t]*$'

def main():
    consoleMode()
    
def consoleMode():
    reading = False
    line = ''
    stack = list()
    context = dict()
    
    while True:
        if reading:
            cur = sys.stdin.readline()
            if re.match(ENDREAD_STRING, cur) :
                reading = False
                script = Script(line)
                script.execute(stack,context)
            else :
                line = line + cur + '\n'
        else :
            line = sys.stdin.readline()
            if re.match(READ_STRING,line) :
                reading = True
                line = ''
            elif re.match(EXIT_STRING,line):
                return
            else :
                script = Script(line)
                script.execute(stack,context)
    


main()
