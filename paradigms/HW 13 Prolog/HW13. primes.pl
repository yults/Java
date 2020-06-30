is_divider(N, X) :- 0 is N mod X.

find_dividers(N, I) :- I >= sqrt(N), \+ is_divider(N, I).
find_dividers(N, I) :- I < sqrt(N), \+ is_divider(N, I), I1 is I + 1, find_dividers(N, I1).

min_pd(N, K, R) :- K =< N, is_divider(N, K), R is K, !.
min_pd(N, K, R) :- K =< N, \+ is_divider(N, K), K1 is K + 1, min_pd(N, K1, R1), R is R1.

prime(N) :- N > 1, find_dividers(N, 2).
prime(2).
composite(N) :- N > 1, \+ prime(N).

next_prime(X, R) :- prime(X), R is X.
next_prime(X, R) :- composite(X), X1 is X + 1, next_prime(X1, R1), R is R1.
n_prime(N, X, Pp, Np, R) :- X is N, R is Pp.
n_prime(N, X, Pp, Np, R) :- X < N, next_prime(Pp + 1, Np), X1 is X + 1, n_prime(N, X1, Np, Np1, R1), R is R1.

nth_prime(N, P) :- n_prime(N, 1, 2, _, P).

prime_divisors(1, []) :- !.
prime_divisors(N, [H | T]) :- number(N), !, min_pd(N, 2, H), Nx is div(N, H), prime_divisors(Nx, T).

cmp([], _) :- !.
cmp([H | T], Hf) :- Hf =< H, prime(H), prime(Hf).
find_n([], R) :- R is 1.
find_n([H | T], R) :- cmp(T, H), find_n(T, R1), R is R1 * H.

prime_divisors(N, [H | T]) :- find_n([H | T], N).