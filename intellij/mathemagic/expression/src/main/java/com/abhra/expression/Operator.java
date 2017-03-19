package com.abhra.expression;

/**
 * Operator
 */
public enum Operator {

    ADD("+", 2),
    SUBTRACT("-", 2),
    MULTIPLY("*", 3),
    DIVIDE("/", 3),
    REMAINDER("%", 4),
    POWER("^", 5),
    BRACKET_START("(", 6),
    BRACKET_END(")", 6),
    COMMA(",", 100);

    private final String operator;
    private final int priority;

    Operator(String operator, int priority) {
        this.operator = operator;
        this.priority = priority;
    }

    public String getOperator() {
        return operator;
    }

    public int getPriority() {
        return priority;
    }

    public static boolean isOperator(Character operator) {
        return isOperator(operator.toString());
    }

    public static boolean isOperator(String operator) {
        try {
            toEnum(operator);
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    public static Operator toEnum(String operator) {
        switch(operator.toLowerCase().trim()) {
            case "+":
                return ADD;
            case "-":
                return SUBTRACT;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "%":
                return REMAINDER;
            case "^":
                return POWER;
            case "(":
                return BRACKET_START;
            case ")":
                return BRACKET_END;
            case ",":
                return COMMA;
            default:
                throw new UnsupportedOperationException("Not supported OPERATOR -> " + operator);
        }
    }

    @Override
    public String toString() {
        return operator;
    }
}
