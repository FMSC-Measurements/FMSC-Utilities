package com.usda.fmsc.utilities;

public class ParseEx {

    public static Float parseFloat(String input) {
        return parseFloat(input, null);
    }

    public static Float parseFloat(String input, Float defaultValue) {
        Float value = defaultValue;

        try {
            value = Float.parseFloat(input);
        } catch (NumberFormatException e) {

        }

        return value;
    }

    public static Double parseDouble(String input) {
        return parseDouble(input, null);
    }

    public static Double parseDouble(String input, Double defaultValue) {
        Double value = defaultValue;

        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {

        }

        return value;
    }

    public static Integer parseInteger(String input) {
        return parseInteger(input, null);
    }

    public static Integer parseInteger(String input, Integer defaultValue) {
        Integer value = defaultValue;

        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {

        }

        return value;
    }

    public static Long parseLong(String input) {
        return parseLong(input, null);
    }

    public static Long parseLong(String input, Long defaultValue) {
        Long value = defaultValue;

        try {
            value = Long.parseLong(input);
        } catch (NumberFormatException e) {

        }

        return value;
    }

    public static Boolean parseBoolean(String input) {
        return parseBoolean(input, null);
    }

    public static Boolean parseBoolean(String input, Boolean defaultValue) {
        switch (input.toLowerCase()) {
            case "1":
            case "true":
            case "t":
            case "yes":
                return true;
            case "0":
            case "false":
            case "f":
            case "no":
            default:
                return defaultValue;
        }
    }
}
