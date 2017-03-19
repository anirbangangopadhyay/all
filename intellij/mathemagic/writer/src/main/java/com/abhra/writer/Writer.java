package com.abhra.writer;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;

/**
 * Writer
 */
public class Writer {

    private static final PrintWriter PRINT_WRITER = new PrintWriter(new BufferedOutputStream(System.out));

    private static String indent = "";

    private static boolean print = true;

    public static void pushTab() {
        indent += '\t';
    }

    public static void popTab() {
        indent = indent.replaceFirst("\t", "");
    }

    private static PrintWriter get() {
        return PRINT_WRITER;
    }

    public static void print(String formatData, Object... data) {
        print(true, formatData, data);
    }

    public static void print(boolean withIndent, String formatData, Object... data) {
        if(print) {
            get().print(String.format((withIndent ? indent : "") + formatData, data));
        }
    }

    public static void println(String formatData, Object... data) {
        println(true, formatData, data);
    }

    public static void println(boolean withIndent, String formatData, Object... data) {
        if(print) {
            get().println(String.format((withIndent ? indent : "") + formatData, data));
        }
    }

    public static void flush() {
        get().flush();
    }

    public static void close() {
        get().close();
    }

    public static String getPowerPrint(int power) {
        StringBuilder builder = new StringBuilder();
        if(power == 0) {
            builder.append("\u2070");
        }
        while(power != 0) {
            switch(power % 10) {
                case 0:
                    builder.append("\u2070");
                    break;
                case 1:
                    builder.append("\u2071");
                    break;
                case 2:
                    builder.append("\u00B2");
                    break;
                case 3:
                    builder.append("\u00B3");
                    break;
                case 4:
                    builder.append("\u2074");
                    break;
                case 5:
                    builder.append("\u2075");
                    break;
                case 6:
                    builder.append("\u2076");
                    break;
                case 7:
                    builder.append("\u2077");
                    break;
                case 8:
                    builder.append("\u2078");
                    break;
                case 9:
                    builder.append("\u2079");
                    break;
                default:
                    throw new RuntimeException("Not supported power = " + power);
            }
            power /= 10;
        }
        return builder.reverse().toString();
    }

    public static int reverseNumber(int number) {
        int reverse = 0;
        while( number != 0 ) {
            reverse = reverse * 10;
            reverse = reverse + number%10;
            number /= 10;
        }
        return reverse;
    }

    public static void stopPrinting() {
        print = false;
    }

    public static void startPrinting() {
        print = true;
    }

    public static int printFactors(SortedMap<Integer, Integer> gcfs) {
        int total = 1;
        int lastKey = gcfs.lastKey();
        for (Map.Entry<Integer, Integer> entry : gcfs.entrySet()) {
            Writer.print(false, "%d%s", entry.getKey(), Writer.getPowerPrint(entry.getValue()));
            if(!Objects.equals(lastKey, entry.getKey())) {
                Writer.print(false, " x ");
            }
            total *= Math.pow(entry.getKey(), entry.getValue());
        }
        return total;
    }
}
