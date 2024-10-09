package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExprEval3GrammarParserTest {
  @Test
  void givenValidExpression_whenParsed_thenShouldReturnTrue() {
    ExprEval3GrammarParser parser = new ExprEval3GrammarParser("3!!+3*-4+10+10-10+10+(10*-30*30)");
    assertTrue(parser.parse());
    assertEquals(
        "(((((((! (! 3)) + (3 * -4)) + 10) + 10) - 10) + 10) + ((10 * -30) * 30))",
        String.valueOf(parser.getValue()));
  }
}
