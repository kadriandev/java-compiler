*	generate prelude
0:     LD 6, 0(0)	load gp with maxaddr
1:     LDA 5, 0(6)	copy gp to fp
2:     ST 0, 0(0)	clear content at loc 0
*	generate i/o routines
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
*	end prelude
*	generate code
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
15:     ST 5, -2(5)	    push ofp
16:     LDA 5, 0(5)	   push frame
17:     LDA 0, 1(7)	     load ac with ret ptr
18:     LDA 7, -15(7)	jump to fn body
19:     LD 5, -2(5)	  pop frame
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
*	-> id
*	looking up id: x
27:     LD 0, -2(5)	load id value
*	<- id
28:     ST 0, -4(5)	op: push left
*	-> constant
29:     LDC 0, 1(0)	load const
*	<- constant
30:     LD 0, -4(5)	
31:     LD 1, -5(5)	
32:     SUB 0, 0, 1	
33:     ST 0, -3(5)	
*	<- op
*	while: jump to end belongs here
*	-> compound statement
*	-> op
*	-> id
*	looking up id: fac
35:     LDA 0, -3(5)	load id address
*	<- id
36:     ST 0, -4(5)	   op: push left
*	-> id
*	looking up id: fac
37:     LD 0, -3(5)	load id value
*	<- id
38:     ST 0, -6(5)	op: push left
*	-> id
*	looking up id: x
39:     LD 0, -2(5)	load id value
*	<- id
40:     ST 0, -7(5)	op: push left
41:     LD 0, -6(5)	
42:     LD 1, -7(5)	
43:     MUL 0, 0, 1	
44:     ST 0, -5(5)	
45:     LD 1, -4(5)	op: load left
46:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: x
47:     LDA 0, -2(5)	load id address
*	<- id
48:     ST 0, -4(5)	   op: push left
*	-> id
*	looking up id: x
49:     LD 0, -2(5)	load id value
*	<- id
50:     ST 0, -6(5)	op: push left
*	-> constant
51:     LDC 0, 1(0)	load const
*	<- constant
52:     LD 0, -6(5)	
53:     LD 1, -7(5)	
54:     SUB 0, 0, 1	
55:     ST 0, -5(5)	
56:     LD 1, -4(5)	op: load left
57:     ST 0, 0(1)	assign: store value
*	<- op
58:     LDA 7, -32(7)	jump back to start of while loop
27:     JGT 0, 6(7)	
*	-> id
*	looking up id: fac
59:     LD 0, -3(5)	load id value
*	<- id
60:     ST 0, -1(5)	op: push left
61:     ST 0, -3(5)	
*	-> call of function: output
62:     ST 5, -2(5)	    push ofp
63:     LDA 5, 0(5)	   push frame
64:     LDA 0, 1(7)	     load ac with ret ptr
65:     LDA 7, -59(7)	jump to fn body
66:     LD 5, -2(5)	  pop frame
*	<- call
67:     LD 7, -1(5)	return back to caller
11:     LDA 7, 56(7)	jump around fn body
*	generate finale
68:     ST 5, -1(5)	push ofp
69:     LDA 5, 0(5)	push frame
70:     LDA 0, 1(7)	load ac with ret ptr
71:     LDA 7, -61(7)	jump to main loc
72:     LD 5, 0(5)	pop frame
73:     HALT 0, 0, 0	halt program
