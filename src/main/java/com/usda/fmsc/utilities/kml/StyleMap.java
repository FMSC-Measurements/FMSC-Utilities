package com.usda.fmsc.utilities.kml;

public class StyleMap {
    private final String ID;

    private String NormalStyleUrl;
    private String HightLightedStyleUrl;


    public StyleMap() {
        this(null, null, null);
    }

    public StyleMap(String id) {
        this(id, null, null);
    }

    public StyleMap(String id, String norm, String high) {
        if (id != null)
            ID = id;
        else
            ID = java.util.UUID.randomUUID().toString();

        NormalStyleUrl = norm;
        HightLightedStyleUrl = high;
    }


    public String getID() {
        return ID;
    }

    public String getStyleUrl() {
        return String.format("#%s", ID);
    }

    public String getNormalStyleUrl() {
        return NormalStyleUrl;
    }

    public void setNormalStyleUrl(String normalStyleUrl) {
        NormalStyleUrl = normalStyleUrl;
    }

    public String getHightLightedStyleUrl() {
        return HightLightedStyleUrl;
    }

    public void setHightLightedStyleUrl(String hightLightedStyleUrl) {
        HightLightedStyleUrl = hightLightedStyleUrl;
    }
}
