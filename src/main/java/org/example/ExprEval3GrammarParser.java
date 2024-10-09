package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.abstraction.AbstractGrammarParser;

public class ExprEval3GrammarParser extends AbstractGrammarParser<ExprEval3Parser> {

  private ParseTree parseTree;

  public ExprEval3GrammarParser(String input) {
    super(input);
  }

  @Override
  protected ExprEval3Parser createParser(CommonTokenStream tokens) {
    return new ExprEval3Parser(tokens);
  }

  @Override
  protected ParseTree startParser(ExprEval3Parser parser) {
    return parser.start();
  }

  @Override
  protected ExprEval3Lexer createLexer(String input) {
    return new ExprEval3Lexer(CharStreams.fromString(input));
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
    if (parseTree instanceof ExprEval3Parser.StartContext context) {
      return context.val;
    }
    return null;
  }

  @Override
  protected String getGrammarName() {
    return "ExprEval3";
  }
}
