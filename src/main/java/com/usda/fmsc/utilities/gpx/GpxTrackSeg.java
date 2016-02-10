package com.usda.fmsc.utilities.gpx;

import java.util.ArrayList;
import java.util.List;

public class GpxTrackSeg {
    private List<GpxPoint> Points;
    private String Extensions;

    public GpxTrackSeg() {
        Points = new ArrayList<GpxPoint>();
        Extensions = null;
    }

    public List<GpxPoint> getPoints() {
        return Points;
    }

    public void setPoints(List<GpxPoint> points) {
        Points = points;
    }

    public String getExtensions() {
        return Extensions;
    }

    public void setExtensions(String extensions) {
        Extensions = extensions;
    }

    public void addPoint(GpxPoint point) {
        Points.add(point);
    }
}