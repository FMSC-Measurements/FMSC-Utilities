package com.usda.fmsc.utilities;

public class ParseEx {
    
    public static Double parseDouble(String input) {
        Double value = null;

        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            
        }

        return value;
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
        Integer value = null;

        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {

        }

        return value;
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
        Long value = null;

        try {
            value = Long.parseLong(input);
        } catch (NumberFormatException e) {

        }

        return value;
    }

    public static Long parseLong(String input, Long defaultValue) {
        Long value = defaultValue;

        try {
            value = Long.parseLong(input);
        } catch (NumberFormatException e) {

        }

        return value;
    }

    public static boolean parseBoolean(String input) {
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
                return false;
        }
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
