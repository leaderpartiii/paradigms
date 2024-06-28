divisors(N, Size, R) :-
    divi(N, 1, 0, Size, [],  [], R).

divi(N, D, Size, ResSize, TempR, TempInv, R) :-
	D * D > N, !,
	append(TempR, TempInv, R),
	ResSize is Size.

divi(N, D, Size, ResSize, TempR, TempInv, R) :-
    D * D < N,
    0 is mod(N, D), !,
    D1 is D + 1, Size1 is Size + 2, Inv is N / D,
    append(TempR, [D], Temp),
    divi(N, D1, Size1, ResSize, Temp, [Inv | TempInv] ,  R).

divi(N, D, Size, ResSize, TempR, TempInv, R) :-
	D * D < N,
	\+ (0 is mod(N, D)), !,
    D1 is D + 1,
    divi(N, D1, Size, ResSize, TempR, TempInv, R).

divi(N, D, Size, ResSize, TempR, TempInv, R) :-
    N is D * D, !,
    Size1 is Size + 1, D1 is D + 1,
    append(TempR, [D], Temp),
    divi(N, D1, Size1, ResSize, Temp, TempInv, R).

diviSize(N, D, Size) :- D * D > N, Size is 2.

diviSize(N, D, Size) :-
	D * D < N,
    0 is mod(N, D), !,
    D1 is D + 1, Size1 is Size + 2, Size < 3,
    diviSize(N, D1, Size1).

diviSize(N, D, Size) :-
    D * D < N,
    D1 is D + 1,
    diviSize(N, D1, Size).

diviSize(N, D, Size) :-
    N is D * D, !,
    Size1 is Size + 1, D1 is D + 1,
    Size1 is 2.

prime(N) :-
   diviSize(N, 1, 0).

composite(N) :-
    \+ prime(N).

multiplying(N, [], Mul, First) :- N is Mul, !.

multiplying(N, [H | T], Mul, First) :-
    H > First, !,
    prime(H),
    Mul1 is Mul * H,
    multiplying(N, T, Mul1, H), !.

multiplying(N, [H | T], Mul, First) :-
    H is First,
    Mul1 is Mul * H,
    multiplying(N, T, Mul1, H), !.

prime_divisor(N, []) :- N is 1, !.

prime_divisor(N, [H | T]) :-
  prime(H),
  multiplying(N, T, H, H), !.

dividing_until(N, Divider, CurrProduct, InputDivs, OutputDivs):-
	0 is mod(N, Divider), !,
	N1 is N/Divider,
	CurrProduct1 is CurrProduct * Divider,
	dividing_until(N1, Divider, CurrProduct, [Divider | InputDivs], OutputDivs), !.

dividing_until(N, Divider, CurrProduct, InputDivs, OutputDivs) :- OutputDivs = InputDivs.

select_prime_divisors(N, [], Divs, CurrProduct, Divisors) :- Divisors = Divs, !.
select_prime_divisors(N, [], Divs, N, Divisors) :- Divisors = Divs, !.

select_prime_divisors(N, [H | T], Divs, CurrProduct, Divisors) :-
    prime(H), !,
    dividing_until(N, H, CurrProduct, [],TempDivs),
    append(Divs, TempDivs, Result),
    select_prime_divisors(N, T, Result, CurrProduct, Divisors), !.

select_prime_divisors(N, [H | T], Divs, CurrProduct, Divisors) :-
    select_prime_divisors(N, T, Divs, CurrProduct, Divisors), !.

filling_divi(N, Divs, Divisors) :-
    divisors(N, Size, [_ | T]), select_prime_divisors(N, T, Divs, 1,  Divisors), !.

%if Divisors && N is not variables.
prime_divisors(N, Divisors) :-
    \+ var(Divisors), \+ var(N), !,
    prime_divisor(N, Divisors).
%if !Divisors && N is not variables.
prime_divisors(N, Divisors) :-
	var(Divisors), !,
	filling_divi(N, [], Divisors), !.
%hard version
prime_divisors(N, Divisors) :-
	var(N), !,
	multiplying(N, Divisors, 1, 1), !.


is_prime(1 ,Curr ,  R) :- R is Curr, !.

is_prime(N, Curr ,R) :-
    prime(N), !, Curr1 is  Curr + 1, N1 is N - 1, is_prime(N1, Curr1, R).

is_prime(N,Curr ,  R) :-
    N1 is N - 1, is_prime(N1, Curr, R).

prime_index(N, R) :-
    prime(N),
    is_prime(N, 0, R), !.