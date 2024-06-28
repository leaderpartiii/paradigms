node((Key, Value), Left, Right).%(Key, Value), Left, Right it is a left and right child from

sizeOfList([], S, R) :- R = S, !.
sizeOfList([(_, _) | T], Size, ResSize) :-
    Size1 is Size + 1, sizeOfList(T, Size1, ResSize), !.

concatWhileMiddle([], _, _, TempFirstPart, TempSecondPart, FirstPart, SecondPart) :-
    FirstPart = TempFirstPart, SecondPart = TempSecondPart, !.

updating(Size, SizeTemp,Part, TempPart, (Key, Value)) :-
      SizeTemp is Size + 1, append(Part,[(Key, Value)],TempPart), !.

concatWhileMiddle([(Key, Value) | T], SizeTemp,  Size, TempFirstPart, TempSecondPart, FirstPart, SecondPart) :-
    SizeTemp < Size // 2, !,
    updating(SizeTemp,SizeTemp1, TempFirstPart, TempFirstPart1, (Key, Value) ),
    concatWhileMiddle(T, SizeTemp1,  Size, TempFirstPart1 , TempSecondPart, FirstPart, SecondPart), !.

concatWhileMiddle([(Key, Value) | T], SizeTemp,  Size, TempFirstPart, TempSecondPart, FirstPart, SecondPart) :-
    updating(SizeTemp,SizeTemp1, TempSecondPart, TempSecondPart1, (Key, Value) ),
    concatWhileMiddle(T, SizeTemp1,  Size, TempFirstPart , TempSecondPart1, FirstPart, SecondPart).

twoPartList(List, FirstPart, SecondPart) :-
    sizeOfList(List, 0, ResSize), concatWhileMiddle(List, 0, ResSize, [], [], FirstPart, SecondPart).

map_build([], Result) :- !, Result = nil, !.

%First recur with one element
map_build(List, node((SKey, SValue), Left, Right)) :-
    twoPartList(List, FT, [(SKey, SValue) | ST]),
    sizeOfList(FT, 0, FirstSize),
    FirstSize is 0, !,
    Left = nil, Right = nil, !.

%First recur with not one element
map_build(List, node((SKey, SValue), Left, Right)) :-
    twoPartList(List, [(FKey, FValue) | FT], [(SKey, SValue) | ST]),
    map_build([(FKey, FValue) | FT], Left),
    map_build(ST, Right), !.

map_get(node((Key, Value), _, _), Key, Value) :- !.
map_get(node((QKey, _), _, Right), Key, Value) :-
	QKey < Key, map_get(Right, Key, Value), !.

map_get(node((QKey, _), Left, _), Key, Value) :-
	QKey > Key, map_get(Left, Key, Value).

map_lastKey(node((Key, Value), Left, nil), QKey) :- QKey = Key.

map_lastKey(node((Key, Value), Left, Right), QKey) :-
    map_lastKey(Right, QKey).

map_lastValue(node((Key, Value), Left, nil), QValue) :- QValue = Value.
map_lastValue(node((Key, Value), Left, Right), QValue) :-
    map_lastValue(Right, QValue).

map_lastKeyValue(node((Key, Value), Left, nil), QValue) :- QValue = Value.
map_lastKeyValue(node((Key, Value), Left, Right), QValue) :-
    map_lasttKeyValue(Right, QKey).

map_lastEntry(node((Key, Value), Left, nil), (QKey, QValue)) :- QKey = Key, QValue = Value.
map_lastEntry(node((Key, Value), Left, Right), (QKey, QValue)) :-
    map_lastEntry(Right, (QKey, QValue)).

map_replace(node((Key, Value), Left, Right), Key, QValue, Result) :- Result = node((Key, QValue), Left, Right), !.

map_replace(node((Key, Value), Left, Right), QKey, QValue, Result) :-
    QKey < Key,!,  map_replace(Left, QKey, QValue, QResult), Result = node((Key, Value), QResult, Right).
    
map_replace(node((Key, Value), Left, Right), QKey, QValue, Result) :-
    QKey > Key,!,  map_replace(Right, QKey, QValue, QResult), Result = node((Key, Value), Left, QResult).

map_replace(nil, QKey, QValue, Result) :- Result = nil, !.