package com.usda.fmsc.utilities.kml;

import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.usda.fmsc.utilities.kml.Types.*;
import com.usda.fmsc.utilities.XmlWriter;

public class KmlWriter extends XmlWriter {
    public KmlWriter(File file) throws IOException {
        super(file);
    }

    public void writeStartKml() throws IOException {
        startDocument("UTF-8", null);

        setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        startElement("kml");
        attribute("xmlns", "http://www.opengis.net/kml/2.2");
        attribute("xmlns:gx", "http://www.google.com/kml/ext/2.2");
    }

    public void writeEndKml() throws IOException {
        endElement();
        endDocument();
        close();
    }

    public void writeDocument(KmlDocument doc) throws IOException {
        startElement("Document");

        startElement("name");
        if (doc.getName() != null) {
            value(doc.getName());
        } else {
            value("Kml File");
        }
        endElement();

        writeDescription(doc.getDescription());

        if (doc.getVisibility() != null) {
            writeElement("visibility", convertBool(doc.getVisibility()));
        }

        if (doc.isOpen() != null) {
            writeElement("open", convertBool(doc.isOpen()));
        }

        writeProperties(doc);

        for (Style style : doc.getStyles()) {
            writeStyle(style);
        }

        for (StyleMap map : doc.getStyleMaps()) {
            writeStyleMap(map);
        }

        for (Folder folder : doc.getSubFolders()) {
            writeFolder(folder);
        }

        for (Placemark placemark : doc.getPlacemarks()) {
            writePlacemark(placemark);
        }

        endElement();
    }


    public void writeFolder(Folder folder) throws IOException {
        if (folder != null) {
            startElement("Folder");
            comment("Folder Guid: " + folder.getCN());

            writeElement("name", folder.getName());
            writeDescription(folder.getDescription());

            if(folder.getStyleUrl() != null)
                writeElement("styleUrl", folder.getStyleUrl());

            if (folder.getVisibility() != null)
                writeElement("visibility", convertBool(folder.getVisibility()));
            
            if (folder.isOpen() != null)
                writeElement("open", convertBool(folder.isOpen()));

            writeProperties(folder);

            for (Folder subFolder : folder.getSubFolders()) {
                writeFolder(subFolder);
            }
            
            for (Placemark pm : folder.getPlacemarks()) {
                writePlacemark(pm);
            }

            //end folder
            endElement();
        }
    }

    public void writePlacemark(Placemark pm) throws IOException {
        if (pm != null) {
            startElement("Placemark");
            comment("Placemark Guid: " + pm.getCN());

            writeElement("name", pm.getName());

            writeDescription(pm.getDesctription());

            ArrayList<Polygon> polygons = pm.getPolygons();
            ArrayList<Point> points = pm.getPoints();
            
            if (pm.getView() != null && pm.getView().getCoordinates() != null)
                writeView(pm.getView());
            else {
                View v = new View();

                Polygon poly;
                
                Point point;

                if (polygons != null && polygons.size() > 0) {
                    poly = polygons.get(0);
                    
                    v.setCoordinates(poly.getAveragedCoords());
                    v.setAltMode(poly.getAltMode());
                } else if (points != null && points.size() > 0) {
                    point = points.get(0);
                    
                    v.setCoordinates(point.getCoordinates());
                    v.setAltMode(point.getAltMode());
                }

                writeView(v);
            }

            if(pm.getStyleUrl() != null)
                writeElement("styleUrl", pm.getStyleUrl());

            if (pm.getVisibility() != null)
                writeElement("visibility", convertBool(pm.getVisibility()));

            if (pm.isOpen() != null)
                writeElement("open", convertBool(pm.isOpen()));

            writeProperties(pm);


            if (points != null && polygons != null ) {
                if (points.size() + polygons.size() > 1) {
                    startElement("MultiGeometry");

                    for (Polygon poly : polygons) {
                        writePolygon(poly);
                    }

                    for (Point point : points) {
                        writePoint(point);
                    }

                    //end MultiGeo
                    endElement();
                } else {
                    if (polygons.size() > 0) {
                        writePolygon(polygons.get(0));
                    } else if (points.size() > 0) {
                        writePoint(points.get(0));
                    }
                }
            }

            //end placemark
            endElement();
        }
    }

