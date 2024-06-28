lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

variable(Name, variable(Name)).
const(Value, const(Value)).

op_negate(A, operation(op_negate, A)).

op_cube(A, operation(op_cube, A)).

op_add(A, B, operation(op_add, A, B)).
op_subtract(A, B, operation(op_subtract, A, B)).
op_multiply(A, B, operation(op_multiply, A, B)).
op_divide(A, B, operation(op_divide, A, B)).

operation(op_negate, A, R) :- R is -A.
operation(op_cube, A, R) :- R is A**3.
operation(op_cbrt, A, R) :- A >= 0,!, R is A**(1/3).
operation(op_cbrt, A, R) :- R is -(abs(A)**(1/3)).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.

nonvar(V, T) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

:- load_library('alice.tuprolog.lib.DCGLibrary').

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'])},
  [H],
  digits_p(T).


op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].

op_p(op_cube) --> ['c', 'u', 'b', 'e'].
op_p(op_cbrt) --> ['c', 'b', 'r', 't'].

op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].



whits([], []).

whits_beg_end(H, Value) :- var(H),!,  H = Value.

whits([' ' | T], Value) :-
	!, whits(T, Value).

whits([H | T], Value) :-
	Value1 = T, append([H], Value1, Value).

whits_beg_end([H | T], Value2) :-
	whits([H | T], Value), reverse(Value, Ta), whits(Ta, Value1),reverse(Value1, Value2).


whits_mid([], []).

whits_mid(H, Value) :-  var(H), !, H = Value.

whits_mid([' ', ' ' | T], Value) :-
    whits_mid([' ' | T], Value), !.

whits_mid([' ', H | T], Value) :-
    whits_mid(T, Value1), !, append([' ' , H], Value1, Value).

whits_mid([H | T], Value) :-
    whits_mid(T, Value1), append([H], Value1, Value).


skipWhitespace-->[].

skipWhitespace-->[' '], skipWhitespace.

expr_p(variable(Name)) --> skipWhitespace, [Name], skipWhitespace, { member(Name, [x, y, z]) }.

expr_p(const(Value)) -->
	skipWhitespace,
  { nonvar(Value, number_chars(Value, Chars))},
  digits_p(Chars), skipWhitespace,
  { Chars = [_ | _],(\+ Chars = ['-']), number_chars(Value, Chars)}.

%expr_p(variable(Value)) -->
%  { nonvar(Value, atom_chars(Value, Chars2))},
%  var_p(Chars2),
%  { Chars2 = [_ | _], whits_mid(Chars2, Chars1), whits(Chars1, Chars), atom_chars(Value, Chars)}.


expr_p(operation(op_negate, A)) --> skipWhitespace, ['('], skipWhitespace, op_p(op_negate), [' '], expr_p(A), skipWhitespace, [')'], skipWhitespace.

expr_p(operation(op_cube, A)) --> skipWhitespace, ['('], skipWhitespace, op_p(op_cube), [' '], expr_p(A), skipWhitespace, [')'], skipWhitespace.

expr_p(operation(op_cbrt, A)) --> skipWhitespace, ['('], skipWhitespace, op_p(op_cbrt), [' '], expr_p(A), skipWhitespace, [')'], skipWhitespace.

expr_p(operation(Op, A, B)) -->skipWhitespace, ['('], skipWhitespace , op_p(Op), [' '], expr_p(A), [' '], expr_p(B), [')'], skipWhitespace.


expr_str(E, A) :- ground(E), phrase(expr_p(E), C),atom_chars(A, C), !.

expr_str(E, A) :-   atom(A), atom_chars(A, P), phrase(expr_p(E), P), !.



evaluate(const(Value), Variables, Value).

evaluate(variable(Value), Variables, Result) :-
     lookup(Value, Variables, Result).

evaluate(operation(op_negate, A), Variables, Result) :-
    evaluate(A, Variables, AT), !,
    operation(op_negate, AT, Result).

evaluate(operation(op_cube, A), Variables, Result) :-
    evaluate(A, Variables, AT), !,
    operation(op_cube, AT, Result).

evaluate(operation(op_cbrt, A), Variables, Result) :-
   evaluate(A, Variables, AT), !,
   operation(op_cbrt, AT, Result).

evaluate(operation(Op, A, B), Variables, Result) :-
    evaluate(A, Variables, AT),
    evaluate(B, Variables, BT),
    operation(Op, AT, BT, Result).

prefix_str(S, R) :-
    expr_str(S, R).
