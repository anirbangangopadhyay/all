package org.abhra.math.primenumber;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Sieve Of Atkin - as per default wiki
 */
public class PrimeNumber {

    static List<Integer> getPrimeNumbersSlow(int limit) {
        List<Integer> primeNumberList = new ArrayList<Integer>();
        boolean[] primeNumbers = getPrimeNumbersSieve(limit);
        for (int i = 0; i < primeNumbers.length; i++) {
            if(primeNumbers[i]) {
                primeNumberList.add(i);
            }
        }
        return primeNumberList;
    }

    /**
     * Function to calculate all primes less than n
     */
    private static boolean[] getPrimeNumbersSieve(int limit) {
        /* initialize the sieve */
        boolean[] primeNumbers = new boolean[limit + 1];
        primeNumbers[2] = true;
        primeNumbers[3] = true;
        int root = (int) Math.ceil(Math.sqrt(limit));

        /* put in candidate primes:
         integers which have an odd number of
         representations by certain quadratic forms **/
        for (int x = 1; x < root; x++) {
            for (int y = 1; y < root; y++) {
                int n = 4 * x * x + y * y;
                if (n <= limit && (n % 12 == 1 || n % 12 == 5)) {
                    primeNumbers[n] = !primeNumbers[n];
                }
                n = 3 * x * x + y * y;
                if (n <= limit && n % 12 == 7) {
                    primeNumbers[n] = !primeNumbers[n];
                }
                n = 3 * x * x - y * y;
                if ((x > y) && (n <= limit) && (n % 12 == 11)) {
                    primeNumbers[n] = !primeNumbers[n];
                }
            }
        }

        /* eliminate composites by sieving, omit multiples of its square **/
        for (int i = 5; i <= root; i++) {
            if (primeNumbers[i]) {
                for (int j = i * i; j < limit; j += i * i) {
                    primeNumbers[j] = false;
                }
            }
        }

        return primeNumbers;
    }

    // http://compoasso.free.fr/primelistweb - Atkin4
    public static SortedSet<Integer> getPrimeNumbers(int limit) {
        final SortedSet<Integer> primeNumberList = new TreeSet<>();
        if(limit == 1) {
            return primeNumberList;
        }
        limit += 1; // this is to include that number
        final int sqrtMax = (int) (Math.sqrt(limit) + 1);
        primeNumberList.add(2);
        if(sqrtMax <= 2) {
            return primeNumberList;
        }
        final int memorySize = limit < 10000 ? limit >> 2 : limit >> 4;
        byte[] array = new byte[memorySize];

        // Find prime
        int[] sequence = { 2, 4 };
        //noinspection UnusedAssignment
        int index = 0;
        //noinspection UnusedAssignment
        long k1 = 0, k = 0;

        double xUpper = Math.sqrt(limit / 4) + 1;
        long x = 1;
        //noinspection UnusedAssignment
        long y = 0;

        while (x < xUpper) {
            index = 0;
            k1 = 4 * x * x;
            y = 1;
            if (x % 3 == 0) {
                //noinspection Duplicates
                while (true) {
                    k = k1 + y * y;
                    if (k >= limit) {
                        break;
                    }
                    toggleBit(k, array);
                    y += sequence[(++index & 1)];
                }
            } else {
                while (true) {
                    k = k1 + y * y;
                    if (k >= limit) {
                        break;
                    }
                    toggleBit(k, array);
                    y += 2;
                }
            }
            x++;
        }

        xUpper = Math.sqrt(limit / 3) + 1;
        x = 1;
        //noinspection UnusedAssignment
        y = 0;

        while (x < xUpper) {
            index = 1;
            k1 = 3 * x * x;
            y = 2;
            //noinspection Duplicates
            while (true) {
                k = k1 + y * y;
                if (k >= limit) {
                    break;
                }
                toggleBit(k, array);
                y += sequence[(++index & 1)];
            }
            x += 2;
        }

        xUpper = (int) Math.sqrt(limit);
        x = 1;
        //noinspection UnusedAssignment
        y = 0;

        while (x < xUpper) {
            k1 = 3 * x * x;
            if ((x & 1) == 0) {
                y = 1;
                index = 0;
            } else {
                y = 2;
                index = 1;
            }
            while (y < x) {
                k = k1 - y * y;
                if (k < limit) {
                    toggleBit(k, array);
                }
                y += sequence[(++index & 1)];
            }
            x++;
        }

        setBit(2, array);
        setBit(3, array);
        for (int n = 5; n <= sqrtMax; n += 2) {
            if (getBit(n, array)) {
                int n2 = n * n;
                for (k = n2; k < limit; k += (2 * n2)) {
                    unSetBit(k, array);
                }
            }
        }

        // Display prime
        for (int i = 3; i < limit; i += 2) {
            if (getBit(i, array)) {
                primeNumberList.add(i);
            }
        }
        return primeNumberList;
    }

    private static boolean getBit(long i, byte[] array) {
        byte block = array[(int) (i >> 4)];
        byte mask = (byte) (1 << ((i >> 1) & 7));

        return ((block & mask) != 0);
    }

    private static void setBit(long i, byte[] array) {
        int index = (int) (i >> 4);
        byte block = array[index];
        byte mask = (byte) (1 << ((i >> 1) & 7));

        array[index] = (byte) (block | mask);
    }

    private static void unSetBit(long i, byte[] array) {
        int index = (int) (i >> 4);
        byte block = array[index];
        byte mask = (byte) (1 << ((i >> 1) & 7));

        array[index] = (byte) (block & ~mask);
    }

    private static void toggleBit(long i, byte[] array) {
        int index = (int) (i >> 4);
        byte block = array[index];
        byte mask = (byte) (1 << ((i >> 1) & 7));

        array[index] = (byte) (block ^ mask);
    }
}
