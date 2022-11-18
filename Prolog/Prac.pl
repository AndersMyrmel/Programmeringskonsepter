loves(juliet, romeo).
loves(romeo, juliet) :- (juliet, romeo).

male(carl).
male(albert).
male(bob).
female(betsy).
female(alice).

parent(carl, albert).
parent(albert, bob).
parent(albert, betsy).

happy(albert).
happy(alice).
with_albert(alice).

runs(albert) :-
    happy(albert).

dances(alice) :-
    happy(alice),
    with_albert(alice).

does_alice_dance :- dances(alice),
    write('When Alice is happy and with Albert she dances').

get_grandparent :-
    parent(X, Y),
    parent(Y, bob),
    format('~w ~s grandparent ~n', [X, "is the"]).

brother(bob, alice).

grand_parent(X, Y) :-
    parent(Z, X),
    parent(Y, Z).

blushes(X) :- human(X).
human(derek).

stabs(tybalt,mercutio,sword).
hates(romeo, X) :- stabs(X, mercutio, sword).

what_grade(5) :-
    write('Go to kindergarten').

what_grade(6) :-
    write('Go to first grade').

what_grade(Other) :-
    Grade is Other - 5,
    format('Go to grade ~w', [Grade]).

owns(albert, pet(cat, tom)).