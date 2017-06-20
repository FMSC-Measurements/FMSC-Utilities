package com.usda.fmsc.utilities.kml;

import com.usda.fmsc.utilities.FileUtils;
import com.usda.fmsc.utilities.ParseEx;
import com.usda.fmsc.utilities.StringEx;

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

        return null;
    }

    private static void parseFolderData(Folder folder, NodeList folderNodes) {
        for (int i = 0; i < folderNodes.getLength(); i++) {
            Node node = folderNodes.item(i);

            switch (node.getNodeName().toLowerCase()) {
                case "folder": folder.addFolder(parseFolder(node)); break;
                case "palcemark": folder.addPlacemark(parsePlacemark(node)); break;
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

    private static Placemark parsePlacemark(Node node) {
        return null;
    }

    private static Polygon parsePolygon(Node node) {
        return null;
    }

    private static Point parsePoint(Node node) {
        return null;
    }

    private static Style parseStyle(Node node) {
        return null;
    }

    private static StyleMap parseStyleMap(Node node) {
        return null;
    }

    private static View parseView(Node node) {
        return null;
    }
}
