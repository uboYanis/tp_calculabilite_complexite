grammar ExprEval;

@header {
    package org.example;
}

@parser::members { }

start returns [int val]: e=expression EOF { $val=$e.val; };

expression returns [int val]:
  left=expression op=MUL right=expression
    {
        //if(!$left.text.isEmpty() && !$op.text.isEmpty() && !$right.text.isEmpty())
        //    System.out.println($left.text + "  " + $op.text + "  " + $right.text);
        $val = $left.val * $right.val;
    }
 | left=expression op=DIV right=expression
    {
        //if(!$left.text.isEmpty() && !$op.text.isEmpty() && !$right.text.isEmpty())
        //    System.out.println($left.text + "  " + $op.text + "  " + $right.text);
        $val = $left.val / $right.val;
    }
 | left=expression op=PLUS right=expression
    {
        //if(!$left.text.isEmpty() && !$op.text.isEmpty() && !$right.text.isEmpty())
        //    System.out.println($left.text + "  " + $op.text + "  " + $right.text);
        $val = $left.val + $right.val;
    }
 | left=expression op=MINUS right=expression
    {
        //if(!$left.text.isEmpty() && !$op.text.isEmpty() && !$right.text.isEmpty())
        //    System.out.println($left.text + "  " + $op.text + "  " + $right.text);
        $val = $left.val - $right.val;
    }
 | i=INT
    {
        //System.out.println($i.text);
        $val = Integer.parseInt($i.text);
    }
 ;

/*  ************ LEXER **************** */

WS      : [\t\r\n]+ -> skip;   /* Skip whitespaces, tabs, newlines */
INT     : [0-9]+ ;
MUL     : '*';
DIV     : '/';
PLUS    : '+';
MINUS   : '-';
