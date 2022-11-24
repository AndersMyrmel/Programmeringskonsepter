main :-
    open('Unequal.txt', read, Str),
    read_file(Str,Lines),
    close(Str),
    write(Lines), nl.

read_file(Stream, []) :-
    at_end_of_stream(Stream).

read_file(Stream,[X|L]) :-
    \+ at_end_of_stream(Stream),
    read_line_to_codes(Stream,Codes),
    atom_chars(X, Codes),
    read_file(Stream,L), !.

alt :-
    open('Unequal.txt', read, Stream),
    get_char(Stream, Char1),
    process_the_stream(Char1, Stream),
    close(Stream).

process_the_stream(end_of_file, _) :- !.

process_the_stream(Char, Stream) :-
    write(Char),
    get_char(Stream, Char2),
    process_the_stream(Char2, Stream).

getNumOfPuzzles :-
    open('Unequal.txt', read, File).
    read_until_stop(File),
    close(File).

read_until_stop('Unequal.txt', [L|Lines]) :-
    read_line_to_codes(File, Codes), Codes \= end_of_file,
    atom_codes(L, Codes),
    L \= size,
    !, read_until_stop(File, Lines).
read_until_stop(_, []).


