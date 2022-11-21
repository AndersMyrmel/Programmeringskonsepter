lastElement([Head]) :- write(Head).
lastElement([_|Tail]):-
    lastElement(Tail).