package com.usda.fmsc.utilities.kml;

import java.util.ArrayList;

public class Folder {
    private ArrayList<Folder> SubFolders;
    private ArrayList<Placemark> Placemarks;
    
    private String CN;
    private String Name;
    private String Desctription;
    private String StyleUrl;
    private Properties Properties;
    private Boolean Open;
    private Boolean Visibility;


    public Folder(String name) {
        this(name, null);
    }

    public Folder(String name, String desc) {
        Name = name;
        CN = java.util.UUID.randomUUID().toString();

        SubFolders = new ArrayList<Folder>();
        Placemarks = new ArrayList<Placemark>();

        Desctription = desc;
    }


    public void addFolder(Folder f) {
        if(f != null && f.getCN() != null)
            SubFolders.add(f);
    }

    public void removeFolder(String cn) {
        for (int i = 0; i < SubFolders.size(); i++) {
            if (SubFolders.get(i).getCN().equals(cn)) {
                SubFolders.remove(i);
                return;
            }
        }
    }

    public Folder getFolder(String cn) {
        for (Folder folder : SubFolders) {
            if (folder.getCN().equals(cn))
                return folder;
        }

        return null;
    }

    public Folder getFolderByName(String name) {
        for (Folder folder : SubFolders) {
            if (folder.getName().equals(name))
                return folder;
        }

        return null;
    }


    public void addPlacemark(Placemark p) {
        if (p != null && p.getCN() != null)
            Placemarks.add(p);
    }

    public void removePlacemark(String cn) {
        for (int i = 0; i < Placemarks.size(); i++) {
            if (Placemarks.get(i).getCN().equals(cn)) {
                Placemarks.remove(i);
                return;
            }
        }
    }

    public Placemark getPlacemark(String cn) {
        for (Placemark pm : Placemarks) {
            if (pm.getCN().equals(cn))
                return pm;
        }

        return null;
    }

    public Placemark getPlacemarkByName(String name) {
        for (Placemark pm : Placemarks) {
            if (pm.getName().equals(name))
                return pm;
        }

        return null;
    }


    public String getCN() {
        return CN;
    }

    public ArrayList<Folder> getSubFolders() {
        return SubFolders;
    }

    public ArrayList<Placemark> getPlacemarks() {
        return Placemarks;
    }

    public Boolean getVisibility() {
        return Visibility;
    }

    public void setVisibility(Boolean visibility) {
        Visibility = visibility;
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

    public void setDesctription(String desctription) {
        Desctription = desctription;
    }

    public String getStyleUrl() {
        return StyleUrl;
    }

    public void setStyleUrl(String styleUrl) {
        StyleUrl = styleUrl;
    }

    public com.usda.fmsc.utilities.kml.Properties getProperties() {
        return Properties;
    }

    public void setProperties(com.usda.fmsc.utilities.kml.Properties properties) {
        Properties = properties;
    }

    public Boolean getOpen() {
        return Open;
    }

    public void setOpen(Boolean open) {
        Open = open;
    }
}
