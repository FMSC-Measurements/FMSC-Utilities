package com.usda.fmsc.utilities.kml;

import java.util.ArrayList;

public class Placemark extends Properties {
    private ArrayList<Polygon> Polygons;
    private ArrayList<Point> Points;
    private String CN;
    private String Name;
    private String Desctription;
    private String StyleUrl;
    private View View;
    private Boolean Visibility;
    private Boolean Open;


    public Placemark(String name) {
        setup(name, null, null);
    }

    public Placemark(String name, String desc) {
        setup(name, desc, null);
    }

    public Placemark(String name, String desc, View v) {
        setup(name, desc, v);
    }

    public Placemark(Placemark pm) {
        super(pm);
        setup(pm.Name, pm.Desctription, new View(pm.View));

        StyleUrl = pm.StyleUrl;
        Visibility = pm.Visibility;
        Open = pm.Open;

        Polygons = pm.getPolygons();
        Points = pm.getPoints();
    }

    private void setup(String name, String desc, View v) {
        Name = name;
        View = v;

        if (desc != null)
            Desctription = desc;
        else
            Desctription = "";

        CN = java.util.UUID.randomUUID().toString();

        Polygons = new ArrayList<>();
        Points = new ArrayList<>();
    }


    public String getCN() {
        return CN;
    }

    public ArrayList<Polygon> getPolygons() {
        return Polygons;
    }

    public ArrayList<Point> getPoints() {
        return Points;
    }
    

    public void addPoint(Point p) {
        if (p != null && p.getCN() != null)
            Points.add(p);
    }

    public void addPolygon(Polygon p) {
        if (p != null && p.getCN() != null)
            Polygons.add(p);
    }

    public void removePoint(String cn) {
        for (int i = 0; i < Points.size(); i++) {
            if (Points.get(i).getCN().equals(cn)) {
                Points.remove(i);
                return;
            }
        }
    }

    public void removePolygon(String cn) {
        for (int i = 0; i < Polygons.size(); i++) {
            if (Polygons.get(i).getCN().equals(cn)) {
                Polygons.remove(i);
                return;
            }
        }
    }

    public Point getPoint(String cn) {
        for (Point point : Points)
        {
            if (point.getCN().equals(cn))
                return point;
        }

        return null;
    }

    public Point getPointByName(String name) {
        for (Point point : Points) {
            if (point.getName().equals(name))
                return point;
        }

        return null;
    }

    public Polygon getPolygon(String cn) {
        for (Polygon poly : Polygons) {
            if (poly.getCN().equals(cn))
                return poly;
        }

        return null;
    }

    public Polygon getPolygonByName(String name) {
        for (Polygon poly : Polygons) {
            if (poly.getName().equals(name))
                return poly;
        }

        return null;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesctription() {
        return Desctription;
    }

    public void setDescription(String desctription) {
        Desctription = desctription;
    }

    public String getStyleUrl() {
        return StyleUrl;
    }

    public void setStyleUrl(String styleUrl) {
        StyleUrl = styleUrl;
    }

    public com.usda.fmsc.utilities.kml.View getView() {
        return View;
    }

    public void setView(com.usda.fmsc.utilities.kml.View view) {
        View = view;
    }

    public Boolean getVisibility() {
        return Visibility;
    }

    public void setVisibility(Boolean visibility) {
        Visibility = visibility;
    }

    public Boolean isOpen() {
        return Open;
    }

    public void setOpen(Boolean open) {
        Open = open;
    }
}
