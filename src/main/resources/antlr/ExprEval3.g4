grammar ExprEval3;

@header {
    package org.example;
    import java.util.stream.*;
    import static org.example.utils.Share.*;
    import static org.example.utils.AST.*;
}

@parser::members { }

start returns [Expr val]: e=add_expr EOF { $val=$e.val; };

/* level 0 */
primary returns [Expr val] :
	   i=INT                    { $val =  new ExprINT(Integer.parseInt($i.text)); }
	|  LPAREN e=add_expr RPAREN { $val=$e.val; }
	;

/* level 1 */
unary_expr returns [Expr val] @init{boolean empty = true;}:
    (opS+=MINUS {empty = false;})? primS=primary
        {
            // if no unary_operator
		    if (empty)
                    $val=$primS.val;
            else
                // Si on a affaire en sous-arbre à une feuille, alors c'est un entier
                // et on reconstruit directement un entier (exemple -5)
                // Sinon, c'est une expression quelconque (exemple -(4+5))
                switch ($primS.val) {
                    case ExprINT(int leaf) -> $val=new ExprINT(-leaf);
                    default -> $val = new ExprUNA("-", $primS.val);
                }
        }
    |  primM=primary (opM+=MULTI_UNARY_OPERATOR)*
        {
            $val = $primM.val;
            String[] subOp = ($opM).stream().map(e->e.getText()).toArray(String[]::new);
            for(int i=0;i<subOp.length;i++)
                $val = new ExprUNA(subOp[i], $val);
        }
    ;

/* level 2 */
mult_expr returns [Expr val]:
    sub=unary_expr (op+=(MUL | DIV) mult+=unary_expr)*
        {
            $val = $sub.val;
            Expr[] subVal = ($mult).stream().map(e->e.val).toArray(Expr[]::new);
            String[] subOp = ($op).stream().map(e->e.getText()).toArray(String[]::new);
            for(int i=0;i<subVal.length;i++)
                $val = new ExprBIN($val,subOp[i], subVal[i]);
        }
        ;

/* level 3 */
add_expr returns [Expr val]:
    sub=mult_expr (op+=(PLUS | MINUS) sum+=mult_expr)*
        {
            $val = $sub.val;
            Expr[] subVal = ($sum).stream().map(e->e.val).toArray(Expr[]::new);
            String[] subOp = ($op).stream().map(e->e.getText()).toArray(String[]::new);
            for(int i=0;i<subVal.length;i++)
                $val = new ExprBIN($val,subOp[i], subVal[i]);
        }
    ;


/*  ************ LEXER **************** */

MULTI_UNARY_OPERATOR: '!' | '%';
WS      : [\t\r\n]+ -> skip;   /* Skip whitespaces, tabs, newlines */
INT     : [0-9]+ ;
MUL     : '*';
DIV     : '/';
PLUS    : '+';
MINUS   : '-';
LPAREN  : '(';
RPAREN  : ')';

ErrorCharacter : . ;