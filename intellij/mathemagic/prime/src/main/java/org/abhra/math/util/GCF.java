package org.abhra.math.util;

import com.abhra.writer.Writer;
import org.abhra.math.primefactor.PrimeFactor;
import org.abhra.math.primenumber.PrimeNumber;

import java.util.*;

/**
 * Greatest Common Factor or Divisor
 * Remember LCM x GCF = Number1 x Number2
 */
public class GCF {

    public static int getEuclidGCF(int... numbers) {
        Arrays.sort(numbers);
        printStartInfoEuclid(numbers);
        int gcf = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            gcf = calculateEuclidGCF(gcf, numbers[i]);
            if (gcf == 1) {
                Writer.println("GCF is already 1 so no point checking anymore.", gcf);
                break;
            }
        }
        printEndInfo(gcf);
        return gcf;
    }

    private static int calculateEuclidGCF(int number1, int number2) {
        Writer.println("Calculating GCF of %d and %d", number1, number2);
        int high = number1 > number2 ? number1 : number2;
        int low = number1 < number2 ? number1 : number2;
        int remainder = high % low;

        Writer.println("%d / %d = %d with remainder = %d", high, low, high / low, remainder);

        while (remainder != 0) {
            high = low;
            low = remainder;
            Writer.println("Remainder is not 0. Next checking divisor %d with remainder %d", high, low);
            remainder = high % low;
            Writer.println("%d / %d = %d with remainder = %d", high, low, high / low, remainder);
        }
        Writer.println("GCF of %d and %d is %d", number1, number2, low);
        return low;
    }

    public static int getCommonFactorGCF(int... numbers) {
        Arrays.sort(numbers);
        printStartInfo("Common Factor", numbers);

        Map<Integer, SortedMap<Integer, Integer>> primeFactors = new LinkedHashMap<>();
        for (int number : numbers) {
            primeFactors.put(number, PrimeFactor.getPrimeFactors(number));
        }

        Writer.println("Thus we have the following:");
        Writer.pushTab();
        SortedMap<Integer, Integer> gcfs = new TreeMap<>();
        for (Map.Entry<Integer, SortedMap<Integer, Integer>> entry : primeFactors.entrySet()) {

            Writer.print("%d = ", entry.getKey());
            Writer.printFactors(entry.getValue());
            Writer.println(false, "");

            if(Objects.equals(numbers[0], entry.getKey())) {
                gcfs.putAll(entry.getValue());
            } else {
                gcfs.keySet().retainAll(entry.getValue().keySet());
            }
            for (Map.Entry<Integer, Integer> gcfEntry : gcfs.entrySet()) {
                Integer newValue = entry.getValue().get(gcfEntry.getKey());
                if(gcfEntry.getValue() > newValue) {
                    gcfEntry.setValue(newValue);
                }
            }
        }

        Writer.println("And selecting all COMMON values with LOWEST POWER we have the following:");
        Writer.print("GCF = ");
        int gcf = Writer.printFactors(gcfs);
        Writer.println(false, " = %d", gcf);

        Writer.popTab();

        printEndInfo(gcf);
        return gcf;
    }

    public static int getLadderGCF(int... numbers) {
        Arrays.sort(numbers);
        printStartInfo("Ladder", numbers);

        Writer.println("Check divisibility by Prime Numbers one by one. (In real life even non Prime is fine - like 4 instead of 2 times 2)");

        SortedSet<Integer> primeNumbers = PrimeNumber.getPrimeNumbers(numbers[0]);
        List<Integer> factors = new LinkedList<>();
        List<Integer> residues = new LinkedList<>();
        for (int number : numbers) {
            residues.add(number);
        }

        int maxPadding = ("" + numbers[numbers.length - 1]).length();
        calculateLadderGCF(factors, residues, primeNumbers, maxPadding, 1);

        Writer.println("GCF is the multiple of ALL numbers in the LEFT of the LADDER");
        int gcf = 1;
        for (Integer factor : factors) {
            if(gcf != 1) {
                Writer.print(false, " x %d", factor);
            } else {
                Writer.print("%d", factor);
            }
            gcf *= factor;
        }
        Writer.println(false, " = %d", gcf);
        printEndInfo(gcf);
        return gcf;
    }

    static void calculateLadderGCF(List<Integer> factors, List<Integer> residues, SortedSet<Integer> primeNumbers, int maxPadding, int iteration) {
        for (Integer primeNumber : primeNumbers) {
            if (primeNumber > residues.iterator().next()) {
                break;
            }

            boolean isDivisibleForAll = true;
            //noinspection Duplicates
            for (Integer residue : residues) {
                if (residue % primeNumber != 0) {
                    isDivisibleForAll = false;
                    break;
                }
            }
            if (isDivisibleForAll) {
                Set<Integer> newResidues = new LinkedHashSet<>();
                factors.add(primeNumber);
                StringBuilder builder = new StringBuilder();
                builder.append(String.format(" %" + maxPadding + "d  |  ", primeNumber));
                //noinspection Duplicates
                for (Integer residue : residues) {
                    newResidues.add(residue / primeNumber);
                    builder.append(String.format("%" + maxPadding + "d  ", residue));
                }
                Writer.println(builder.toString());
                int index = builder.indexOf("|");
                Writer.print(" %" + index + "s", " +");
                for (int i = index; i < builder.length(); i++) {
                    Writer.print(false, "-");
                }
                Writer.println(false, "");
                residues.clear();
                residues.addAll(newResidues);
                calculateLadderGCF(factors, residues, primeNumbers, maxPadding, iteration + 1);
                return;
            }
        }
        Writer.print(" %" + maxPadding + "s     ", "");
        for (Integer residue : residues) {
            Writer.print(false, "%" + maxPadding + "d  ", residue);
        }
        Writer.println(false, "");
        Writer.println("The remaining numbers are either ALL PRIMES or have NO COMMON FACTOR.");
    }

    private static void printStartInfoEuclid(int... numbers) {
        Writer.print("Calculating GCF of numbers using Euclid Division method for ");
        Arrays.stream(numbers).mapToObj(Integer::toString).forEach(item -> Writer.print(false, item + ", "));
        Writer.println(false, "(taking 2 numbers at a time)");
        Writer.pushTab();
    }

    private static void printStartInfo(String info, int... numbers) {
        Writer.print("Calculating GCF of numbers using %s method for ", info);
        Arrays.stream(numbers).mapToObj(Integer::toString).forEach(item -> Writer.print(false, item + ", "));
        Writer.println(false, "");
        Writer.pushTab();
    }

    private static void printEndInfo(int gcf) {
        Writer.popTab();
        Writer.println("Final GCF is %d", gcf);
    }
}
