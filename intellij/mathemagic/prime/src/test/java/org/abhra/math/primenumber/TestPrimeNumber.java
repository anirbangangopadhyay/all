package org.abhra.math.primenumber;

import org.junit.Test;

import java.util.List;
import java.util.SortedSet;

/**
 * Test Primes
 */
public class TestPrimeNumber {

    private static final int MAX = 100;

    @Test
    public void testSieveOfAtkin() {
        long start = System.currentTimeMillis();
        SortedSet<Integer> primeNumbers = PrimeNumber.getPrimeNumbers(MAX);
        System.out.println(primeNumbers);
        System.out.println("Time for SieveOfAtkin > " + (System.currentTimeMillis() - start));
    }

    @Test
    public void testSieveOfAtkin7() {
        long start = System.currentTimeMillis();
        SortedSet<Integer> primeNumbers = PrimeNumber.getPrimeNumbers(3);
        System.out.println(primeNumbers);
        System.out.println("Time for SieveOfAtkin > " + (System.currentTimeMillis() - start));
    }

    @Test
    public void testSieveOfAtkinSlow() {
        long start = System.currentTimeMillis();
        List<Integer> primeNumbersSlow = PrimeNumber.getPrimeNumbersSlow(MAX);
        System.out.println(primeNumbersSlow);
        System.out.println("Time for SieveOfAtkin Slow > " + (System.currentTimeMillis() - start));
    }
}
