package org.example;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.abstraction.AbstractGrammarParser;

public class AnBnGrammarParser extends AbstractGrammarParser<AnBnParser> {

  public AnBnGrammarParser(String input) {
    super(input);
  }

  @Override
  protected AnBnParser createParser(CommonTokenStream tokens) {
    return new AnBnParser(tokens);
  }

  @Override
  protected ParseTree startParser(AnBnParser parser) {
    return parser.start();
  }

  @Override
  protected AnBnLexer createLexer(String input) {
    return new AnBnLexer(CharStreams.fromString(input));
  }

  @Override
  protected Object getValue() {
    return null;
  }

  @Override
  protected String getGrammarName() {
    return "AnBn";
  }
}
