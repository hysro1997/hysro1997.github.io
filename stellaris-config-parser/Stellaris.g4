grammar Stellaris;

file
   : (pair|var)*
   ;

map
   : '{' (pair)* '}'
   ;

pair
   : BAREWORD SPECIFIER value
   ;

var
   : VARIABLE SPECIFIER NUMBER
   ;

array
   : '{' value+ '}'
   ;

value
   : NUMBER
   | BOOLEAN
   | DATE
   | STRING
   | VARIABLE
   | BAREWORD
   | map
   | array
   ;

BOOLEAN
   : 'yes'
   | 'no'
   | 'true'
   | 'false'
   ;

VARIABLE
   : '@'([A-Za-z][A-Za-z_0-9.%-]*)
   ;

SPECIFIER
   : '=' | '<>' | '>' | '<' | '<=' | '>=' ;

NUMBER
   : '-'?[0-9]+'%'
   | '-'?[0-9]+'.'[0-9]+
   | '-'?[0-9]+
   ;

DATE
   : [0-9]+'.'[0-9]+'.'[0-9]+;

BAREWORD
   : [A-Za-z][@A-Za-z_0-9.%-]*
   ;

STRING
   : '"'(~["])*'"'
   ;

WS
   : [ \t\n\r]+ -> skip
   ;

LINE_COMMENT
   : '#'~[\r\n]* -> channel(HIDDEN)
   ;