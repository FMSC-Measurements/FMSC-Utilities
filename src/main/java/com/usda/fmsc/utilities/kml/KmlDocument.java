package com.usda.fmsc.utilities.kml;

import com.usda.fmsc.utilities.FileUtils;
import com.usda.fmsc.utilities.ParseEx;
import com.usda.fmsc.utilities.StringEx;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class KmlDocument extends Folder {
    private ArrayList<Style> Styles = new ArrayList<>();
    private ArrayList<StyleMap> StyleMaps = new ArrayList<>();


    public KmlDocument() {
        super(null, null);
    }

    public KmlDocument(String name) {
        super(name, null);
    }

    public KmlDocument(String name, String desc) {
        super(name, desc);
    }

    public KmlDocument(Folder folder) {
        super(folder);
    }

    public KmlDocument(KmlDocument doc) {
        super(doc);

        Styles = new ArrayList<>(doc.getStyles());
        StyleMaps = new ArrayList<>(doc.getStyleMaps());
    }


    public void addStyle(Style s) {
        if (s != null)
            Styles.add(s);
    }

    public void removeStyleById(String id) {
        for (int i = 0; i < Styles.size(); i++) {
            if (Styles.get(i).getID().equals(id)) {
                Styles.remove(i);
                return;
            }
        }
    }

    public Style getStyleById(String id) {
        for (Style s : Styles) {
            if (s.getID().equals(id))
                return s;
        }

        return null;
    }


    public void addStyleMap(StyleMap s) {
        if (s != null)
            StyleMaps.add(s);
    }

    public void removeStyleMapById(String id) {
        for (int i = 0; i < StyleMaps.size(); i++) {
            if (StyleMaps.get(i).getID().equals(id)) {
                StyleMaps.remove(i);
                return;
            }
        }
    }

    public StyleMap getStyleMapById(String id) {
        for (StyleMap s : StyleMaps) {
            if (s.getID().equals(id))
                return s;
        }

        return null;
    }


    public ArrayList<Style> getStyles() {
        return Styles;
    }

    public ArrayList<StyleMap> getStyleMaps() {
        return StyleMaps;
    }


    public static KmlDocument load(String filename) throws ParserConfigurationException, SAXException, IOException {
        if (filename.endsWith(".kmz")) {
            ZipFile zipFile = new ZipFile(filename);
            return load(new InputSource(zipFile.getInputStream(zipFile.entries().nextElement())));
        } else {
            return load(new File(filename));
        }
    }

    public static KmlDocument load(File file) throws ParserConfigurationException, IOException, SAXException {
        return load(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file));
    }

    public static KmlDocument load(InputSource source) throws ParserConfigurationException, IOException, SAXException {
        return load(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(source));
    }

    public static KmlDocument load(Document doc) {
        KmlDocument kdoc = new KmlDocument();

        NodeList nodes = doc.getElementsByTagName("Document");
        if (nodes.getLength() > 0) {
            nodes = nodes.item(0).getChildNodes();
        } else {
            throw new RuntimeException("Invalid Kml File");
        }

        parseFolderData(kdoc, nodes);
        parsePropertyData(kdoc, nodes);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "style": kdoc.addStyle(parseStyle(node)); break;
                case "stylemap": kdoc.addStyleMap(parseStyleMap(node)); break;
            }
        }

        return kdoc;
    }

    private static void parseFolderData(Folder folder, NodeList folderNodes) {
        for (int i = 0; i < folderNodes.getLength(); i++) {
            Node node = folderNodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "folder": folder.addFolder(parseFolder(node)); break;
                case "placemark": folder.addPlacemark(parsePlacemark(node)); break;
                case "name": folder.setName(node.getTextContent()); break;
                case "description": folder.setDescription(node.getTextContent()); break;
                case "styleurl": folder.setStyleUrl(node.getTextContent()); break;
                case "open": folder.setOpen(ParseEx.parseBoolean(node.getTextContent())); break;
                case "visibility": folder.setVisibility(ParseEx.parseBoolean(node.getTextContent())); break;
            }
        }
    }

    private static void parsePropertyData(Properties properties, NodeList propertyNodes) {
        for (int i = 0; i < propertyNodes.getLength(); i++) {
            Node node = propertyNodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "atom:author": properties.setAuthor(node.getTextContent()); break;
                case "atom:link": properties.setLink(node.getTextContent()); break;
                case "address": properties.setAddress(node.getTextContent()); break;
                case "snippit": {
                    properties.setSnippit(node.getTextContent());
                    NamedNodeMap map = node.getAttributes();
                    if (map != null) {
                        Node ml = map.getNamedItem("maxLines");
                        if (ml != null) {
                            properties.setSnippitMaxLines(ParseEx.parseInteger(ml.getTextContent(), 2));
                        }
                    }
                    break;
                }
                case "region": properties.setRegion(node.getTextContent()); break;
                case "extendeddata": {

                    NodeList edNodes = node.getChildNodes();

                    ExtendedData extendedData = new ExtendedData();

                    for (int j = 0; j < edNodes.getLength(); j++) {
                        node = edNodes.item(j);
                        if (node.getNodeName().toLowerCase().equals("data")) {
                            try {
                                extendedData.add(new ExtendedData.Data(
                                        node.getAttributes().getNamedItem("name").getTextContent(),
                                        node.getTextContent()
                                ));
                            } catch (DOMException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    properties.setExtendedData(extendedData);
                    break;
                }
            }
        }
    }


    private static Folder parseFolder(Node node) {
        Folder folder = new Folder(StringEx.Empty);

        NodeList nodes = node.getChildNodes();

        parseFolderData(folder, nodes);
        parsePropertyData(folder, nodes);

        return folder;
    }

    private static Placemark parsePlacemark(Node placemarkNodes) {
        NodeList nodes = placemarkNodes.getChildNodes();

        Placemark placemark = new Placemark("");

        parsePropertyData(placemark, nodes);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "name": placemark.setName(node.getTextContent()); break;
                case "description": placemark.setDescription(node.getTextContent()); break;
                case "lookat": placemark.setView(parseView(node)); break;
                case "styleUrl": placemark.setStyleUrl(node.getTextContent()); break;
                case "visibility": placemark.setVisibility(ParseEx.parseBoolean(node.getTextContent())); break;
                case "open": placemark.setOpen(ParseEx.parseBoolean(node.getTextContent())); break;
                case "polygon": placemark.addPolygon(parsePolygon(node)); break;
                case "point": placemark.addPoint(parsePoint(node)); break;
                case "multigeometry": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);
                        switch (node.getNodeName().toLowerCase()) {
                            case "polygon": placemark.addPolygon(parsePolygon(node)); break;
                            case "point": placemark.addPoint(parsePoint(node)); break;
                        }
                    }
                    break;
                }
            }
        }

        return placemark;
    }

    private static Polygon parsePolygon(Node polyNode) {
        Polygon poly = null;

        NamedNodeMap map = polyNode.getAttributes();
        if (map != null) {
            Node id = map.getNamedItem("id");
            if (id != null) {
                poly = new Polygon(id.getTextContent());
            }
        }

        boolean isPolyLine = polyNode.getNodeName().equalsIgnoreCase("linestring");

        if (poly == null) {
            poly = new Polygon(isPolyLine ? "PolyLine" : "Polygon");
        }

        NodeList nodes = polyNode.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "extrude": poly.setExtrude(ParseEx.parseBoolean(node.getTextContent())); break;
                case "tessellate": poly.setExtrude(ParseEx.parseBoolean(node.getTextContent())); break;
                case "altitudemode":
                case "gx:altitudemode": poly.setAltMode(Types.Parse.AltitudeMode(node.getTextContent())); break;
                case "coordinates": poly.setOuterBoundary(getCoordinates(node.getTextContent())); break;
                case "outerboundaryis": poly.setOuterBoundary(getCoordinates(node.getChildNodes().item(1).getChildNodes().item(1).getTextContent())); break;
                case "innerboundaryis": poly.setInnerBoundary(getCoordinates(node.getChildNodes().item(1).getChildNodes().item(1).getTextContent())); break;
            }
        }

        return poly;
    }

    private static ArrayList<Coordinates> getCoordinates(String coordsStr) {
        String[] coordsList = coordsStr.split(" ");
        String[] values;
        ArrayList<Coordinates> coords = new ArrayList<>();

        for (int i = 0; i < coordsList.length; i++) {
            values = coordsList[i].split(",");

            coords.add(values.length > 2 ?
                    new Coordinates(
                        ParseEx.parseDouble(values[1]),
                        ParseEx.parseDouble(values[0]),
                        ParseEx.parseDouble(values[2])) :
                    new Coordinates(
                        ParseEx.parseDouble(values[1]),
                        ParseEx.parseDouble(values[0])));
        }

        return coords;
    }

    private static Point parsePoint(Node pointNode) {
        Point point = new Point();

        NamedNodeMap map = pointNode.getAttributes();
        if (map != null) {
            Node id = map.getNamedItem("id");
            if (id != null) {
                point.setName(id.getTextContent());
            }
        }

        NodeList nodes = pointNode.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "extrude": point.setExtrude(ParseEx.parseBoolean(node.getTextContent())); break;
                case "altitudemode":
                case "gx:altitudemode": point.setAltMode(Types.Parse.AltitudeMode(node.getTextContent())); break;
                case "coordinates": {
                    String[] values = node.getTextContent().split(",");

                    point.setCoordinates(
                            values.length > 2 ?
                            new Coordinates(
                                ParseEx.parseDouble(values[1]),
                                ParseEx.parseDouble(values[0]),
                                ParseEx.parseDouble(values[2])) :
                            new Coordinates(
                                ParseEx.parseDouble(values[1]),
                                ParseEx.parseDouble(values[0]))
                    );
                    break;
                }
            }
        }

        return point;
    }

    private static Style parseStyle(Node stylNode) {
        Style style = new Style();

        NodeList nodes = stylNode.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "iconstyle": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);

                        switch (node.getNodeName().toLowerCase()) {
                            case "id": style.setIconID(node.getTextContent()); break;
                            case "color": style.setIconColor(new Color(node.getTextContent())); break;
                            case "icon": style.setIconID(node.getChildNodes().item(0).getTextContent()); break;
                            case "scale": style.setIconScale(ParseEx.parseDouble(node.getTextContent())); break;
                            case "colormode": style.setIconColorMode(Types.Parse.ColorMode(node.getTextContent())); break;
                            case "heading": style.setIconHeading(ParseEx.parseDouble(node.getTextContent())); break;
                            case "hotspot": {
                                NodeList subNodes2 = node.getChildNodes();

                                HotSpot hotSpot = new HotSpot(0, 0, Types.XYUnitType.fraction, Types.XYUnitType.fraction);

                                for (int k = 0; k < subNodes2.getLength(); k++) {
                                    node = subNodes2.item(j);

                                    switch (node.getNodeName().toLowerCase()) {
                                        case "x": hotSpot.setX(ParseEx.parseDouble(node.getTextContent())); break;
                                        case "y": hotSpot.setY(ParseEx.parseDouble(node.getTextContent())); break;
                                        case "xunits": hotSpot.setXUnits(Types.Parse.XYUnitType(node.getTextContent())); break;
                                        case "yunits": hotSpot.setXUnits(Types.Parse.XYUnitType(node.getTextContent())); break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                case "labelstyle": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);

                        switch (node.getNodeName().toLowerCase()) {
                            case "id": style.setLabelID(node.getTextContent());
                            case "color": style.setLineColor(new Color(node.getTextContent())); break;
                            case "scale": style.setLineWidth(ParseEx.parseDouble(node.getTextContent())); break;
                            case "colormode": style.setLineColorMode(Types.Parse.ColorMode(node.getTextContent())); break;
                            case "gx:outercolor": style.setLineOuterColor(new Color(node.getTextContent())); break;
                            case "gx:outerwidth": style.setLineOuterWidth(ParseEx.parseDouble(node.getTextContent())); break;
                            case "gx:physicalwidth": style.setLinePhysicalWidth(ParseEx.parseDouble(node.getTextContent())); break;
                            case "gx:labelVisibility": style.setLineLabelVisibility(ParseEx.parseBoolean(node.getTextContent())); break;
                        }
                    }
                    break;
                }
                case "linestyle": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);

                        switch (node.getNodeName().toLowerCase()) {
                            case "id": style.setLineID(node.getTextContent());
                            case "color": style.setLabelColor(new Color(node.getTextContent())); break;
                            case "scale": style.setLabelScale(ParseEx.parseDouble(node.getTextContent())); break;
                            case "colormode": style.setLabelColorMode(Types.Parse.ColorMode(node.getTextContent())); break;
                        }
                    }
                    break;
                }
                case "polystyle": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);

                        switch (node.getNodeName().toLowerCase()) {
                            case "id": style.setPolygonID(node.getTextContent());
                            case "color": style.setPolygonColor(new Color(node.getTextContent())); break;
                            case "colormode": style.setPolygonColorMode(Types.Parse.ColorMode(node.getTextContent())); break;
                            case "fill": style.setPolygonFill(ParseEx.parseBoolean(node.getTextContent())); break;
                            case "outline": style.setPolygonOutline(ParseEx.parseBoolean(node.getTextContent())); break;
                        }
                    }
                    break;
                }
                case "balloonstyle": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);

                        switch (node.getNodeName().toLowerCase()) {
                            case "id": style.setBalloonID(node.getTextContent());
                            case "bgcolor": style.setBalloonBgColor(new Color(node.getTextContent())); break;
                            case "textcolor": style.setBalloonTextColor(new Color(node.getTextContent())); break;
                            case "text": style.setBalloonText(node.getTextContent()); break;
                            case "displaymode": style.setBalloonDisplayMode(Types.Parse.DisplayMode(node.getTextContent())); break;
                        }
                    }
                    break;
                }
                case "listitemtype": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);

                        switch (node.getNodeName().toLowerCase()) {
                            case "id": style.setListID(node.getTextContent());
                            case "listitemtype": style.setListListItemType(Types.Parse.ListItemType(node.getTextContent()));
                            case "bgcolor": style.setListBgColor(new Color(node.getTextContent())); break;
                            case "itemicon": {
                                    NodeList subNodes2 = node.getChildNodes();
                                    for (int k = 0; k < subNodes2.getLength(); k++) {
                                        node = subNodes2.item(k);

                                        switch (node.getNodeName().toLowerCase()) {
                                            case "state": style.setListItemState(Types.Parse.State(node.getTextContent())); break;
                                            case "href": style.setListItemIconUrl(node.getTextContent());
                                        }
                                    }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }

        return style;
    }

    private static StyleMap parseStyleMap(Node styleMapNode) {
        StyleMap styleMap = new StyleMap();

        NamedNodeMap map = styleMapNode.getAttributes();
        if (map != null) {
            Node id = map.getNamedItem("id");
            if (id != null) {
                styleMap = new StyleMap(id.getTextContent());
            }
        }

        NodeList nodes = styleMapNode.getChildNodes();

        String key = null;
        String url = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeName().toLowerCase().equals("pair")) {
                NodeList pairNodes = node.getChildNodes();
                for (int j = 0; j < pairNodes.getLength(); j++) {
                    node = pairNodes.item(j);
                    switch (node.getNodeName().toLowerCase()) {
                        case "key": key = node.getTextContent(); break;
                        case "styleurl": url = node.getTextContent(); break;
                    }
                }

                if (key != null && url != null) {
                    if (key.equalsIgnoreCase("normal")) {
                        styleMap.setNormalStyleUrl(url);
                    } else if (key.equalsIgnoreCase("highlight")) {
                        styleMap.setHightLightedStyleUrl(url);
                    }

                    key = null;
                    url = null;
                }
            }
        }

        return styleMap;
    }

    private static View parseView(Node viewNode) {
        View view = new View();

        NodeList nodes = viewNode.getChildNodes();

        Coordinates coordinates = new Coordinates(0d, 0d, 0d);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "gx:timespan": {
                    NodeList subNodes = node.getChildNodes();
                    com.usda.fmsc.utilities.kml.View.TimeSpan ts = new View.TimeSpan(null, null);

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);
                        if (node.getNodeName().equalsIgnoreCase("begin")) {
                            ts.StartTime = parseTime(node.getTextContent());
                        } else if (node.getNodeName().equalsIgnoreCase("end")) {
                            ts.EndTime = parseTime(node.getTextContent());
                        }
                    }

                    if (ts.StartTime != null || ts.EndTime != null) {
                        view.setTimeSpan(ts);
                    }
                    break;
                }
                case "gx:timestamp": {
                    NodeList subNodes = node.getChildNodes();

                    for (int j = 0; j < subNodes.getLength(); j++) {
                        node = subNodes.item(j);
                        if (node.getNodeName().equalsIgnoreCase("when")) {
                            view.setTimeStamp(parseTime(node.getTextContent()));
                        }
                    }
                    break;
                }
                case "longitude": coordinates.setLongitude(ParseEx.parseDouble(node.getTextContent())); break;
                case "latitude": coordinates.setLatitude(ParseEx.parseDouble(node.getTextContent())); break;
                case "altitude": coordinates.setAltitude(ParseEx.parseDouble(node.getTextContent())); break;
                case "range": view.setRange(ParseEx.parseDouble(node.getTextContent())); break;
                case "tilt": view.setTilt(ParseEx.parseDouble(node.getTextContent())); break;
                case "heading": view.setHeading(ParseEx.parseDouble(node.getTextContent())); break;
                case "altitudemode":
                case "gx:altitudemode": view.setAltMode(Types.Parse.AltitudeMode(node.getTextContent())); break;
            }
        }

        if (coordinates.getLatitude() != 0 || coordinates.getLongitude() != 0 || coordinates.getAltitude() != 0) {
            view.setCoordinates(coordinates);
        }

        return view;
    }

    //private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-ddTHH:mm:ssZ");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private static DateTime parseTime(String text) {
        return DateTime.parse(text.replace("T", " ").replace("Z", ""), dateTimeFormatter);
    }
}