    public void writePolygon(Polygon poly) throws IOException {
        if (poly != null) {
            if (!poly.isPath())
                startElement("Polygon");
            else
                startElement("LineString");

            if (poly.getName() != null)
                attribute("id", poly.getName());

            comment("Poly Guid: " + poly.getCN());

            writeElement("extrude", convertBool(poly.isExtrude()));
            writeElement("tessellate", convertBool(poly.isTessellate()));

            if (poly.getAltMode() != null) {
                if (poly.getAltMode() == AltitudeMode.clampToGround || poly.getAltMode() == AltitudeMode.relativeToGround || poly.getAltMode() == AltitudeMode.absolute)
                    writeElement("altitudeMode", poly.getAltMode().toString());
                else
                    writeElement("gx:altitudeMode", poly.getAltMode().toString());
            }

            if (!poly.isPath()) {
                startElement("outerBoundaryIs");
                startElement("LinearRing");
                startElement("coordinates");

                StringBuilder sb;
                for (Coordinates c : poly.getOuterBoundary()) {
                    sb = new StringBuilder();

                    sb.append(String.format("%f,%f", c.getLongitude(), c.getLatitude()));

                    if (c.getAltitude() != null)
                        sb.append(String.format(",%f ", c.getAltitude()));
                    else
                        sb.append(" ");

                    value(sb.toString());
                }

                endElement();
                endElement();
                endElement();

                if (poly.getInnerBoundary() != null && poly.getInnerBoundary().size() > 0) {
                    startElement("innerBoundaryIs");
                    startElement("LinearRing");
                    startElement("coordinates");

                    for (Coordinates c : poly.getInnerBoundary()) {
                        sb = new StringBuilder();

                        sb.append(String.format("%f,%f", c.getLongitude(), c.getLatitude()));

                        if (c.getAltitude() != null)
                            sb.append(String.format(",%f ", c.getAltitude()));
                        else
                            sb.append(" ");

                        value(sb.toString());
                    }

                    endElement();
                    endElement();
                    endElement();
                }
            } else {
                startElement("coordinates");

                StringBuilder sb;
                for (Coordinates c : poly.getOuterBoundary()) {
                    sb = new StringBuilder();

                    sb.append(String.format("%f,%f", c.getLongitude(), c.getLatitude()));

                    if (c.getAltitude() != null)
                        sb.append(String.format(",%f ", c.getAltitude()));
                    else
                        sb.append(" ");

                    value(sb.toString());
                }

                endElement();
            }

            //end poly
            endElement();
        }
    }

    public void writePoint(Point point) throws IOException {
        if (point != null) {
            startElement("Point");

            if (point.getName() != null)
                attribute("id", point.getName());

            comment("Point Guid: " + point.getCN());

            if (point.getExtrude() != null)
                writeElement("extrude", convertBool(point.getExtrude()));

            if (point.getName() != null) {
                if (point.getAltMode() == AltitudeMode.clampToGround || point.getAltMode() == AltitudeMode.relativeToGround || point.getAltMode() == AltitudeMode.absolute)
                    writeElement("altitudeMode", point.getAltMode().toString());
                else
                    writeElement("gx:altitudeMode", point.getAltMode().toString());
            }

            startElement("coordinates");

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%f,%f", point.getCoordinates().getLongitude(), point.getCoordinates().getLatitude()));

            if (point.getCoordinates().getAltitude() != null)
                sb.append(String.format(",%f ", point.getCoordinates().getAltitude()));
            else
                sb.append(" ");
            value(sb.toString());
            endElement();

            //end poly
            endElement();
        }
    }

