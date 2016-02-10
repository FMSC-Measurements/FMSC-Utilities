package com.usda.fmsc.utilities.kml;

import com.usda.fmsc.utilities.kml.Types.XYUnitType;

public class HotSpot {
    private double X;
    private double Y;
    private XYUnitType xUnits;
    private XYUnitType yUnits;

    public HotSpot(double x, double y, XYUnitType xu, XYUnitType yu) {
        X = x;
        Y = y;
        xUnits = xu;
        yUnits = yu;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public XYUnitType getXUnits() {
        return xUnits;
    }

    public void setXUnits(XYUnitType xUnits) {
        this.xUnits = xUnits;
    }

    public XYUnitType getYUnits() {
        return yUnits;
    }

    public void setYUnits(XYUnitType yUnits) {
        this.yUnits = yUnits;
    }
}
