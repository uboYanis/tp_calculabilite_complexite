grammar ExprEval2;

@header {
    package org.example;
    import java.util.stream.*;
    import static org.example.utils.Share.*;
}

@parser::members { }

start returns [int val]: e=add_expr EOF { $val=$e.val; };

/* level 0 */
primary returns [int val] :
	   i=INT                    { $val = Integer.parseInt($i.text); }
	|  LPAREN e=add_expr RPAREN { $val=$e.val; }
	;

/* level 1 */
unary_expr returns [int val] @init{boolean empty = true;}:
      (opS+=MINUS {empty = false;})? primS=primary
        {
            // if no unary_operator
	    if (empty)
                $val=$primS.val;
            else
                $val=-$primS.val;
            // Note that we CALCULATE -X (where X is the integer) but another solution could be
            // lexing -X and thus not have to calculate -X in the parsing (but in the lexer)
        }
    |  primM=primary (opM+=Multi_unary_operator)*
        {
            // Une manière de faire avec un stream
            /* $val = ($opM).stream().reduce($primM.val,
                (subtotal, element) ->
                    switch (element.getText()) {
                     case "!" -> fact(subtotal);
                     case "%" -> subtotal/100;
                     default  -> throw new ArithmeticException("Unknow operator "+element.getText());
                    }, (e1,e2)->(e1*e2));
            */

            $val = $primM.val;
            String[] subOp = ($opM).stream().map(e->e.getText()).toArray(String[]::new);
            for(int i=0;i<subOp.length;i++)
                switch (subOp[i]) {
                    case "!" -> $val = fact($val);
                    case "%" -> $val = $val/100;
                    default  -> throw new ArithmeticException("Unknow operator "+subOp[i]);
                }
        }
    ;

/* level 2 */
mult_expr returns [int val]:
      sub=unary_expr (op+=(MUL | DIV) mult+=unary_expr)*
        {
            // Une version améliorée (?) serait de fusionner les 2 streams (des pairs) puis de parcourir ...
            $val = $sub.val;
            int[] subVal = ($mult).stream().map(e->e.val).mapToInt(Integer::intValue).toArray();
            String[] subOp = ($op).stream().map(e->e.getText()).toArray(String[]::new);
            for(int i=0;i<subVal.length;i++)
                switch (subOp[i]) {
                    case "*" -> $val *= subVal[i];
                    case "/" -> $val /= subVal[i];
                    default  -> throw new ArithmeticException("Unknow operator "+subOp[i]);
                }

        }
        ;

/* level 3 */
add_expr returns [int val]:
      sub=mult_expr (op+=(PLUS | MINUS) sum+=mult_expr)*
        {
            $val = $sub.val;
            int[] subVal = ($sum).stream().map(e->e.val).mapToInt(Integer::intValue).toArray();
            String[] subOp = ($op).stream().map(e->e.getText()).toArray(String[]::new);
            for(int i=0;i<subVal.length;i++)
                switch (subOp[i]) {
                    case "+" -> $val += subVal[i];
                    case "-" -> $val -= subVal[i];
                    default  -> throw new ArithmeticException("Unknow operator "+subOp[i]);
                }
        }
    ;


/*  ************ LEXER **************** */


Multi_unary_operator: '!' | '%';
WS      : [\t\r\n]+ -> skip;   /* Skip whitespaces, tabs, newlines */
INT     : [0-9]+ ;
MUL     : '*';
DIV     : '/';
PLUS    : '+';
MINUS   : '-';
LPAREN  : '(';
RPAREN  : ')';

ErrorCharacter : . ;
