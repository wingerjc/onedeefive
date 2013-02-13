
# redefine for recursive deletion
# on my system I use an aliased del (dos/win32) hence /S
RMFLAGS=/S

# general compilation step
all: functions structures
	javac onedeefive/Script.java
	javac onedeefive/onedeefive.java

# build a jar file from the java source
jar: onedeefive.jar

onedeefive.jar:
	jar -cfe onedeefive.jar onedeefive/onedeefive onedeefive

# run the jar file
runjar: onedeefive.jar
	java -jar onedeefive.jar

# run the program on the command line
console:
	java onedeefive/onedeefive

# run the java test script
test:
	javac onedeefive/test.java
	java onedeefive/test

# build specific parts of the program
functions: onedeefive/LangDef.class onedeefive/StackFrame.class
	javac onedeefive/functions/*.java

structures: onedeefive/LangDef.class onedeefive/StackFrame.class
	javac onedeefive/structures/*.java

onedeefive/LangDef.class:
	javac onedeefive/LangDef.java

onedeefive/StackFrame.class:
	javac onedeefive/StackFrame.java

# remove backups and class files from the directory
clean:
	rm $(RMFLAGS) *.class *~
