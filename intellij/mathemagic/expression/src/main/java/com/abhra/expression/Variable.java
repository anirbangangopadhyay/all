package com.abhra.expression;

import com.abhra.writer.Writer;

import java.math.BigDecimal;
import java.util.UnknownFormatConversionException;

/**
 * Variable
 */
public final class Variable {

    private final BigDecimal number;
    private final String variable;
    private int degree = 1;

    private Variable(String token) {
        String numberToken = token.replaceAll(",", "").trim();
        if(numberToken.matches("-?\\d+(\\.\\d+)?")) {
            variable = null;
            number = new BigDecimal(numberToken);
        } else {
            variable = token;
            number = null;
        }
    }

    public static Variable newVariable(String token) {
        return new Variable(token);
    }

    public boolean isVariable() {
        return variable != null;
    }

    public boolean isNumber() {
        return number != null;
    }

    public String getVariable() {
        checkVariable();
        return variable;
    }

    public BigDecimal getNumber() {
        checkNumber();
        return number;
    }

    public int getDegree() {
        checkVariable();
        return degree;
    }

    public void setDegree(int degree) {
        checkVariable();
        this.degree = degree;
    }

    private void checkVariable() {
        if(!isVariable()) {
            throw new UnknownFormatConversionException("This is not a variable");
        }
    }

    private void checkNumber() {
        if(!isNumber()) {
            throw new UnknownFormatConversionException("This is not a number");
        }
    }

    @Override
    public String toString() {
        return isNumber() ? number.toString() : getDegree() != 1 ? variable + Writer.getPowerPrint(degree) : variable;
    }
}
