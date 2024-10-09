# TP2 de Calculabilité et Complexité (C&C)

## Exercice 1 :

Fournir la grammaire qui reconnaît les mots de la forme suivante: **_a<sup>n</sup>b<sup>n</sup>c<sup>2n</sup>_** avec **_n > 0_**.

### Solution Exercice 1 :

#### La grammaire

La grammaire pour le langage **_a<sup>n</sup>b<sup>n</sup>c<sup>2n</sup>_** avec **_n > 0_**, peut être définie comme suit:

* **_Variables:_**
  * `S` (symbole de départ)
  * `A` (pour les `a`)
  * `B` (pour les `b`)
  * `C` (pour les `c`)

* **_Alphabet:_**
  * {`a`, `b`, `c`}

* **_Règles de production:_**
  * `S -> A B C`
  * `A -> a A | a`
  * `B -> b B | b`
  * `C -> c C c | c c`

* **_Symbole de départ:_**
  * `S`

* **_Symboles terminaux:_**
  * a, b, c

#### Explication des règles de production

- **`S -> A B C`** : La règle de départ qui indique que pour générer une chaîne valide, on doit avoir une séquence d'`a`, suivie d'une séquence de `b`, et enfin d'une séquence de `c`.

- **`A -> a A | a`** : Cette règle génère au moins un `a`, et peut en générer plusieurs en appelant récursivement `A`.

- **`B -> b B | b`** : De manière similaire, cette règle génère au moins un `b`, avec la possibilité d'en générer plusieurs.

- **`C -> c C c | c c`** : Cette règle génère des `c` par paires. Pour chaque `c` généré, un autre `c` doit suivre, garantissant ainsi que le nombre de `c` soit toujours le double de `n` (le nombre d'`a` ou de `b`).


#### Grammaire ANTLR

**AnBnC2n.g4**
```
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
```


## Exercice 2 :

Créer un programme _**reconnaissant**_ et _**évaluant**_ les expressions booléennes avec
les symboles `true`, `false` et les opérateur `&&` et `||`; ou le`||` est prioritaire sur `&&`.

### Solution Exercice 2 :

> **INFO :** Pour mieux comprendre, je vais diviser la réponse en deux parties :
> 1) La grammaire utilisée pour la reconnaissance.
> 2) L'évaluation des formules et l'obtention des résultats.

### Partie 1 (La grammaire utilisée pour la reconnaissance):

### La grammaire

### Variables

- **S** (symbole de départ)
- **OR** (pour les expressions OR)
- **AND** (pour les expressions AND)
- **PRIMARY** (pour les expressions primaires)

### Alphabet

{ `true`, `false`, `(`, `)`, `||`, `&&` }

### Règles de production

1. **S** → **OR**
2. **OR** → **AND** | `||` **AND**
3. **AND** → **PRIMARY** | `&&` **PRIMARY**
4. **PRIMARY** → `true`
5. **PRIMARY** → `false`
6. **PRIMARY** → `(` **OR** `)`

#### Exemple d'utilisation
* `true`
* `false`
* `(true && false) || (false && true)`
* `((true || false) && (false || true))`


#### Grammaire ANTLR

**BooleanExpr.g4**
```
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
```

### Partie 2 (L'évaluation des formules et l'obtention des résultats):

**EvalBooleanExpr.g4**
```
grammar EvalBooleanExpr;

@header {
    package org.example;
}

@members {
    // Helper method to evaluate OR, AND, and primary expressions.
    boolean eval(boolean left, String op, boolean right) {
        switch (op) {
            case "||":
                return left || right;
            case "&&":
                return left && right;
            default:
                throw new UnsupportedOperationException("Unknown operator: " + op);
        }
    }
}

// Start rule
start returns [boolean val]
    : orExpr EOF { $val = $orExpr.val; }
    ;

// OR Expression
orExpr returns [boolean val]
    : left=andExpr
      ( '||' right=andExpr { $val = eval($val, "||", $right.val); } )*
      { $val = $left.val; } // No null check needed, always use the left value if no operator
    ;

// AND Expression
andExpr returns [boolean val]
    : left=primary
      ( '&&' right=primary { $val = eval($val, "&&", $right.val); } )*
      { $val = $left.val; } // No null check needed, always use the left value if no operator
    ;

// Primary expression
primary returns [boolean val]
    : 'true'  { $val = true; }
    | 'false' { $val = false; }
    | '(' orExpr ')' { $val = $orExpr.val; }
    ;

// Lexer rules
WS: [ \t\n\r]+ -> skip; // Ignore whitespace

```