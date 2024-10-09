package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvalBooleanExprGrammarParserTest {

  @Test
  void test1() {
    EvalBooleanExprGrammarParser parser = new EvalBooleanExprGrammarParser("false && true");
    assertTrue(parser.parse());
    assertEquals(false, parser.getValue());
  }

  @Test
  void test2() {
    EvalBooleanExprGrammarParser parser =
        new EvalBooleanExprGrammarParser("false && (true || false)");
    assertTrue(parser.parse());
    assertEquals(false, parser.getValue());
  }

  @Test
  void test3() {
    EvalBooleanExprGrammarParser parser = new EvalBooleanExprGrammarParser("true || false && true");
    assertTrue(parser.parse());
    assertEquals(true, parser.getValue());
  }
}
