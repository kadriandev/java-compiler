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
*	processing global variable: y
*	<- vardecl
*	processing function: gcd
*	jump around function body here
12:     ST 0, -1(5)	store return
*	processing local variable: u
*	processing local variable: v
*	-> compound statement
*	-> if
*	-> op
*	-> id
*	looking up id: v
13:     LD 0, -3(5)	load id value
*	<- id
14:     ST 0, -5(5)	op: push left
*	-> constant
15:     LDC 0, 0(0)	load const
*	<- constant
16:     LD 1, -5(5)	op: load left
17:     SUB 0, 1, 0	op ==
18:     JGT 0, 2(7)	br if true
19:     LDC 0, 0(0)	false case
20:     LDA 7, 1(7)	unconditional jump
21:     LDC 0, 1(0)	true case
*	<- op
*	if: jump to else belongs here
*	-> return
*	-> id
*	looking up id: u
23:     LD 0, -2(5)	load id value
*	<- id
24:     LD 0, -4(5)	return to caller
*	<- return
*	if: jump to end belongs here
22:     JEQ 0, -1(7)	
*	-> return
*	-> call of function: gcd
*	-> id
*	looking up id: v
26:     LD 0, -3(5)	load id value
*	<- id
27:     ST 0, -7(5)	store arg val
*	-> op
*	-> id
*	looking up id: u
28:     LD 0, -2(5)	load id value
*	<- id
29:     ST 0, -2(5)	op: push left
*	-> op
*	-> op
*	-> id
*	looking up id: u
30:     LD 0, -2(5)	load id value
*	<- id
31:     ST 0, -4(5)	op: push left
*	-> id
*	looking up id: v
32:     LD 0, -3(5)	load id value
*	<- id
33:     LD 1, -4(5)	op: load left
34:     DIV 0, 1, 0	op /
*	<- op
35:     ST 0, -4(5)	op: push left
*	-> id
*	looking up id: v
36:     LD 0, -3(5)	load id value
*	<- id
37:     LD 1, -4(5)	op: load left
38:     MUL 0, 1, 0	op *
*	<- op
39:     LD 1, -2(5)	op: load left
40:     SUB 0, 1, 0	op -
*	<- op
41:     ST 0, -8(5)	store arg val
42:     ST 5, -5(5)	    push ofp
43:     LDA 5, -5(5)	   push frame
44:     LDA 0, 1(7)	     load ac with ret ptr
45:     LDA 7, -46(7)	jump to fn body
46:     LD 5, 0(5)	  pop frame
*	<- call
47:     LD 0, -1(5)	return to caller
*	<- return
25:     LDA 7, 22(7)	
*	<- if
*	<- compound statement
48:     LD 7, -1(5)	return back to caller
11:     LDA 7, 37(7)	jump around fn body
*	<- fundecl
*	processing function: main
*	jump around function body here
50:     ST 0, -1(5)	store return
*	-> compound statement
*	processing local variable: x
*	-> op
*	-> id
*	looking up id: x
51:     LDA 0, -2(5)	load id address
*	<- id
52:     ST 0, -4(5)	   op: push left
*	-> call of function: input
53:     ST 5, -3(5)	    push ofp
54:     LDA 5, -3(5)	   push frame
55:     LDA 0, 1(7)	     load ac with ret ptr
56:     LDA 7, -53(7)	jump to fn body
57:     LD 5, 0(5)	  pop frame
*	<- call
58:     LD 1, -4(5)	op: load left
59:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: y
60:     LDA 0, 0(5)	load id address
*	<- id
61:     ST 0, -4(5)	   op: push left
*	-> constant
62:     LDC 0, 10(0)	load const
*	<- constant
63:     LD 1, -4(5)	op: load left
64:     ST 0, 0(1)	assign: store value
*	<- op
*	-> call of function: output
*	-> call of function: gcd
*	-> id
*	looking up id: x
65:     LD 0, -2(5)	load id value
*	<- id
66:     ST 0, -5(5)	store arg val
*	-> id
*	looking up id: y
67:     LD 0, 0(5)	load id value
*	<- id
68:     ST 0, -6(5)	store arg val
69:     ST 5, -3(5)	    push ofp
70:     LDA 5, -3(5)	   push frame
71:     LDA 0, 1(7)	     load ac with ret ptr
72:     LDA 7, -73(7)	jump to fn body
73:     LD 5, 0(5)	  pop frame
*	<- call
74:     ST 0, -1(5)	store arg val
75:     ST 5, 1(5)	    push ofp
76:     LDA 5, 1(5)	   push frame
77:     LDA 0, 1(7)	     load ac with ret ptr
78:     LDA 7, -72(7)	jump to fn body
79:     LD 5, 0(5)	  pop frame
*	<- call
*	<- compound statement
80:     LD 7, -1(5)	return back to caller
49:     LDA 7, 31(7)	jump around fn body
*	<- fundecl
*	generate finale
81:     ST 5, 0(5)	push ofp
82:     LDA 5, 0(5)	push frame
83:     LDA 0, 1(7)	load ac with ret ptr
84:     LDA 7, -35(7)	jump to main loc
85:     LD 5, 0(5)	pop frame
*	End of execution.
86:     HALT 0, 0, 0	halt program
