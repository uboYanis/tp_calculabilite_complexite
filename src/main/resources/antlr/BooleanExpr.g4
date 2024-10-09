grammar BooleanExpr;

@header {
    package org.example;
}

@parser::members { }

// Règle de départ
start: orExpr EOF;

// Expression OR
orExpr: andExpr ('||' andExpr)*;

// Expression AND
andExpr: primary ('&&' primary)*;

// Expression primaire
primary: 'true'
       | 'false'
       | '(' orExpr ')';

// Lexer rules
WS: [ \t\n\r]+ -> skip; // Ignore whitespace