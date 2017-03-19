package org.abhra.math.util;

import com.abhra.writer.Writer;
import org.abhra.math.primefactor.PrimeFactor;
import org.abhra.math.primenumber.PrimeNumber;

import java.util.*;

/**
 * Lowest Common Multiple
 * Remember LCM x GCF = Number1 x Number2
 */
public class LCM {

    public static int getEuclidLCM(int... numbers) {
        Arrays.sort(numbers);
        printStartInfo("Euclid", numbers);

        Writer.println("First we calculate the GCF using Euclid");
        Writer.pushTab();
        int gcf = GCF.getEuclidGCF(numbers);
        Writer.popTab();

        Writer.println("Then we multiply all numbers except if one is multiple of the other, then we take higher value");
        Writer.pushTab();
        Map<Integer, Integer> excluded = new HashMap<>();
        int maxItems = numbers.length - 1;
        int total = 1;
        for (int i = maxItems; i >= 0; i--) {
            if(i != maxItems) {
                boolean isMultipleOfPrevious = false;
                for (int j = maxItems; j > i; j--) {
                    if(numbers[j] % numbers[i] == 0) {
                        excluded.put(numbers[i], numbers[j]);
                        isMultipleOfPrevious = true;
                    }
                }
                if(isMultipleOfPrevious) {
                    continue;
                }
                Writer.print(false, " x ");
            } else {
                Writer.print("");
            }
            Writer.print(false, "%d", numbers[i]);
            total *= numbers[i];
        }
        Writer.println(false, " = %d", total);
        Writer.println("The following numbers are excluded");
        Writer.pushTab();
        for (Map.Entry<Integer, Integer> excludedEntry : excluded.entrySet()) {
            Writer.println("Excluded %d since %d / %d = %d",
                    excludedEntry.getKey(), excludedEntry.getValue(), excludedEntry.getKey(), excludedEntry.getValue() / excludedEntry.getKey());
        }
        Writer.popTab();
        Writer.popTab();

        Writer.println("After that we divide the result of the multiplication total of 2nd step with the GCF found in 1st step");
        Writer.pushTab();
        int lcm = total/gcf;
        Writer.println("LCM = %d / %d = %d", total, gcf, lcm);
        Writer.popTab();

        return lcm;
    }

    public static int getCommonFactorLCM(int... numbers) {
        Arrays.sort(numbers);
        printStartInfo("Common Factor", numbers);

        Map<Integer, SortedMap<Integer, Integer>> primeFactors = new LinkedHashMap<>();
        for (int number : numbers) {
            primeFactors.put(number, PrimeFactor.getPrimeFactors(number));
        }

        Writer.println("Thus we have the following:");
        Writer.pushTab();
        SortedMap<Integer, Integer> lcms = new TreeMap<>();
        for (Map.Entry<Integer, SortedMap<Integer, Integer>> entry : primeFactors.entrySet()) {

            Writer.print("%d = ", entry.getKey());
            Writer.printFactors(entry.getValue());
            Writer.println(false, "");

            lcms.putAll(entry.getValue());

            for (Map.Entry<Integer, Integer> lcmEntry : lcms.entrySet()) {
                Integer newValue = entry.getValue().get(lcmEntry.getKey());
                if(newValue != null && lcmEntry.getValue() < newValue) {
                    lcmEntry.setValue(newValue);
                }
            }
        }

        Writer.println("And selecting ALL DISTINCT values with HIGHEST POWER we have the following:");
        Writer.print("LCM = ");
        int lcm = Writer.printFactors(lcms);
        Writer.println(false, " = %d", lcm);

        Writer.popTab();

        printEndInfo(lcm);
        return lcm;
    }

    public static int getLadderLCM(int... numbers) {
        Arrays.sort(numbers);
        printStartInfo("Ladder", numbers);

        Writer.println("Check divisibility by Prime Numbers one by one as long as at least 2 numbers can be divided. (In real life even non Prime is fine - like 4 instead of 2 times 2)");

        SortedSet<Integer> primeNumbers = PrimeNumber.getPrimeNumbers(numbers[0]);
        List<Integer> factors = new LinkedList<>();
        List<Integer> residues = new LinkedList<>();
        for (int number : numbers) {
            residues.add(number);
        }

        int maxPadding = ("" + numbers[numbers.length - 1]).length();
        calculateLadderLCM(factors, residues, primeNumbers, maxPadding, 1);

        Writer.println("LCM is the multiple of ALL numbers in the LEFT and BOTTOM of the LADDER");
        int lcm = 1;
        for (Integer factor : factors) {
            if(lcm != 1) {
                Writer.print(false, " x %d", factor);
            } else {
                Writer.print("%d", factor);
            }
            lcm *= factor;
        }
        for (Integer residue : residues) {
            Writer.print(false, " x %d", residue);
            lcm *= residue;
        }
        Writer.println(false, " = %d", lcm);
        printEndInfo(lcm);
        return lcm;
    }

    static void calculateLadderLCM(List<Integer> factors, List<Integer> residues, SortedSet<Integer> primeNumbers, int maxPadding, int iteration) {
        boolean allResiduesPrime = true;
        for (Integer residue : residues) {
            if(!primeNumbers.contains(residue)) {
                allResiduesPrime = false;
                break;
            }
        }
        if(!allResiduesPrime) {
            for (Integer primeNumber : primeNumbers) {

                boolean primeNumberGreaterThanAllResidues = true;
                for (Integer residue : residues) {
                    if(residue > primeNumber) {
                        primeNumberGreaterThanAllResidues = false;
                        break;
                    }
                }
                if (primeNumberGreaterThanAllResidues) {
                    break;
                }

                boolean atLeastOneNumbersDivisible = false;
                boolean atLeastTwoNumbersDivisible = false;
                //noinspection Duplicates
                for (Integer residue : residues) {
                    if (residue >= primeNumber && residue % primeNumber == 0) {
                        if(atLeastOneNumbersDivisible) {
                            atLeastTwoNumbersDivisible = true;
                        }
                        atLeastOneNumbersDivisible = true;
                    }
                }
                if (atLeastTwoNumbersDivisible) {
                    Set<Integer> newResidues = new LinkedHashSet<>();
                    factors.add(primeNumber);
                    StringBuilder builder = new StringBuilder();
                    builder.append(String.format(" %" + maxPadding + "d  |  ", primeNumber));
                    //noinspection Duplicates
                    for (Integer residue : residues) {
                        if(residue >= primeNumber && residue % primeNumber == 0) {
                            newResidues.add(residue / primeNumber);
                        } else {
                            newResidues.add(residue);
                        }
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
                    calculateLadderLCM(factors, residues, primeNumbers, maxPadding, iteration + 1);
                    return;
                }
            }
        }
        Writer.print(" %" + maxPadding + "s     ", "");
        for (Integer residue : residues) {
            Writer.print(false, "%" + maxPadding + "d  ", residue);
        }
        Writer.println(false, "");
        Writer.println("The remaining numbers are either ALL PRIMES or have NO COMMON FACTOR.");
    }

    private static void printStartInfo(String info, int... numbers) {
        Writer.print("Calculating LCM of numbers using %s method for ", info);
        Arrays.stream(numbers).mapToObj(Integer::toString).forEach(item -> Writer.print(item + ", "));
        Writer.println("");
        Writer.pushTab();
    }

    private static void printEndInfo(int lcm) {
        Writer.popTab();
        Writer.println("Final LCM is %d", lcm);
    }
}
