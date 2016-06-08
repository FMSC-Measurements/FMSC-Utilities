package com.usda.fmsc.utilities.kml;

import org.joda.time.DateTime;

public class View {
    public static class TimeSpan {
        public DateTime StartTime;
        public DateTime EndTime;

        public TimeSpan(DateTime s, DateTime e) {
            StartTime = s;
            EndTime = e;
        }
    }

    private TimeSpan TimeSpan;
    private DateTime TimeStamp;
    private Coordinates Coordinates;
    private Double Heading;
    private Double Tilt;
    private double Range;
    private Types.AltitudeMode AltMode = Types.AltitudeMode.clampToGround;


    public View() {
        Range = 1000;
    }

    public View(Coordinates c, double h, double t, double r) {
        Coordinates = c;
        Heading = h;
        Tilt = t;
        Range = r;
    }

    public View(View v) {
        TimeSpan = v.TimeSpan;
        TimeStamp = v.TimeStamp;
        Coordinates = v.Coordinates;
        Heading = v.Heading;
        Tilt = v.Tilt;
        Range = v.Range;
    }


    public TimeSpan getTimeSpan() {
        return TimeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        TimeSpan = timeSpan;
    }

    public DateTime getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        TimeStamp = timeStamp;
    }

    public Coordinates getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        Coordinates = coordinates;
    }

    public Double getHeading() {
        return Heading;
    }

    public void setHeading(Double heading) {
        Heading = heading;
    }

    public Double getTilt() {
        return Tilt;
    }

    public void setTilt(Double tilt) {
        Tilt = tilt;
    }

    public double getRange() {
        return Range;
    }

    public void setRange(double range) {
        Range = range;
    }

    public Types.AltitudeMode getAltMode() {
        return AltMode;
    }

    public void setAltMode(Types.AltitudeMode altMode) {
        AltMode = altMode;
    }
}

