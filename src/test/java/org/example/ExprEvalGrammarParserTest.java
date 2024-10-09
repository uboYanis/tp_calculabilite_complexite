package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExprEvalGrammarParserTest {
  @Test
  void givenValidExpression_whenParsed_thenShouldReturnTrue() {
    ExprEvalGrammarParser parser = new ExprEvalGrammarParser("5*4+2*3");
    assertTrue(parser.parse());
    assertEquals(26, parser.getValue());
  }
}
