package com.usda.fmsc.utilities.kml;

public class Coordinates {
    private Double Latitude;
    private Double Longitude;
    private Double Altitude;

    public Coordinates(Double lat, Double lon) {
        this(lat, lon, null);
    }

    public Coordinates(Double lat, Double lon, Double altitude) {
        Latitude = lat;
        Longitude = lon;
        Altitude = altitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getAltitude() {
        return Altitude;
    }

    public void setAltitude(Double elevation) {
        Altitude = elevation;
    }
}