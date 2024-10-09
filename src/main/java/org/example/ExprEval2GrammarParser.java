package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.abstraction.AbstractGrammarParser;

public class ExprEval2GrammarParser extends AbstractGrammarParser<ExprEval2Parser> {

  private ParseTree parseTree;

  public ExprEval2GrammarParser(String input) {
    super(input);
  }

  @Override
  protected ExprEval2Parser createParser(CommonTokenStream tokens) {
    return new ExprEval2Parser(tokens);
  }

  @Override
  protected ParseTree startParser(ExprEval2Parser parser) {
    return parser.start();
  }

  @Override
  protected ExprEval2Lexer createLexer(String input) {
    return new ExprEval2Lexer(CharStreams.fromString(input));
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
    if (parseTree instanceof ExprEval2Parser.StartContext context) {
      return context.val;
    }
    return null;
  }

  @Override
  protected String getGrammarName() {
    return "ExprEval2";
  }
}
