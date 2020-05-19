package com.axxes.traineeship.testing.unit;

public class StringUtils {

    public static String abbreviate(final String input, final int maxLength) {
        if (input == null) {
            return null;
        }
        if (maxLength > input.length()) {
            return input;
        }
        if (maxLength <= 3) {
            return input.substring(0, maxLength);
        }
        return input.substring(0, maxLength - 3) + "...";
    }

}
