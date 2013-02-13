
all: functions structures
	javac onedeefive/Script.java
	javac onedeefive/onedeefive.java

jar:
	jar -cfe onedeefive.jar onedeefive/onedeefive onedeefive

runjar:
	java -jar onedeefive.jar

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
