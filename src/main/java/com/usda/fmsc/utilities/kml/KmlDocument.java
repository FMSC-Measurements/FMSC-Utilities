package com.usda.fmsc.utilities.kml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class KmlDocument extends Folder {
    private ArrayList<Style> Styles;
    private ArrayList<StyleMap> StyleMaps;


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


    public static KmlDocument load(String filename) throws IOException, ParserConfigurationException, SAXException {

        File fXmlFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);



        return null;
    }


    private static Folder parseFolder(Node node) {
        return null;
    }
}
