% Find the last element of a list.

lastElement([Head]) :- write(Head).
lastElement([_|Tail]):-
    lastElement(Tail).

% Find the last but one element of a list.

secondLast([Head]) :- write(Head).
secondLast([X,_], X) :-
    secondLast([_|T], X) :- secondLast(T, X).