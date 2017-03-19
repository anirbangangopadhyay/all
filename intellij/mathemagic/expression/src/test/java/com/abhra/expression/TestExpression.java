package com.abhra.expression;

import org.junit.Test;

/**
 * Test Expression
 */
public class TestExpression {

    @Test
    public void testExpressionPostfix() {
        String postfixNotation1 = Expression.newExpression("x * (2 + 3)").toPostfix();
        System.out.println(postfixNotation1);
        String postfixNotation2 = Expression.newExpression("x * (2)").toPostfix();
        System.out.println(postfixNotation2);
        String postfixNotation3 = Expression.newExpression("-A*B^cos C,E +(-D)").toPostfix();
        System.out.println(postfixNotation3);
        String postfixNotation4 = Expression.newExpression("-A*B^cos(C,E)+(-D)").toPostfix();
        System.out.println(postfixNotation4);
    }
}
