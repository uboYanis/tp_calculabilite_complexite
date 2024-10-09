package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.abstraction.AbstractGrammarParser;

public class ExprEvalGrammarParser extends AbstractGrammarParser<ExprEvalParser> {

  private ParseTree parseTree;

  public ExprEvalGrammarParser(String input) {
    super(input);
  }

  @Override
  protected ExprEvalParser createParser(CommonTokenStream tokens) {
    return new ExprEvalParser(tokens);
  }

  @Override
  protected ParseTree startParser(ExprEvalParser parser) {
    return parser.start();
  }

  @Override
  protected ExprEvalLexer createLexer(String input) {
    return new ExprEvalLexer(CharStreams.fromString(input));
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
    if (parseTree instanceof ExprEvalParser.StartContext context) {
      return context.val;
    }
    return null;
  }

  @Override
  protected String getGrammarName() {
    return "ExprEval";
  }
}
