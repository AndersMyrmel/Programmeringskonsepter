% Find the last element of a list.

lastElement([Head]) :- write(Head).
lastElement([_|Tail]):-
    lastElement(Tail).

% Assume given a set of facts of the form father(name1,name2) (name1 is the father of name2).
%   Define a predicate brother(X,Y) which holds iff X and Y are brothers.
%   Define a predicate cousin(X,Y) which holds iff X and Y are cousins.
%   Define a predicate grandson(X,Y) which holds iff X is a grandson of Y.
%   Define a predicate descendent(X,Y) which holds iff X is a descendent of Y.

father(name1,name3).
father(name2,name4).
father(name5, name1).
brother(name1, name2).

brother(X,Y) :- 
    father(Z,X), 
    father(Z,Y), 
    not(X=Y).

cousin(X,Y) :-
    father(Z,X),
    father(W,Y),
    brother(Z,W).

grandson(X,Y) :-
    father(Y,Z),
    father(Z,X).

descendent(X,Y) :-
    father(Y,X).
descendent(X,Y) :-
    grandson(X,Y).
