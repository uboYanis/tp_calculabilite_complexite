package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.abstraction.AbstractGrammarParser;

public class EvalBooleanExprGrammarParser extends AbstractGrammarParser<EvalBooleanExprParser> {

    private ParseTree parseTree;

    public EvalBooleanExprGrammarParser(String input) {
        super(input);
    }

    @Override
    protected EvalBooleanExprParser createParser(CommonTokenStream tokens) {
        return new EvalBooleanExprParser(tokens);
    }

    @Override
    protected ParseTree startParser(EvalBooleanExprParser parser) {
        return parser.start();
    }

    @Override
    protected EvalBooleanExprLexer createLexer(String input) {
        return new EvalBooleanExprLexer(CharStreams.fromString(input));
    }

    @Override
    public boolean parse() {
        boolean isValid = super.parse();
        if (isValid) {
            this.parseTree = startParser(initializeParser());
        }
        return isValid;
    }

    @Override
    protected Object getValue() {
        if (parseTree == null) {
            this.parse();
        }
        if (parseTree instanceof EvalBooleanExprParser.StartContext context) {
            return context.val;
        }
        return null;
    }

    @Override
    protected String getGrammarName() {
        return "EvalBooleanExpr";
    }
}