    public void writeStyle(Style style) throws IOException {
        //start style
        startElement("Style");
        attribute("id", style.getID());

        //region Icon Style
        if (style.getIconUrl() != null || style.getIconColor() != null || style.getIconColorMode() != null ||
                style.getIconScale() != null || style.getIconHeading() != null || style.getIconHotSpot() != null) 
        {
            startElement("IconStyle");
            
            if (style.getIconID() != null)
                attribute("id", style.getIconID());

            if (style.getIconColor() != null)
                writeElement("color", style.getIconColor().toString());
            
            if (style.getIconUrl() != null) {
                startElement("Icon");
                writeElement("href", style.getIconUrl());
                endElement();
            }
            
            if (style.getIconScale() != null)
                writeElement("scale", style.getIconScale().toString());
            
            if (style.getIconColorMode() != null)
                writeElement("colorMode", style.getIconColorMode().toString());
            
            if (style.getIconHeading() != null)
                writeElement("heading", style.getIconHeading().toString());
            
            if (style.getIconHotSpot() != null) {
                startElement("hotSpot");
                attribute("x", Double.toString(style.getIconHotSpot().getX()));
                attribute("y", Double.toString(style.getIconHotSpot().getY()));
                attribute("xunits", style.getIconHotSpot().getXUnits().toString());
                attribute("yunits", style.getIconHotSpot().getYUnits().toString());
                endElement();
            }

            endElement();
        }
        //endregion

        //region Label Style
        if (style.getLabelColor() != null || style.getLabelColorMode() != null || style.getLabelScale() != null) {
            startElement("LabelStyle");
            if (style.getLabelID() != null)
                attribute("id", style.getLabelID());

            if (style.getLabelColor() != null)
                writeElement("color", style.getLabelColor().toString());
            
            if (style.getLabelColorMode() != null)
                writeElement("colorMode", style.getLabelColorMode().toString());
            
            if (style.getLabelScale() != null)
                writeElement("scale", style.getLabelScale().toString());

            endElement();
        }
        //endregion

        //region Line Style
        if (style.getLineColor() != null || style.getLineColorMode() != null || style.getLineLabelVisibility() != null ||
                style.getLineOuterColor() != null || style.getLineOuterWidth()  != null || style.getLinePhysicalWidth()  != null ||
                style.getLineWidth()  != null) {
            
            startElement("LineStyle");
            
            if (style.getLineID() != null)
                attribute("id", style.getLineID());

            if (style.getLineColor() != null)
                writeElement("color", style.getLineColor().toString());
            
            if (style.getLineColorMode() != null)
                writeElement("colorMode", style.getLineColorMode().toString());
            
            if (style.getLineWidth()  != null)
                writeElement("width", style.getLineWidth().toString());

            if (style.getLineOuterColor() != null)
                writeElement("gx:outerColor", style.getLineOuterColor().toString());
            
            if (style.getLineOuterWidth()  != null)
                writeElement("gx:outerWidth", style.getLineOuterWidth().toString());
            
            if (style.getLinePhysicalWidth()  != null)
                writeElement("gx:physicalWidth", style.getLinePhysicalWidth().toString());
            
            if (style.getLineLabelVisibility() != null)
                writeElement("gx:labelVisibility", convertBool(style.getLineLabelVisibility()));

            endElement();
        }
        //endregion

        //region Poly Style
        if (style.getPolygonColor() != null || style.getPolygonColorMode() != null ||
                style.getPolygonFill() != null || style.getPolygonOutline() != null) {
            
            startElement("PolyStyle");
            if (style.getPolygonID() != null)
                attribute("id", style.getPolygonID());

            if (style.getPolygonColor() != null)
                writeElement("color", style.getPolygonColor().toString());
            
            if (style.getPolygonColorMode() != null)
                writeElement("colorMode", style.getPolygonColorMode().toString());
            
            if (style.getPolygonFill() != null)
                writeElement("fill", convertBool(style.getPolygonFill()));
            
            if (style.getPolygonOutline() != null)
                writeElement("outline", convertBool(style.getPolygonOutline()));

            endElement();
        }
        //endregion

        //region Balloon Style
        if (style.getBalloonBgColor() != null || style.getBalloonTextColor() != null ||
                style.getBalloonText() != null || style.getBalloonDisplayMode() != null) {

            startElement("BalloonStyle");
            if (style.getBalloonID() != null)
                attribute("id", style.getBalloonID());

            if (style.getBalloonBgColor() != null)
                writeElement("bgColor", style.getBalloonBgColor().toString());
            
            if (style.getBalloonTextColor() != null)
                writeElement("textColor", style.getBalloonTextColor().toString());
            
            if (style.getBalloonText() != null)
                writeElement("text", style.getBalloonText());
            
            if (style.getBalloonDisplayMode() != null)
                writeElement("displayMode", style.getBalloonDisplayMode().toString());

            endElement();
        }
        //endregion

        //region List Style
        if (style.getListBgColor() != null || style.getListItemIconUrl() != null ||
                style.getListItemState() != null || style.getListListItemType() != null) {

            startElement("listItemType");
            if (style.getListID() != null)
                attribute("id", style.getListID());

            if (style.getListListItemType() != null)
                writeElement("listItemType", style.getListListItemType().toString());

            if (style.getListBgColor() != null)
                writeElement("bgColor", style.getListBgColor().toString());

            if (style.getListItemState() != null || style.getListItemIconUrl() != null) {
                startElement("ItemIcon");

                if (style.getListItemState() != null)
                    writeElement("state", style.getListItemState().toString());

                if (style.getListItemIconUrl() != null)
                    writeElement("href", style.getListItemIconUrl());

                endElement();
            }
            endElement();
        }
        //endregion

        //end style
        endElement();
    }

