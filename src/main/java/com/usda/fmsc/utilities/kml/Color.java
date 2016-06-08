package com.usda.fmsc.utilities.kml;

import java.util.ArrayList;

public class Color {
    private int r, g, b, alpha;


    public Color() {
        //black
        r = g = b = 0;
        alpha = 255;
    }

    public Color(String color) {
        SetColorFromStringRGBA(color);
    }

    public Color(Color color) {
        alpha = color.getAlpha();
        r = color.getR();
        g = color.getG();
        b = color.getB();
    }

    public Color(int r, int g, int b, int alpha) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.alpha = alpha;
    }

    public void SetColorFromStringRGBA(String color) {
        if (color == null || color.length() < 2)
            throw new RuntimeException("String must be greater than 2 and even");

        if (color.length() % 2 != 0)
            throw new RuntimeException("String must be even");

        int i=0;
        ArrayList<String> colors = new ArrayList<>();


        while (i < color.length()) {
            colors.add(color.substring(i, 2));
            i += 2;
        }

        int len = colors.size();

        if (len > 2)
            r = Integer.parseInt(colors.get(0), 16);
            g = Integer.parseInt(colors.get(1), 16);
            b = Integer.parseInt(colors.get(2), 16);
        if (len > 3)
            alpha = Integer.parseInt(colors.get(3), 16);
    }


    public int getR() {
        return r;
    }

    public void setR(int value) {
        if (value > 255)
            r = 255;
        else if (value < 0)
            r = 0;
        else
            r = value;
    }

    public int getG() {
        return g;
    }

    public void setG(int value) {
        if (value > 255)
            g = 255;
        else if (value < 0)
            g = 0;
        else
            g = value;
    }

    public int getB() {
        return b;
    }

    public void setB(int value) {
        if (value > 255)
            b = 255;
        else if (value < 0)
            b = 0;
        else
            b = value;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int value) {
        if (value > 255)
            alpha = 255;
        else if (value < 0)
            alpha = 0;
        else
            alpha = value;
    }


    public String toStringRGBA() {
        return String.format("%02x%02x%02x%02x", r, g, b, alpha);
    }

    //ABGR klm color is reversed
    @Override
    public String toString() {
        return String.format("%02x%02x%02x%02x", alpha, b, g, r);
    }
}
