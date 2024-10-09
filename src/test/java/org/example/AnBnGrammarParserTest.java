package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnBnGrammarParserTest {

    @Test
    void givenValidInput_whenParsed_thenShouldReturnTrue() {
        AnBnGrammarParser parser = new AnBnGrammarParser("aaabbb");
        assertTrue(parser.parse());
    }

    @Test
    void givenInvalidInput_whenParsed_thenShouldReturnFalse() {
        AnBnGrammarParser parser = new AnBnGrammarParser("aabbb");
        assertFalse(parser.parse());
    }
}
