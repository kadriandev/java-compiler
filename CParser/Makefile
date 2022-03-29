JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-cp /usr/share/java/cup.jar:.
CUP=cup
#JFLEX=~/Projects/jflex/bin/jflex
# CLASSPATH=-cp ~/Users/kyle/Users/kyle/java-cup-bin-11b-20160615/java-cup-11b.jar:.
#CUP=$(JAVA) $(CLASSPATH) java_cup.Main

all: Main.class

Main.class: absyn/*.java absyn/*/*.java parser.java sym.java Lexer.java ShowTreeVisitor.java SemanticAnalyzer.java Scanner.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: clang.flex
	$(JFLEX) clang.flex

parser.java: clang.cup
	#$(CUP) -dump -expect 3 clang.cup
	$(CUP) -expect 3 clang.cup

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*/*.class absyn/*.class *~ output_files/*
