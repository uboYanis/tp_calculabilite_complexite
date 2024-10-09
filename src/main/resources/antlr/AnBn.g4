grammar AnBn;

@header {
    package org.example;
}

@parser::members { }

start: s EOF;

s: 'a' s 'b'
 |
 ;

/*  ************ LEXER **************** */

CHARS: [c-z,A-Z] -> skip ; //skip chars

SPACE: [ \t\r\n] -> skip ; // skip spaces, tabs, newlines

OTHER: . ;
