package com.usda.fmsc.utilities;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;

public class StringEx {
    public static final String Empty = "";
    private static final DecimalFormat df = new DecimalFormat("#.##########");
    private static final DecimalFormat dfe = new DecimalFormat("#.##########");


    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    public static boolean isEmpty(String value, boolean trim) {
        return value == null || trim && value.trim().length() < 1 || value.length() < 1;
    }


    public static String subString(String string, int start, int length) {
        if (string.length() >= start && length > 0) {
            if (start + length < string.length()) {
                return string.substring(start, length);
            } else {
                return string.substring(start);
            }
        }

        return Empty;
    }


    public static String toString(Double value) {
        if (value != null) {
            return df.format(value);
        } else {
            return StringEx.Empty;
        }
    }

    public static String toString(Double value, int decimals) {
        if (value != null) {
            dfe.setMaximumFractionDigits(decimals);
            return dfe.format(value);
        } else {
            return StringEx.Empty;
        }
    }

    public static String toStringRound(Double value, int decimals) {
        return toString(round(value, decimals), decimals);
    }


    public static String toString(Float value) {
        if (value != null) {
            return df.format(value);
        } else {
            return StringEx.Empty;
        }
    }

    public static String toString(Float value, int decimals) {
        if (value != null) {
            dfe.setMaximumFractionDigits(decimals);
            return dfe.format(value);
        } else {
            return StringEx.Empty;
        }
    }

    public static String toStringRound(Float value, int decimals) {
        return toString(round(value, decimals), decimals);
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


    private static Double round(Double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException();

        if (value == null)
            return null;

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static Float round(Float value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException();

        if (value == null)
            return null;

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
