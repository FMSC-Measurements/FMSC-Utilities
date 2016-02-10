package com.usda.fmsc.utilities.kml;

import java.util.ArrayList;

import com.usda.fmsc.utilities.kml.Types.AltitudeMode;

public class Polygon {
    public class Dimensions {
        public double East;
        public double West;
        public double North;
        public double South;

        public Dimensions(double e, double w, double n, double s) {
            East = e;
            West = w;
            North = n;
            South = s;
        }
    }

    private AltitudeMode AltMode;
    private String CN;

    private String Name;
    private ArrayList<Coordinates> OuterBoundary;
    private ArrayList<Coordinates> InnerBoundary;
    private boolean IsPath;
    private boolean Tessellate;
    private boolean Extrude;


    public Polygon(String name) {
        this(name, null);
    }

    public Polygon(String name, ArrayList<Coordinates> ob) {
        Name = name;
        OuterBoundary = ob;
        CN = java.util.UUID.randomUUID().toString();
        IsPath = false;
    }


    public Coordinates getAveragedCoords() {
        Coordinates c = new Coordinates(0d, 0d);
        double lat = 0, lon = 0, alt = 1000;
        int count = 0;

        for (Coordinates coord : OuterBoundary) {
            lat += coord.getLatitude();
            lon += coord.getLongitude();
            alt += coord.getAltitude();
            count++;
        }

        if (count > 0) {
            lat /= count;
            lon /= count;
            alt /= count;
        }

        c.setLatitude(lat);
        c.setLongitude(lon);
        c.setAltitude(alt);

        return c;
    }


    public String getCN() {
        return CN;
    }

    public boolean isExtrude() {
        return Extrude;
    }

    public void setExtrude(boolean extrude) {
        Extrude = extrude;
    }

    public boolean isTessellate() {
        return Tessellate;
    }

    public void setTessellate(boolean tessellate) {
        Tessellate = tessellate;
    }

    public boolean isPath() {
        return IsPath;
    }

    public void setIsPath(boolean isPath) {
        IsPath = isPath;
    }

    public ArrayList<Coordinates> getInnerBoundary() {
        return InnerBoundary;
    }

    public void setInnerBoundary(ArrayList<Coordinates> innerBoundary) {
        InnerBoundary = innerBoundary;
    }

    public ArrayList<Coordinates> getOuterBoundary() {
        return OuterBoundary;
    }

    public void setOuterBoundary(ArrayList<Coordinates> outerBoundary) {
        OuterBoundary = outerBoundary;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public AltitudeMode getAltMode() {
        return AltMode;
    }

    public void setAltMode(AltitudeMode altMode) {
        AltMode = altMode;
    }


    public Dimensions getOuterDimensions() {
        if (OuterBoundary.size() < 1)
            return null;

        Coordinates obnd0coord = OuterBoundary.get(0);

        Dimensions d = new Dimensions(
                obnd0coord.getLongitude(), obnd0coord.getLongitude(),
                obnd0coord.getLatitude(), obnd0coord.getLatitude());

        for (Coordinates c : OuterBoundary) {
            if (c.getLatitude() < d.South)
                d.South = c.getLatitude();

            if (c.getLatitude() > d.North)
                d.North = c.getLatitude();

            if (c.getLongitude() < d.West)
                d.West = c.getLongitude();

            if (c.getLongitude() > d.East)
                d.East = c.getLongitude();
        }

        return d;
    }

    public Dimensions getInnerDimensions() {
        if (InnerBoundary.size() < 1)
            return null;

        Coordinates ibnd0coord = InnerBoundary.get(0);

        Dimensions d = new Dimensions(
                ibnd0coord.getLongitude(), ibnd0coord.getLongitude(),
                ibnd0coord.getLatitude(), ibnd0coord.getLatitude());

        for (Coordinates c : InnerBoundary) {
            if (c.getLatitude() < d.South)
                d.South = c.getLatitude();

            if (c.getLatitude() > d.North)
                d.North = c.getLatitude();

            if (c.getLongitude() < d.West)
                d.West = c.getLongitude();

            if (c.getLongitude() > d.East)
                d.East = c.getLongitude();
        }

        return d;
    }
}
