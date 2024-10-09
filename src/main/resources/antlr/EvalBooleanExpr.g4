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
      ( '||' right=andExpr { $val = eval($left.val, "||", $right.val); } )*
      // This assignment is now guaranteed to provide the correct final value
      { $val = $left.val; }
    ;

// AND Expression
andExpr returns [boolean val]
    : left=primary
      ( '&&' right=primary { $val = eval($left.val, "&&", $right.val); } )*
      // The last evaluated result is assigned here
      { $val = $left.val; }
    ;

// Primary expression
primary returns [boolean val]
    : 'true'  { $val = true; }
    | 'false' { $val = false; }
    | '(' orExpr ')' { $val = $orExpr.val; }
    ;

// Lexer rules
WS: [ \t\n\r]+ -> skip; // Ignore whitespace
