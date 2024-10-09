package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExprEval2GrammarParserTest {
  @Test
  void givenValidExpression_whenParsed_thenShouldReturnTrue() {
    ExprEval2GrammarParser parser = new ExprEval2GrammarParser("3!!+3*-4+10+10-10+10+(10*-20*30)");
    assertTrue(parser.parse());
    assertEquals(-5272, parser.getValue());
  }
}
