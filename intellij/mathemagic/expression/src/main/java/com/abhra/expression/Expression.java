package com.abhra.expression;

import com.abhra.writer.Writer;

import java.util.*;

/**
 * Expression Builder
 */
public class Expression {

    private final Stack<Object> output = new Stack<>();
    private final Stack<Object> operators = new Stack<>();
    private final String lhsExpression;
    private String rhsExpression;

    private Expression(String lhsExpression) {
        this.lhsExpression = lhsExpression;
    }

    public static Expression newExpression(String expression) {
        return new Expression(expression);
    }

    public Expression equals(String rhsExpression) {
        this.rhsExpression = rhsExpression;
        return this;
    }

    //Collections.swap(List<?> list, int i, int j);

    /**
     * https://brilliant.org/wiki/shunting-yard-algorithm/
     *  1.  While there are tokens to be read:
     *  2.        Read a token
     *  3.        If it's a number add it to queue
     *  4.        If it's an operator
     *  5.               While there's an operator on the top of the stack with greater precedence:
     *  6.                       Pop operators from the stack onto the output queue
     *  7.               Push the current operator onto the stack
     *  8.        If it's a left bracket push it onto the stack
     *  9.        If it's a right bracket
     *  10.              While there's not a left bracket at the top of the stack:
     *  11.                     Pop operators from the stack onto the output queue.
     *  12.              Pop the left bracket from the stack and discard it
     *  13. While there's operators on the stack, pop them to the queue
     */
    public String toPostfix() {
        Tokenizer tokenizer = Tokenizer.newTokenizer(lhsExpression + (rhsExpression != null ? " - ( " + rhsExpression + " )" : ""));
        while (tokenizer.hasNext()) {
            String token = tokenizer.next();

            if (token.length() == 1 && Operator.isOperator(token)) {
                Operator operator = Operator.toEnum(token);

                if (operator != Operator.COMMA && operator != Operator.BRACKET_END) {
                    if(operator != Operator.BRACKET_START) {
                        while (operators.size() > 0 && (operators.peek() instanceof Function ||
                                (operators.peek() instanceof Operator
                                        && operators.peek() != Operator.BRACKET_START
                                        && ((Operator) operators.peek()).getPriority() > operator.getPriority()))) {
                            Object poppedOperator = operators.pop();
                            output.push(poppedOperator);
                        }
                    }
                    operators.push(operator);

                } else if (operator == Operator.BRACKET_END) {
                    while (operators.peek() != Operator.BRACKET_START) {
                        Object poppedOperator = operators.pop();
                        output.push(poppedOperator);
                    }
                    operators.pop();
                }

            } else if (token.length() > 1 && Function.isFunction(token)) {
                Function function = Function.toEnum(token);
                operators.push(function);

            } else {
                output.push(Variable.newVariable(token));
            }
        }

        while (operators.size() > 0) {
            Object poppedOperator = operators.pop();
            output.push(poppedOperator);
        }

        assert operators.size() == 0;

        return output.toString();
    }
}
