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
*	processing global variable: x
*	<- vardecl
*	processing function: minloc
*	jump around function body here
12:     ST 0, -1(5)	store return
*	processing local variable: a
*	processing local variable: low
*	processing local variable: high
*	-> compound statement
*	processing local variable: i
*	processing local variable: x
*	processing local variable: k
*	-> op
*	-> id
*	looking up id: k
13:     LDA 0, -7(5)	load id address
*	<- id
14:     ST 0, -7(5)	   op: push left
*	-> id
*	looking up id: low
15:     LD 0, -3(5)	load id value
*	<- id
16:     LD 1, -7(5)	op: load left
17:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: x
18:     LDA 0, -6(5)	load id address
*	<- id
19:     ST 0, -7(5)	   op: push left
*	-> id
*	looking up id: a
20:     LD 0, -2(5)	load id value
*	<- id
*	-> subs
21:     LD 0, -2(5)	load id value
22:     ST 0, -2(5)	store array addr
23:     LD 1, -7(5)	op: load left
24:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: i
25:     LDA 0, -5(5)	load id address
*	<- id
26:     ST 0, -7(5)	   op: push left
*	-> op
*	-> op
*	-> id
*	looking up id: low
27:     LD 0, -3(5)	load id value
*	<- id
28:     ST 0, -8(5)	op: push left
*	-> constant
29:     LDC 0, 1(0)	load const
*	<- constant
30:     LD 1, -8(5)	op: load left
31:     ADD 0, 1, 0	op +
*	<- op
*	<- op
32:     LD 1, -7(5)	op: load left
33:     ST 0, 0(1)	assign: store value
*	<- op
*	-> while
*	while: jump after body comes back here
*	-> op
*	-> op
*	-> id
*	looking up id: i
34:     LD 0, -5(5)	load id value
*	<- id
35:     ST 0, -7(5)	op: push left
*	-> id
*	looking up id: high
36:     LD 0, -4(5)	load id value
*	<- id
37:     LD 1, -7(5)	op: load left
38:     SUB 0, 1, 0	op >
39:     JGT 0, 2(7)	br if true
40:     LDC 0, 0(0)	false case
41:     LDA 7, 1(7)	unconditional jump
42:     LDC 0, 1(0)	true case
*	<- op
*	<- op
*	while: jump to end belongs here
*	-> compound statement
*	-> if
*	-> op
*	-> id
*	looking up id: a
44:     LD 0, -2(5)	load id value
*	<- id
*	-> subs
45:     LD 0, -2(5)	load id value
46:     ST 0, -2(5)	store array addr
47:     ST 0, -7(5)	op: push left
*	-> id
*	looking up id: x
48:     LD 0, -6(5)	load id value
*	<- id
49:     LD 1, -7(5)	op: load left
50:     SUB 0, 1, 0	op >
51:     JGT 0, 2(7)	br if true
52:     LDC 0, 0(0)	false case
53:     LDA 7, 1(7)	unconditional jump
54:     LDC 0, 1(0)	true case
*	<- op
*	if: jump to else belongs here
*	-> compound statement
*	-> op
*	-> id
*	looking up id: x
56:     LDA 0, -6(5)	load id address
*	<- id
57:     ST 0, -8(5)	   op: push left
*	-> id
*	looking up id: a
58:     LD 0, -2(5)	load id value
*	<- id
*	-> subs
59:     LD 0, -2(5)	load id value
60:     ST 0, -2(5)	store array addr
61:     LD 1, -8(5)	op: load left
62:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: k
63:     LDA 0, -7(5)	load id address
*	<- id
64:     ST 0, -8(5)	   op: push left
*	-> id
*	looking up id: i
65:     LD 0, -5(5)	load id value
*	<- id
66:     LD 1, -8(5)	op: load left
67:     ST 0, 0(1)	assign: store value
*	<- op
*	<- compound statement
*	if: jump to end belongs here
55:     JLT 0, -1(7)	
68:     LDA 7, 0(7)	
*	<- if
*	-> op
*	-> id
*	looking up id: i
69:     LDA 0, -5(5)	load id address
*	<- id
70:     ST 0, -8(5)	   op: push left
*	-> op
*	-> op
*	-> id
*	looking up id: i
71:     LD 0, -5(5)	load id value
*	<- id
72:     ST 0, -9(5)	op: push left
*	-> constant
73:     LDC 0, 1(0)	load const
*	<- constant
74:     LD 1, -9(5)	op: load left
75:     ADD 0, 1, 0	op +
*	<- op
*	<- op
76:     LD 1, -8(5)	op: load left
77:     ST 0, 0(1)	assign: store value
*	<- op
*	<- compound statement
78:     LDA 7, -45(7)	while: absolute jmp to test
43:     JEQ 0, 35(7)	
*	<- while
*	-> return
*	-> id
*	looking up id: k
79:     LD 0, -7(5)	load id value
*	<- id
80:     LD 0, -8(5)	return to caller
*	<- return
*	<- compound statement
81:     LD 7, -1(5)	return back to caller
11:     LDA 7, 70(7)	jump around fn body
*	<- fundecl
*	processing function: sort
*	jump around function body here
83:     ST 0, -1(5)	store return
*	processing local variable: a
*	processing local variable: low
*	processing local variable: high
*	-> compound statement
*	processing local variable: i
*	processing local variable: k
*	-> op
*	-> id
*	looking up id: i
84:     LDA 0, -5(5)	load id address
*	<- id
85:     ST 0, -7(5)	   op: push left
*	-> id
*	looking up id: low
86:     LD 0, -3(5)	load id value
*	<- id
87:     LD 1, -7(5)	op: load left
88:     ST 0, 0(1)	assign: store value
*	<- op
*	-> while
*	while: jump after body comes back here
*	-> op
*	-> op
*	-> id
*	looking up id: i
89:     LD 0, -5(5)	load id value
*	<- id
90:     ST 0, -7(5)	op: push left
*	-> op
*	-> id
*	looking up id: high
91:     LD 0, -4(5)	load id value
*	<- id
92:     ST 0, -9(5)	op: push left
*	-> constant
93:     LDC 0, 1(0)	load const
*	<- constant
94:     LD 1, -9(5)	op: load left
95:     SUB 0, 1, 0	op -
*	<- op
96:     LD 1, -7(5)	op: load left
97:     SUB 0, 1, 0	op >
98:     JGT 0, 2(7)	br if true
99:     LDC 0, 0(0)	false case
100:     LDA 7, 1(7)	unconditional jump
101:     LDC 0, 1(0)	true case
*	<- op
*	<- op
*	while: jump to end belongs here
*	-> compound statement
*	processing local variable: t
*	-> op
*	-> id
*	looking up id: k
103:     LDA 0, -6(5)	load id address
*	<- id
104:     ST 0, -8(5)	   op: push left
*	-> call of function: minloc
*	-> id
*	looking up id: a
105:     LD 0, -2(5)	load id value
*	<- id
106:     ST 0, -17(5)	store arg val
*	-> id
*	looking up id: i
107:     LD 0, -5(5)	load id value
*	<- id
108:     ST 0, -18(5)	store arg val
*	-> id
*	looking up id: high
109:     LD 0, -4(5)	load id value
*	<- id
110:     ST 0, -19(5)	store arg val
111:     ST 5, -15(5)	    push ofp
112:     LDA 5, -15(5)	   push frame
113:     LDA 0, 1(7)	     load ac with ret ptr
114:     LDA 7, -115(7)	jump to fn body
115:     LD 5, 0(5)	  pop frame
*	<- call
116:     LD 1, -8(5)	op: load left
117:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: t
118:     LDA 0, -6(5)	load id address
*	<- id
119:     ST 0, -8(5)	   op: push left
*	-> id
*	looking up id: a
120:     LD 0, -2(5)	load id value
*	<- id
*	-> subs
121:     LD 0, -2(5)	load id value
122:     ST 0, -2(5)	store array addr
123:     LD 1, -8(5)	op: load left
124:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: a
125:     LDA 0, -2(5)	load id address
*	<- id
126:     ST 0, -8(5)	   op: push left
*	-> subs
127:     LD 0, -2(5)	load id value
128:     ST 0, -2(5)	store array addr
*	-> id
*	looking up id: a
129:     LD 0, -2(5)	load id value
*	<- id
*	-> subs
130:     LD 0, -2(5)	load id value
131:     ST 0, -2(5)	store array addr
132:     LD 1, -8(5)	op: load left
133:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: a
134:     LDA 0, -2(5)	load id address
*	<- id
135:     ST 0, -8(5)	   op: push left
*	-> subs
136:     LD 0, -2(5)	load id value
137:     ST 0, -2(5)	store array addr
*	-> id
*	looking up id: t
138:     LD 0, -6(5)	load id value
*	<- id
139:     LD 1, -8(5)	op: load left
140:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: i
141:     LDA 0, -5(5)	load id address
*	<- id
142:     ST 0, -8(5)	   op: push left
*	-> op
*	-> op
*	-> id
*	looking up id: i
143:     LD 0, -5(5)	load id value
*	<- id
144:     ST 0, -9(5)	op: push left
*	-> constant
145:     LDC 0, 1(0)	load const
*	<- constant
146:     LD 1, -9(5)	op: load left
147:     ADD 0, 1, 0	op +
*	<- op
*	<- op
148:     LD 1, -8(5)	op: load left
149:     ST 0, 0(1)	assign: store value
*	<- op
*	<- compound statement
150:     LDA 7, -62(7)	while: absolute jmp to test
102:     JEQ 0, 48(7)	
*	<- while
*	<- compound statement
151:     LD 7, -1(5)	return back to caller
82:     LDA 7, 69(7)	jump around fn body
*	<- fundecl
*	processing function: main
*	jump around function body here
153:     ST 0, -1(5)	store return
*	-> compound statement
*	processing local variable: i
*	-> op
*	-> id
*	looking up id: i
154:     LDA 0, -2(5)	load id address
*	<- id
155:     ST 0, -4(5)	   op: push left
*	-> constant
156:     LDC 0, 0(0)	load const
*	<- constant
157:     LD 1, -4(5)	op: load left
158:     ST 0, 0(1)	assign: store value
*	<- op
*	-> while
*	while: jump after body comes back here
*	-> op
*	-> op
*	-> id
*	looking up id: i
159:     LD 0, -2(5)	load id value
*	<- id
160:     ST 0, -4(5)	op: push left
*	-> constant
161:     LDC 0, 10(0)	load const
*	<- constant
162:     LD 1, -4(5)	op: load left
163:     SUB 0, 1, 0	op >
164:     JGT 0, 2(7)	br if true
165:     LDC 0, 0(0)	false case
166:     LDA 7, 1(7)	unconditional jump
167:     LDC 0, 1(0)	true case
*	<- op
*	<- op
*	while: jump to end belongs here
*	-> compound statement
*	-> op
*	-> id
*	looking up id: x
169:     LDA 0, 0(5)	load id address
*	<- id
170:     ST 0, -5(5)	   op: push left
*	-> subs
171:     LD 0, 0(5)	load id value
172:     ST 0, 0(5)	store array addr
*	-> call of function: input
173:     ST 5, -12(5)	    push ofp
174:     LDA 5, -12(5)	   push frame
175:     LDA 0, 1(7)	     load ac with ret ptr
176:     LDA 7, -173(7)	jump to fn body
177:     LD 5, 0(5)	  pop frame
*	<- call
178:     LD 1, -5(5)	op: load left
179:     ST 0, 0(1)	assign: store value
*	<- op
*	-> op
*	-> id
*	looking up id: i
180:     LDA 0, -2(5)	load id address
*	<- id
181:     ST 0, -5(5)	   op: push left
*	-> op
*	-> op
*	-> id
*	looking up id: i
182:     LD 0, -2(5)	load id value
*	<- id
183:     ST 0, -6(5)	op: push left
*	-> constant
184:     LDC 0, 1(0)	load const
*	<- constant
185:     LD 1, -6(5)	op: load left
186:     ADD 0, 1, 0	op +
*	<- op
*	<- op
187:     LD 1, -5(5)	op: load left
188:     ST 0, 0(1)	assign: store value
*	<- op
*	<- compound statement
189:     LDA 7, -31(7)	while: absolute jmp to test
168:     JEQ 0, 21(7)	
*	<- while
*	-> call of function: sort
*	-> id
*	looking up id: x
190:     LD 0, 0(5)	load id value
*	<- id
191:     ST 0, -13(5)	store arg val
*	-> constant
192:     LDC 0, 0(0)	load const
*	<- constant
193:     ST 0, -14(5)	store arg val
*	-> constant
194:     LDC 0, 10(0)	load const
*	<- constant
195:     ST 0, -15(5)	store arg val
196:     ST 5, -11(5)	    push ofp
197:     LDA 5, -11(5)	   push frame
198:     LDA 0, 1(7)	     load ac with ret ptr
199:     LDA 7, -200(7)	jump to fn body
200:     LD 5, 0(5)	  pop frame
*	<- call
*	-> op
*	-> id
*	looking up id: i
201:     LDA 0, -2(5)	load id address
*	<- id
202:     ST 0, -4(5)	   op: push left
*	-> constant
203:     LDC 0, 0(0)	load const
*	<- constant
204:     LD 1, -4(5)	op: load left
205:     ST 0, 0(1)	assign: store value
*	<- op
*	-> while
*	while: jump after body comes back here
*	-> op
*	-> op
*	-> id
*	looking up id: i
206:     LD 0, -2(5)	load id value
*	<- id
207:     ST 0, -4(5)	op: push left
*	-> constant
208:     LDC 0, 10(0)	load const
*	<- constant
209:     LD 1, -4(5)	op: load left
210:     SUB 0, 1, 0	op >
211:     JGT 0, 2(7)	br if true
212:     LDC 0, 0(0)	false case
213:     LDA 7, 1(7)	unconditional jump
214:     LDC 0, 1(0)	true case
*	<- op
*	<- op
*	while: jump to end belongs here
*	-> compound statement
*	-> call of function: output
*	-> id
*	looking up id: x
216:     LD 0, 0(5)	load id value
*	<- id
*	-> subs
217:     LD 0, 0(5)	load id value
218:     ST 0, 0(5)	store array addr
219:     ST 0, -9(5)	store arg val
220:     ST 5, -7(5)	    push ofp
221:     LDA 5, -7(5)	   push frame
222:     LDA 0, 1(7)	     load ac with ret ptr
223:     LDA 7, -217(7)	jump to fn body
224:     LD 5, 0(5)	  pop frame
*	<- call
*	-> op
*	-> id
*	looking up id: i
225:     LDA 0, -2(5)	load id address
*	<- id
226:     ST 0, -5(5)	   op: push left
*	-> op
*	-> op
*	-> id
*	looking up id: i
227:     LD 0, -2(5)	load id value
*	<- id
228:     ST 0, -6(5)	op: push left
*	-> constant
229:     LDC 0, 1(0)	load const
*	<- constant
230:     LD 1, -6(5)	op: load left
231:     ADD 0, 1, 0	op +
*	<- op
*	<- op
232:     LD 1, -5(5)	op: load left
233:     ST 0, 0(1)	assign: store value
*	<- op
*	<- compound statement
234:     LDA 7, -29(7)	while: absolute jmp to test
215:     JEQ 0, 19(7)	
*	<- while
*	<- compound statement
235:     LD 7, -1(5)	return back to caller
152:     LDA 7, 83(7)	jump around fn body
*	<- fundecl
*	generate finale
236:     ST 5, 0(5)	push ofp
237:     LDA 5, 0(5)	push frame
238:     LDA 0, 1(7)	load ac with ret ptr
239:     LDA 7, -87(7)	jump to main loc
240:     LD 5, 0(5)	pop frame
*	End of execution.
241:     HALT 0, 0, 0	halt program
