package com.usda.fmsc.utilities;

import android.text.TextUtils;

public class StringEx {
    public static final String Empty = "";

    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    public static boolean isEmpty(String value, boolean trim) {
        return value == null || value.trim().length() < 1;
    }

    public static String subString(String string, int start, int length) {
        if (string.length() >= start && length > 0) {
            if (start + length < string.length()) {
                return string.substring(start, length);
            } else {
                return string.substring(start, string.length());
            }
        }

        return Empty;
    }


    public static String toString(Double value) {
        if (value != null) {
            return Double.toString(value);
        } else {
            return StringEx.Empty;
        }
    }

    public static String toString(Double value, int decimals) {
        if (value != null) {
            return String.format("%." + decimals + "f", value);
        } else {
            return StringEx.Empty;
        }
    }

    public static String toString(Integer value) {
        if (value != null) {
            return Integer.toString(value);
        } else {
            return StringEx.Empty;
        }
    }

    public static String toString(Boolean value) {
        if (value != null) {
            return Boolean.toString(value);
        } else {
            return StringEx.Empty;
        }
    }

    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }

        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(CharSequence cs) {
        if (cs == null) {
            return false;
    }

        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String sanitizeForFile(String str) {
        return str.replaceAll("[:\\\\/*?|<>]", "_");
    }


    public static String getValueOrEmpty(String value) {
        return value == null ? Empty : value;
    }
}
