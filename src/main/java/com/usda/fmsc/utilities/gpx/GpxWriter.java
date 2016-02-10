package com.usda.fmsc.utilities.gpx;

import com.usda.fmsc.utilities.StringEx;
import com.usda.fmsc.utilities.XmlWriter;
import com.usda.fmsc.utilities.gpx.GpxPoint.PointType;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class GpxWriter extends XmlWriter {
    private static DateTimeFormatter dtf = DateTimeFormat.forPattern("M/d/yyyy h:mm:ss a");

    public GpxWriter(String fileName) throws IOException {
        super(fileName);
    }

    public void writeStartGpx(GpxDocument doc) throws IOException {
        startDocument("UTF-8", null);

        setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        //setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-indentation", "    ");

        startElement("gpx");
        attribute("version", "1.1");
        attribute("creator", doc.getCreator());
    }

    public void writeEndGpx() throws IOException {
        endElement();
        endDocument();
        close();
    }

    public void writeDocument(GpxDocument doc) throws IOException {
        if (doc.getMetaData() != null)
            writeGpxMetadata(doc.getMetaData());

        if (doc.getWaypoints() != null && doc.getWaypoints().size() > 0) {
            for (GpxPoint point : doc.getWaypoints()) {
                writeGpxPoint(point, PointType.WayPoint);
            }
        }

        if (doc.getRoutes() != null && doc.getRoutes().size() > 0) {
            for (GpxRoute route : doc.getRoutes()) {
                writeGpxRoute(route);
            }
        }

        if (doc.getTracks() != null && doc.getTracks().size() > 0) {
            for (GpxTrack track : doc.getTracks()) {
                writeGpxTrack(track);
            }
        }

        if (doc.getExtensions() != null)
            writeGpxExtensions(doc.getExtensions());
    }


    public void writeGpxRoute(GpxRoute route) throws IOException {
        if (route != null) {
            startElement("rte");

            if (!StringEx.isEmpty(route.getName()))
                writeElement("name", route.getName());

            if (!StringEx.isEmpty(route.getComment()))
                writeElement("cmt", route.getComment());

            if (!StringEx.isEmpty(route.getDescription()))
                writeElement("desc", route.getDescription());

            if (!StringEx.isEmpty(route.getSource()))
                writeElement("src", route.getSource());

            if (!StringEx.isEmpty(route.getLink()))
                writeElement("link", route.getLink());

            if (route.getNumber() != null)
                writeElement("number", route.getNumber().toString());

            if (!StringEx.isEmpty(route.getType()))
                writeElement("type", route.getType());

            if (route.getExtensions() != null)
                writeGpxExtensions(route.getExtensions());

            for (GpxPoint point : route.getPoints()) {
                writeGpxPoint(point, PointType.RoutePoint);
            }

            //end of route
            endElement();
        }
    }

    public void writeGpxTrack(GpxTrack track) throws IOException {
        if (track != null) {
            startElement("trk");

            if (!StringEx.isEmpty(track.getName()))
                writeElement("name", track.getName());

            if (!StringEx.isEmpty(track.getComment()))
                writeElement("cmt", track.getComment());

            if (!StringEx.isEmpty(track.getDescription()))
                writeElement("desc", track.getDescription());

            if (!StringEx.isEmpty(track.getSource()))
                writeElement("src", track.getSource());

            if (!StringEx.isEmpty(track.getLink()))
                writeElement("link", track.getLink());

            if (track.getNumber() != null)
                writeElement("number", track.getNumber().toString());

            if (!StringEx.isEmpty(track.getType()))
                writeElement("type", track.getType());

            if (track.getExtensions() != null)
                writeGpxExtensions(track.getExtensions());

            if (track.Segments != null) {
                for (GpxTrackSeg seg : track.Segments) {
                    writeGpxTrackSegment(seg);
                }
            }

            //end of route
            endElement();
        }
    }

    public void writeGpxTrackSegment(GpxTrackSeg trackseg) throws IOException {
        if (trackseg != null) {
            startElement("trkseg");

            if (trackseg.getPoints() != null) {
                for (GpxPoint point : trackseg.getPoints()) {
                    writeGpxPoint(point, PointType.TrackPoint);
                }
            }

            writeGpxExtensions(trackseg.getExtensions());

            endElement();
        }
    }

    public void writeGpxPoint(GpxPoint point) throws IOException {
        writeGpxPoint(point, PointType.WayPoint);
    }

    public void writeGpxPoint(GpxPoint point, PointType type) throws IOException {
        if (point != null) {
            if (type == PointType.RoutePoint) {
                startElement("rtept");

            } else if (type == PointType.TrackPoint) {
                startElement("trkpt");

            } else {
                startElement("wpt");

            }

            attribute("lat", StringEx.toString(point.getLatitude()));
            attribute("lon", StringEx.toString(point.getLongitude()));

            if (point.getAltitude() != null)
                writeElement("ele", StringEx.toString(point.getAltitude()));

            if (point.getTime() != null)
                writeElement("time", dtf.print(point.getTime()));

            if (point.getMagVar() != null)
                writeElement("magvar", StringEx.toString(point.getMagVar()));

            if (point.getGeoidHeight() != null)
                writeElement("geoidheight", StringEx.toString(point.getGeoidHeight()));

            if (!StringEx.isEmpty(point.getName()))
                writeElement("name", point.getName());

            if (!StringEx.isEmpty(point.getComment()))
                writeElement("cmt", point.getComment());

            if (!StringEx.isEmpty(point.getDescription()))
                writeElement("desc", point.getDescription());

            if (!StringEx.isEmpty(point.getSource()))
                writeElement("src", point.getSource());

            if (!StringEx.isEmpty(point.getLink()))
                writeElement("link", point.getLink());

            if (!StringEx.isEmpty(point.getSymmetry()))
                writeElement("sym", point.getSymmetry());

            if (point.getFix() != null)
                writeElement("fix", StringEx.toString(point.getFix()));

            if (point.getSatteliteNum() != null)
                writeElement("sat", StringEx.toString(point.getSatteliteNum()));

            if (point.getHDOP() != null)
                writeElement("hdop", StringEx.toString(point.getHDOP()));

            if (point.getVDOP() != null)
                writeElement("vdop", StringEx.toString(point.getVDOP()));

            if (point.getPDOP() != null)
                writeElement("pdop", StringEx.toString(point.getPDOP()));

            if (point.getAgeOfData() != null)
                writeElement("ageofdata", StringEx.toString(point.getAgeOfData()));

            if (!StringEx.isEmpty(point.getDGpsId()))
                writeElement("dgpsid", point.getDGpsId());

            writeGpxExtensions(point.getExtensions());

            endElement();
        }
    }

    public void writeGpxMetadata(GpxMetadata meta) throws IOException {
        if (meta != null) {
            startElement("metadata");

            if (!StringEx.isEmpty(meta.getName()))
                writeElement("name", meta.getName());

            if (!StringEx.isEmpty(meta.getDescription()))
                writeElement("desc", meta.getDescription());

            if (!StringEx.isEmpty(meta.getAuthor()))
                writeElement("author", meta.getAuthor());

            if (!StringEx.isEmpty(meta.getCopyright()))
                writeElement("copyright", meta.getCopyright());

            if (!StringEx.isEmpty(meta.getLink()))
                writeElement("link", meta.getLink());

            if (meta.Time != null)
                writeElement("time", meta.Time.toString());

            if (meta.Keywords != null && meta.Keywords.size() > 0) {
                StringBuilder sb = new StringBuilder();

                for (String keyword : meta.Keywords) {
                    sb.append(keyword);
                    sb.append(", ");
                }

                writeElement("keywords", sb.toString().substring(0, sb.length() - 1));
            }

            if (meta.getExtensions() != null)
                writeGpxExtensions(meta.getExtensions());

            endElement();
        }
    }


    public void writeGpxExtensions(String extenstions) throws IOException {
        if (!StringEx.isEmpty(extenstions))
            writeElement("extensions", extenstions);
    }


    public static void createFile(GpxDocument document, String fileName) throws IOException {
        GpxWriter writer = new GpxWriter(fileName);

        writer.writeStartGpx(document);
        writer.writeDocument(document);
        writer.writeEndGpx();
    }
}
