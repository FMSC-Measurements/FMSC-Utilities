package com.usda.fmsc.utilities.gpx;

import com.usda.fmsc.utilities.ParseEx;
import com.usda.fmsc.utilities.StringEx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GpxDocument {
    private List<GpxPoint> _Waypoints;
    private List<GpxRoute> _Routes;
    private List<GpxTrack> _Tracks;

    private String Creator;

    private GpxMetadata MetaData;
    private String Extensions;


    public GpxDocument() {
        _Waypoints = new ArrayList<>();
        _Routes = new ArrayList<>();
        _Tracks = new ArrayList<>();
    }


    public void addPoint(GpxPoint point) {
        if (point != null)
            _Waypoints.add(point);
    }

    public GpxPoint getPointById(String id) {
        for (GpxPoint point : _Waypoints) {
            if (point.getID().equals(id)) {
                return point;
            }
        }

        return null;
    }

    public void deletePointById(String id) {
        for (int i = 0; i < _Waypoints.size(); i++) {
            if (_Waypoints.get(i).getID().equals(id)) {
                _Waypoints.remove(i);
            }
        }
    }

    public void deletePointAtPos(int pos) {
        if (pos < _Waypoints.size() && pos > -1) {
            _Waypoints.remove(pos);
        }
    }

    public void addRoute(GpxRoute route) {
        if (route != null)
            _Routes.add(route);
    }

    public GpxRoute getRouteByName(String name) {
        for (GpxRoute route : _Routes) {
            if (route.getName().equals(name)) {
                return route;
            }
        }

        return null;
    }

    public void deletePointByName(String name) {
        for (int i = 0; i < _Routes.size(); i++) {
            if (_Routes.get(i).getName().equals(name)) {
                _Routes.remove(i);
            }
        }
    }

    public void deleteRouteAtPos(int pos) {
        if (pos < _Routes.size() && pos > -1) {
            _Routes.remove(pos);
        }
    }

    public void addTrack(GpxTrack Track) {
        if (Track != null)
            _Tracks.add(Track);
    }

    public GpxTrack getTrackByName(String name) {
        for (GpxTrack Track : _Tracks) {
            if (Track.getName().equals(name)) {
                return Track;
            }
        }

        return null;
    }

    public void deleteTrackByName(String name) {
        for (int i = 0; i < _Tracks.size(); i++) {
            if (_Tracks.get(i).getName().equals(name)) {
                _Tracks.remove(i);
            }
        }
    }

    public void deleteTrackAtPos(int pos) {
        if (pos < _Tracks.size() && pos > -1) {
            _Tracks.remove(pos);
        }
    }


    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public GpxMetadata getMetaData() {
        return MetaData;
    }

    public void setMetaData(GpxMetadata metaData) {
        MetaData = metaData;
    }

    public String getExtensions() {
        return Extensions;
    }

    public void setExtensions(String extensions) {
        Extensions = extensions;
    }

    public List<GpxTrack> getTracks() {
        return _Tracks;
    }

    public void setTracks(List<GpxTrack> tracks) {
        _Tracks = tracks;
    }

    public List<GpxPoint> getWaypoints() {
        return _Waypoints;
    }

    public void setWaypoints(List<GpxPoint> waypoints) {
        _Waypoints = waypoints;
    }

    public List<GpxRoute> getRoutes() {
        return _Routes;
    }

    public void setRoutes(List<GpxRoute> routes) {
        _Routes = routes;
    }


    public static GpxDocument parseFile(File file) throws ParserConfigurationException, SAXException, IOException{

        if (!file.exists() || file.isDirectory()) {
            throw new RuntimeException("Invalid File");
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();

        GpxDocument gpxDoc = new GpxDocument();

        NodeList routes = doc.getElementsByTagName("rte");
        NodeList tracks = doc.getElementsByTagName("trk");

        int routeCount = 1, trackCount = 1;

        for (int i = 0; i < routes.getLength(); i++) {
            Node node = routes.item(i);
            GpxRoute route = new GpxRoute();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element)node;

                String tmp = elem.getElementsByTagName("name").item(0).getTextContent();
                if (!StringEx.isEmpty(tmp)) {
                    route.setName(tmp);
                } else {
                    route.setName(String.format("Route %d", routeCount));
                }

                tmp = elem.getElementsByTagName("desc").item(0).getTextContent();
                if (!StringEx.isEmpty(tmp)) {
                    route.setDescription(tmp);
                }

                route.setPoints(getPointsFromNodeList(elem.getElementsByTagName("rtept")));

                gpxDoc.addRoute(route);
                routeCount++;
            }
        }

        for (int i = 0; i < tracks.getLength(); i++) {
            Node node = tracks.item(i);
            GpxTrack track = new GpxTrack();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element)node;

                String tmp = elem.getElementsByTagName("name").item(0).getTextContent();
                if (!StringEx.isEmpty(tmp)) {
                    track.setName(tmp);
                } else {
                    track.setName(String.format("Track %d", trackCount));
                }

                tmp = elem.getElementsByTagName("desc").item(0).getTextContent();
                if (!StringEx.isEmpty(tmp)) {
                    track.setDescription(tmp);
                }

                NodeList segs = elem.getElementsByTagName("trkseg");
                GpxTrackSeg seg;

                for (int j = 0; j < segs.getLength(); j++) {
                    node = segs.item(j);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        seg = new GpxTrackSeg();
                        seg.setPoints(getPointsFromNodeList(((Element)node).getElementsByTagName("trkpt")));
                        track.addSegment(seg);
                    }
                }

                gpxDoc.addTrack(track);
                trackCount++;
            }
        }


        return gpxDoc;
    }

    private static ArrayList<GpxPoint> getPointsFromNodeList(NodeList list) {
        ArrayList<GpxPoint> points = new ArrayList<>();

        Element elem;
        Node node;
        String tmp;
        GpxPoint point;

        for (int j = 0; j < list.getLength(); j++) {
            node = list.item(j);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                elem = (Element)node;

                Double lat, lon, elev;
                String name, cmt, desc;

                tmp = elem.getAttribute("lat");
                if (StringEx.isEmpty(tmp) || (lat = ParseEx.parseDouble(tmp)) == null) {
                    throw new RuntimeException("Invalid Latitude");
                }

                tmp = elem.getAttribute("lon");
                if (StringEx.isEmpty(tmp) || (lon = ParseEx.parseDouble(tmp)) == null) {
                    throw new RuntimeException("Invalid Longitude");
                }

                elev = ParseEx.parseDouble(elem.getElementsByTagName("ele").item(0).getTextContent());
                name = elem.getElementsByTagName("name").item(0).getTextContent();
                cmt = elem.getElementsByTagName("cmt").item(0).getTextContent();
                desc = elem.getElementsByTagName("desc").item(0).getTextContent();

                if (elev != null) {
                    point = new GpxPoint(lat, lon, elev);
                } else {
                    point = new GpxPoint(lat, lon);
                }

                if (!StringEx.isEmpty(name)) {
                    point.setName(name);
                }

                if (!StringEx.isEmpty(cmt)) {
                    point.setComment(cmt);
                }

                if (!StringEx.isEmpty(desc)) {
                    point.setDescription(desc);
                }

                points.add(point);
            }
        }

        return points;
    }
}