package com.usda.fmsc.utilities.kml;

import com.usda.fmsc.utilities.kml.Types.AltitudeMode;

public class Point {
    private String CN;
    private AltitudeMode AltMode;
    private String Name;
    private Coordinates Coordinates;
    private Boolean Extrude;


    public Point() {
        this(new Coordinates(0.0, 0.0), AltitudeMode.clampToGround);
    }

    public Point(Coordinates c) {
        this(c, AltitudeMode.clampToGround);
    }

    public Point(Coordinates c, AltitudeMode altMode)
    {
        Coordinates = c;
        AltMode = altMode;
        CN = java.util.UUID.randomUUID().toString();
    }


    public String getCN() {
        return CN;
    }

    public AltitudeMode getAltMode() {
        return AltMode;
    }

    public void setAltMode(AltitudeMode altMode) {
        AltMode = altMode;

        Extrude = AltMode == null || (AltMode == AltitudeMode.clampToSeaFloor || AltMode == AltitudeMode.clampToGround);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Coordinates getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        Coordinates = coordinates;
    }

    public Boolean getExtrude() {
        return Extrude;
    }

    public void setExtrude(Boolean extrude) {
        Extrude = extrude;
    }
}
