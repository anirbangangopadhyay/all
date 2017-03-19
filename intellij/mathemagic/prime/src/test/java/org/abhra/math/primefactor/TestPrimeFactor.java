package org.abhra.math.primefactor;

import com.abhra.writer.Writer;
import org.junit.After;
import org.junit.Test;

/**
 * Test Prime Factor
 */
public class TestPrimeFactor {

    @After
    public void end() {
        Writer.flush();
    }

    @Test
    public void testPrimeFactor8() {
        PrimeFactor.getPrimeFactors(8);
    }

    @Test
    public void testPrimeFactor105() {
        PrimeFactor.getPrimeFactors(105);
    }

    @Test
    public void testPrimeFactor32() {
        PrimeFactor.getPrimeFactors(32);
    }

    @Test
    public void testPrimeFactor28() {
        PrimeFactor.getPrimeFactors(28);
    }

    @Test
    public void testPrimeFactor64() {
        PrimeFactor.getPrimeFactors(64);
    }

    public static void main(String[] args) {
        System.out.println(14 & 1);
    }
}
