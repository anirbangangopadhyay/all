package com.abhra.expression;

import org.junit.Test;

/**
 * Test Tokenizer
 */
public class TestTokenizer {

    @Test
    public void testTokenizer() {
        Tokenizer tokenizer = Tokenizer.newTokenizer("-(-)-(");
        while (tokenizer.hasNext()) {
            System.out.println(tokenizer.next());
        }
    }
}
