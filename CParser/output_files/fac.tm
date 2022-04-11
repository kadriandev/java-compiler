*	Standard prelude
0:     LD 6, 0(0)	load gp with maxaddr
1:     LDA 5, 0(6)	copy gp to fp
2:     ST 0, 0(0)	clear content at loc 0
*	Jump around i/o routines here
*	code for input routine
4:     ST 0, -1(5)	store return
5:     IN 0, 0, 0	input
6:     LD 7, -1(5)	return to caller
*	code for output routine
7:     ST 0, -1(5)	store return
8:     LD 0, -2(5)	load output value
9:     OUT 0, 0, 0	output
10:     LD 7, -1(5)	return to caller
3:     LDA 7, 7(7)	jump around i/o code
*	End of standard prelude
*	processing function: main
*	jump around function body here
12:     ST 0, -1(5)	store return
*	-> compound statement
*	processing local variable: x
*	processing local variable: fac
*	-> op
*	-> id
*	looking up id: x
13:     LDA 0, -2(5)	load id address
*	<- id
14:     ST 0, -4(5)	   op: push left
*	-> call of function: input
15:     ST 5, -5(5)	    push ofp
16:     LDA 5, -5(5)	   push frame
17:     LDA 0, 1(7)	     load ac with ret ptr
18:     LDA 7, -15(7)	jump to fn body
19:     LD 5, 0(5)	  pop frame
*	<- call
20:     LD 1, -4(5)	op: load left
21:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: fac
22:     LDA 0, -3(5)	load id address
*	<- id
23:     ST 0, -4(5)	   op: push left
*	-> constant
24:     LDC 0, 1(0)	load const
*	<- constant
25:     LD 1, -4(5)	op: load left
26:     ST 0, 0(1)	assign: store value
*	<- op
*	-> while
*	while: jump after body comes back here
*	-> op
*	-> op
*	-> id
*	looking up id: x
27:     LD 0, -2(5)	load id value
*	<- id
28:     ST 1, -3(5)	op: push left
*	-> constant
29:     LDC 0, 1(0)	load const
*	<- constant
30:     SUB 0, 0, 1	
*	<- op
*	<- op
*	while: jump to end belongs here
*	-> compound statement
*	-> op
*	-> id
*	looking up id: fac
32:     LDA 0, -3(5)	load id address
*	<- id
33:     ST 0, -4(5)	   op: push left
*	-> op
*	-> id
*	looking up id: fac
34:     LD 0, -3(5)	load id value
*	<- id
35:     ST 1, -5(5)	op: push left
*	-> id
*	looking up id: x
36:     LD 0, -2(5)	load id value
*	<- id
37:     ST 1, -6(5)	op: push left
38:     MUL 0, 1, 0	op *
*	<- op
39:     LD 1, -4(5)	op: load left
40:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: x
41:     LDA 0, -2(5)	load id address
*	<- id
42:     ST 0, -4(5)	   op: push left
*	-> op
*	-> id
*	looking up id: x
43:     LD 0, -2(5)	load id value
*	<- id
44:     ST 1, -5(5)	op: push left
*	-> constant
45:     LDC 0, 1(0)	load const
*	<- constant
46:     SUB 0, 1, 0	op -
*	<- op
47:     LD 1, -4(5)	op: load left
48:     ST 0, 0(1)	assign: store value
*	<- op
*	<- compound statement
49:     LDA 7, -23(7)	while: absolute jmp to test
27:     JGT 0, 3(7)	while: 
*	<- while
*	-> call of function: output
*	-> id
*	looking up id: fac
50:     LD 0, -3(5)	load id value
*	<- id
51:     ST 1, 0(5)	op: push left
52:     ST 0, -8(5)	
53:     ST 5, -6(5)	    push ofp
54:     LDA 5, -6(5)	   push frame
55:     LDA 0, 1(7)	     load ac with ret ptr
56:     LDA 7, -50(7)	jump to fn body
57:     LD 5, 0(5)	  pop frame
*	<- call
*	<- compound statement
58:     LD 7, -1(5)	return back to caller
11:     LDA 7, 47(7)	jump around fn body
*	<- fundecl
*	generate finale
59:     ST 5, -1(5)	push ofp
60:     LDA 5, 0(5)	push frame
61:     LDA 0, 1(7)	load ac with ret ptr
62:     LDA 7, -52(7)	jump to main loc
63:     LD 5, 0(5)	pop frame
*	End of execution.
64:     HALT 0, 0, 0	halt program
