package com.usda.fmsc.utilities.gpx;

import java.util.ArrayList;
import java.util.List;

public class GpxRoute extends GpxBaseTrack {
    private List<GpxPoint> Points;

    public GpxRoute() {
        this(null, null);
    }

    public GpxRoute(String name) {
        this(name, null);
    }

    public GpxRoute(String name, String desc) {
        super(name, desc);
        Points = new ArrayList<>();
    }

    public List<GpxPoint> getPoints() {
        return Points;
    }

    public void setPoints(List<GpxPoint> points) {
        Points = points;
    }

    public void addPoint(GpxPoint point) {
        Points.add(point);
    }
}

