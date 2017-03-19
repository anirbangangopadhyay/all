package org.abhra.math.primefactor;

import com.abhra.writer.Writer;
import org.abhra.math.primenumber.PrimeNumber;

import java.util.*;

/**
 * Prime Factor Calculator
 */
public class PrimeFactor {

    public static SortedMap<Integer, Integer> getPrimeFactors(int number) {
        Writer.println(String.format("Calculating Prime Factors of number %d", number));
        Writer.pushTab();
        TreeMap<Integer, Integer> primeFactors = new TreeMap<>();
        getPrimeFactors(number, number, primeFactors, null, 1);
        Writer.popTab();
        return primeFactors;
    }

    private static void getPrimeFactors(int originalNumber, int newNumber, SortedMap<Integer, Integer> primeFactors, SortedSet<Integer> primeNumbers, int iteration) {
        if (newNumber == 1 || newNumber == 2 || (primeNumbers != null && primeNumbers.contains(newNumber))) {
            if(newNumber != 1) {
                addPrimeFactor(primeFactors, newNumber);
            }
            Writer.println(String.format("The last number %d is prime. We now have all prime factors - so we stop.", newNumber));
            return;
        }

        Writer.println(String.format("Iteration = %d. Starting number = %d.", iteration, newNumber));
        if ((newNumber & 1) == 0) {
            int primeFactor = 2;
            Writer.println(String.format("The number %d is even. %d is a factor.", newNumber, primeFactor));
            newNumber = getNextNumber(originalNumber, newNumber, primeFactors, primeFactor);
        } else if(newNumber > 1) {
            Integer lastPrimeNumber = primeFactors.isEmpty() ? 2 : primeFactors.lastKey();
            if(primeNumbers == null) {
                primeNumbers = PrimeNumber.getPrimeNumbers(primeFactors.size() > 1 ? (int) Math.sqrt(newNumber) : newNumber);
            }
            if(primeNumbers.contains(newNumber)) {
                addPrimeFactor(primeFactors, newNumber);
                Writer.println(String.format("The last number %d is prime. So we stop here.", newNumber));
                return;
            }
            Writer.println(String.format("The number %d is odd and > 1. So we try to divide it by prime number > %d.", newNumber, lastPrimeNumber));
            for (Integer primeNumber : primeNumbers) {
                if (primeNumber <= lastPrimeNumber) {
                    continue;
                }
                if (newNumber % primeNumber == 0) {
                    Writer.println(String.format("The number %d is divisible by %d.", newNumber, primeNumber));
                    newNumber = getNextNumber(originalNumber, newNumber, primeFactors, primeNumber);
                    break;
                } else {
                    Writer.print(String.format("The number %d is not divisible by %d. So we will try next prime number.", newNumber, primeNumber));
                }
            }
        }

        getPrimeFactors(originalNumber, newNumber, primeFactors, primeNumbers, iteration + 1);
    }

    private static int getNextNumber(int originalNumber, int newNumber, SortedMap<Integer, Integer> primeFactors, int primeFactor) {
        addPrimeFactor(primeFactors, primeFactor);
        Writer.print(String.format("%d / %d = ", newNumber, primeFactor));
        newNumber /= primeFactor;
        Writer.print(false, String.format("%d. Therefore we have  ", newNumber));
        Writer.print(false, String.format("%d = ", originalNumber));
        printFactors(primeFactors);
        Writer.println(false, String.format("%d", newNumber));
        return newNumber;
    }

    private static void addPrimeFactor(SortedMap<Integer, Integer> primeFactors, int primeFactor) {
        if (!primeFactors.containsKey(primeFactor)) {
            primeFactors.put(primeFactor, 1);
        } else {
            primeFactors.put(primeFactor, primeFactors.get(primeFactor) + 1);
        }
    }

    private static void printFactors(SortedMap<Integer, Integer> primeFactors) {
        for (Map.Entry<Integer, Integer> entry : primeFactors.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                Writer.print(false, entry.getKey() + " x ");
            }
        }
    }
}
