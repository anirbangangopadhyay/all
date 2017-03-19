package com.abhra.expression;

/**
 * Tokenizer
 */
public class Tokenizer {

    private static final char WHITESPACE = 32;
    private final String expression;
    private int index = 0;
    private String lastToken;
    private boolean lastTokenOperator = true;

    private Tokenizer(String expression) {
        this.expression = expression.trim();
    }

    public static Tokenizer newTokenizer(String expression) {
        return new Tokenizer(expression);
    }

    public boolean hasNext() {
        return index < expression.length();
    }

    public String next() {
        skipWhiteSpaces();
        String token = getNextToken();
        skipWhiteSpaces();
        boolean currentTokenOperator = Operator.isOperator(token);
        if (lastTokenOperator
                && (lastToken == null || Operator.toEnum(lastToken) == Operator.BRACKET_START || Operator.toEnum(lastToken) != Operator.BRACKET_END)
                && currentTokenOperator && Operator.SUBTRACT == Operator.toEnum(token)) {
            token = Function.NEG.getFunction();
            lastTokenOperator = false;
        } else {
            lastTokenOperator = currentTokenOperator;
        }
        lastToken = token;
        return token;
    }

    protected String getNextToken() {
        int currentIndex = index;
        skipUntilNextToken();
        return expression.substring(currentIndex, index).trim();
    }

    protected void skipWhiteSpaces() {
        while (index < expression.length() && expression.charAt(index) == WHITESPACE) {
            index++;
        }
    }

    protected void skipUntilNextToken() {
        int startIndex = index;
        // skip until next white space or end found
        while (index < expression.length() && expression.charAt(index) != WHITESPACE) {
            boolean isCurrentIndexOperator = Operator.isOperator(expression.charAt(index));
            boolean isCurrentIndexFunction = !isCurrentIndexOperator
                    && index + 3 <= expression.length()
                    && Function.isFunction(expression.substring(index, index + 3));
            index++;
            if (isCurrentIndexOperator) {
                break;
            }
            if (isCurrentIndexFunction) {
                index += 2;
                break;
            }
            if (index < expression.length() && expression.charAt(index) != WHITESPACE) {
                boolean isNextIndexOperator = Operator.isOperator(expression.charAt(index));
                boolean isNextIndexFunction = !isNextIndexOperator
                        && index + 3 <= expression.length()
                        && Function.isFunction(expression.substring(index, index + 3));
                if (isNextIndexOperator || isNextIndexFunction) {
                    break;
                }
            }
        }
    }

    public void reset() {
        index = 0;
    }

}
