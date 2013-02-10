
all: functions structures
	javac onedeefive/Script.java
	javac onedeefive/onedeefive.java

console:
	java onedeefive/onedeefive

test:
	javac onedeefive/test.java
	java onedeefive/test

functions: onedeefive/LangDef.class onedeefive/StackFrame.class
	javac onedeefive/functions/*.java

structures: onedeefive/LangDef.class onedeefive/StackFrame.class
	javac onedeefive/structures/*.java

onedeefive/LangDef.class:
	javac onedeefive/LangDef.java

onedeefive/StackFrame.class:
	javac onedeefive/StackFrame.java

clean:
	cd onedeefive; rm /S *.class *~
