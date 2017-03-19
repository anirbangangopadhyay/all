package com.abhra.expression;

/**
 * Function
 */
public enum Function {

    SIN("sin", 1),
    COS("cos", 1),
    TAN("tan", 1),
    COSEC("csc", 1),
    SEC("sec", 1),
    COT("cot", 1),
    LOG("log", 1),
    NEG("neg", 1);

    private final String function;
    private final int argumentCount;

    Function(String function, int argumentCount) {
        this.function = function;
        this.argumentCount = argumentCount;
    }

    public String getFunction() {
        return function;
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public static boolean isFunction(String function) {
        try {
            toEnum(function);
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }

    public static Function toEnum(String function) {
        switch(function.toLowerCase().trim()) {
            case "sin":
                return SIN;
            case "cos":
                return COS;
            case "tan":
                return TAN;
            case "csc":
                return COSEC;
            case "sec":
                return SEC;
            case "cot":
                return COT;
            case "log":
                return LOG;
            case "$-":
            case "neg":
                return NEG;
            default:
                throw new UnsupportedOperationException("Not supported FUNCTION -> " + function);
        }
    }

    @Override
    public String toString() {
        return function;
    }
}
