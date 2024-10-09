grammar AnBnC2n;

@header {
    package org.example;
}
@parser::members { }

// Règle de départ
start
    : aBlock bBlock cBlock EOF // Séquence qui correspond à a^n b^n c^{2n}
    ;

// Bloque pour a^n
aBlock
    : 'a' aBlock
    | 'a' // Au moins un 'a'
    ;

// Bloque pour b^n
bBlock
    : 'b' bBlock
    | 'b' // Au moins un 'b'
    ;

// Bloque pour c^{2n}
cBlock
    : 'c' cBlock 'c' // Chaque 'c' doit être suivi d'un autre 'c'
    | 'c' 'c' // Au moins deux 'c'
    ;

SPACE: [ \t\r\n] -> skip ; // skip spaces, tabs, newlines