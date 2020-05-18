package com.axxes.traineeship.testing;

public class StringUtils {

    public static String abbreviate(final String input, final int maxLength) {
        if (input == null) {
            return null;
        }
        if (maxLength > input.length()) {
            return input;
        }
        return input.substring(0, maxLength - 3) + "...";
    }

}
