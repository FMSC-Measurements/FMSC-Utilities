package com.usda.fmsc.utilities.gpx;

import java.util.ArrayList;
import java.util.List;

public class GpxTrack extends GpxBaseTrack {
    public List<GpxTrackSeg> Segments;

    public GpxTrack() {
        this(null, null);
    }

    public GpxTrack(String name) {
        this(name, null);
    }

    public GpxTrack(String name, String desc) {
        super(name, desc);
        Segments = new ArrayList<>();
    }

    public List<GpxTrackSeg> getSegments() {
        return Segments;
    }

    public void setSegments(List<GpxTrackSeg> segments) {
        Segments = segments;
    }

    public void addSegment(GpxTrackSeg segment) {
        Segments.add(segment);
    }
}