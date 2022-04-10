JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-cp /usr/share/java/cup.jar:.
CUP=cup
CC = gcc


all: Main.class tm

Main.class: absyn/*.java absyn/*/*.java parser.java sym.java Lexer.java ShowTreeVisitor.java SemanticAnalyzer.java CodeGenerator.java Scanner.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: clang.flex
	$(JFLEX) clang.flex

parser.java: clang.cup
	#$(CUP) -dump -expect 3 clang.cup
	$(CUP) -expect 3 clang.cup

tm: tm.c
	$(CC) $(CFLAGS) tm.c -o tm

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*/*.class absyn/*.class *~ output_files/* tm
