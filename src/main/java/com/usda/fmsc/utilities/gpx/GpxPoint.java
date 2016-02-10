package com.usda.fmsc.utilities.gpx;

import org.joda.time.DateTime;

public class GpxPoint {
    private String ID;
    private double Latitude;
    private double Longitude;
    private Double Altitude;
    private DateTime Time;
    private Double MagVar;
    private Double GeoidHeight;

    private String Name;
    private String Description;
    private String Comment;
    private String Source;
    private String Link;
    private String Symmetry;
    private Integer Fix;
    private Integer SatteliteNum;
    private Double HDOP;
    private Double PDOP;
    private Double VDOP;
    private Double AgeOfData;
    private String DGpsId;
    private String Extensions;

    public GpxPoint(double lat, double lon) {
        this(lat, lon, null);
    }

    public GpxPoint(double lat, double lon, Double alt) {
        ID = java.util.UUID.randomUUID().toString();
        Latitude = lat;
        Longitude = lon;
        Altitude = alt;
    }

    public enum PointType {
        WayPoint,
        RoutePoint,
        TrackPoint
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public Double getAltitude() {
        return Altitude;
    }

    public void setAltitude(Double altitude) {
        Altitude = altitude;
    }

    public DateTime getTime() {
        return Time;
    }

    public void setTime(DateTime time) {
        Time = time;
    }

    public Double getMagVar() {
        return MagVar;
    }

    public void setMagVar(Double magVar) {
        MagVar = magVar;
    }

    public Double getGeoidHeight() {
        return GeoidHeight;
    }

    public void setGeoidHeight(Double geoidHeight) {
        GeoidHeight = geoidHeight;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getSymmetry() {
        return Symmetry;
    }

    public void setSymmetry(String symmetry) {
        Symmetry = symmetry;
    }

    public Integer getFix() {
        return Fix;
    }

    public void setFix(Integer fix) {
        Fix = fix;
    }

    public Integer getSatteliteNum() {
        return SatteliteNum;
    }

    public void setSatteliteNum(Integer satteliteNum) {
        SatteliteNum = satteliteNum;
    }

    public Double getHDOP() {
        return HDOP;
    }

    public void setHDOP(Double HDOP) {
        this.HDOP = HDOP;
    }

    public Double getPDOP() {
        return PDOP;
    }

    public void setPDOP(Double PDOP) {
        this.PDOP = PDOP;
    }

    public Double getVDOP() {
        return VDOP;
    }

    public void setVDOP(Double VDOP) {
        this.VDOP = VDOP;
    }

    public Double getAgeOfData() {
        return AgeOfData;
    }

    public void setAgeOfData(Double ageOfData) {
        AgeOfData = ageOfData;
    }

    public String getDGpsId() {
        return DGpsId;
    }

    public void setDGpsId(String DGpsId) {
        this.DGpsId = DGpsId;
    }

    public String getExtensions() {
        return Extensions;
    }

    public void setExtensions(String extensions) {
        Extensions = extensions;
    }
}