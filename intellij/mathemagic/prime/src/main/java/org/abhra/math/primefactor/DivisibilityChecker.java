package org.abhra.math.primefactor;

import com.abhra.writer.Writer;

/**
 * Divisibility Checker
 */
public class DivisibilityChecker {

    // Add a notion of divisibility check
    // https://en.wikipedia.org/wiki/Divisibility_rule#Step-by-step_examples

    private final int number;

    public static DivisibilityChecker create(int number) {
        return new DivisibilityChecker(number);
    }

    private DivisibilityChecker(int number) {
        this.number = number;
    }

    public boolean isDivisibleBy(int check) {
        switch (check) {
            case 2:
                if((number & 1) == 0) {
                    Writer.println(String.format("The last digit of number %d is %d, which is even, and therefore the number is divisible by %d.", number, Math.abs(number % 10), check));
                    return true;
                }
                break;
            case 3:
            case 9:
                if (check3Or9Divisibility(check)) {
                    return true;
                }
                break;
            case 4:
                int sumCheck4 = Math.abs(number % 100);
                if((sumCheck4 & 4) == 0) {
                    Writer.println(String.format("The last 2 digit of number %d is %d, which is divisible by %d (%d x %d = %d), so the number is as well.", number, sumCheck4, check, check, sumCheck4/check, sumCheck4));
                    return true;
                }
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                if(number % check == 0) {
                    return true;
                }
        }
        return false;
    }

    private boolean check3Or9Divisibility(int check) {
        int sumCheck9 = getSumOfDigits(number);
        if(sumCheck9 % check == 0) {
            Writer.println(String.format("The sum of the digits of number %d is %d, which is divisible by %d (%d x %d = %d), so the number is as well.", number, sumCheck9, check, check, sumCheck9/check, sumCheck9));
            return true;
        }
        return false;
    }

    private int getSumOfDigits(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