    public void writeStyleMap(StyleMap map) throws IOException {
        if (map != null && map.getHightLightedStyleUrl() != null && map.getNormalStyleUrl() != null) {
            startElement("StyleMap");
            attribute("id", map.getID());

            startElement("Pair");
            writeElement("key", "normal");
            writeElement("styleUrl", map.getNormalStyleUrl());
            endElement();

            startElement("Pair");
            writeElement("key", "highlight");
            writeElement("styleUrl", map.getHightLightedStyleUrl());
            endElement();

            endElement();
        }
    }

    public void writeView(View v) throws IOException {
        startElement("LookAt");

        if (v.getTimeSpan() != null && v.getTimeSpan().StartTime != null && v.getTimeSpan().EndTime != null) {
            startElement("gx:TimeSpan");
            writeElement("begin", convertToKmlDateTime(v.getTimeSpan().StartTime));
            writeElement("end", convertToKmlDateTime(v.getTimeSpan().EndTime));
            endElement();
        }

        if (v.getTimeStamp() != null) {
            startElement("gx:TimeStamp");
            writeElement("when", convertToKmlDateTime(v.getTimeStamp()));
            endElement();
        }

        if (v.getCoordinates() != null) {
            if (v.getCoordinates().getLongitude() != null)
                writeElement("longitude", v.getCoordinates().getLongitude().toString());

            if (v.getCoordinates().getLatitude() != null)
                writeElement("latitude", v.getCoordinates().getLatitude().toString());

            if (v.getCoordinates().getAltitude() != null)
                writeElement("altitude", v.getCoordinates().getAltitude().toString());
        }

        writeElement("range", Double.toString(v.getRange()));

        if (v.getTilt() != null)
            writeElement("tilt", v.getTilt().toString());

        if (v.getHeading() != null)
            writeElement("heading", v.getHeading().toString());

        if (v.getAltMode() != null) {
            if (v.getAltMode() == AltitudeMode.absolute || v.getAltMode() == AltitudeMode.clampToGround || v.getAltMode() == AltitudeMode.relativeToGround)
                writeElement("altitudeMode", v.getAltMode().toString());
            else
                writeElement("gx:altitudeMode", v.getAltMode().toString());
        }

        endElement();
    }

    public void writeDescription(String description) throws IOException {
        if (description != null && description.length() > 0) {
            startElement("description");
            cdsect(description);
            endElement();
        }
    }

    public void writeProperties(Properties properties) throws IOException {
        if (properties != null) {
            if (properties.getAuthor() != null)
                writeElement("atom:author", properties.getAuthor());

            if (properties.getLink() != null)
                writeElement("atom:link", properties.getLink());

            if (properties.getAddress() != null)
                writeElement("address", properties.getAddress());

            if (properties.getSnippit() != null) {
                startElement("Snippit");

                if (properties.getSnippitMaxLines() != null)
                    attribute("maxLines", properties.getSnippitMaxLines().toString());
                else
                    attribute("maxLines", "2");

                value(properties.getSnippit());
                endElement();
            }

            if (properties.getRegion() != null)
                writeElement("region", properties.getRegion());

            //write extended data
            if (properties.getExtendedData() != null && properties.getExtendedData().getDataItems().size() > 0) {
                startElement("ExtendedData");

                for (ExtendedData.Data data : properties.getExtendedData().getDataItems())
                {
                    if (data.getName() != null)
                    {
                        startElement("Data");
                        attribute("name", data.getName());
                        writeElement("value", data.getValue());
                        endElement();
                    }
                }

                endElement();
            }
        }
    }

    //region converters
    private String convertBool(Boolean b) {
        return b ? "1" : "0";
    }

    private String convertToKmlDateTime(DateTime dt) {
        return String.format("%04d-%02d-%02dT%02d:%02d:%02dZ",
                dt.getYear(),
                dt.getMonthOfYear(),
                dt.getDayOfMonth(),
                dt.getHourOfDay(),
                dt.getMinuteOfHour(),
                dt.getSecondOfMinute());
    }
    //endregion


    public static void write(File file, KmlDocument document) throws IOException  {
        KmlWriter writer = new KmlWriter(file);

        writer.writeStartKml();
        writer.writeDocument(document);
        writer.writeEndKml();
    }
}
