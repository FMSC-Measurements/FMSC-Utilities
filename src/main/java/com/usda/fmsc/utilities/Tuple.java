package com.usda.fmsc.utilities;

public class Tuple<T1, T2> {
    public final T1 Item1;
    public T2 Item2;

    public Tuple(T1 item1, T2 item2) {
        Item1 = item1;
        Item2 = item2;
    }
}
