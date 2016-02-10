package com.usda.fmsc.utilities;

import java.util.EnumSet;

public class EnumEx {

    public static <E extends Enum<E>> String[] getNames(Class<E> e) {
        EnumSet<E> eSet = EnumSet.allOf(e);

        String[] values = new String[eSet.size()];

        int c = 0;
        for (E v : eSet) {
            values[c] = v.toString();
            c++;
        }

        return values;
    }

    public static <E extends Enum<E>> int[] getValues(Class<E> e) {
        EnumSet<E> eSet = EnumSet.allOf(e);

        int[] values = new int[eSet.size()];

        int c = 0;
        for (E v : eSet) {
            values[c] = v.ordinal();
            c++;
        }

        return values;
    }
}
