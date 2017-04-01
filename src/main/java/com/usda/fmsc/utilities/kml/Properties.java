package com.usda.fmsc.utilities.kml;

public abstract class Properties {
    private String Author;
    private String Link;
    private String Address;
    private String Snippit;
    private Integer SnippitMaxLines;
    private String Region;
    private com.usda.fmsc.utilities.kml.ExtendedData ExtendedData;

    public Properties() {
    }

    public Properties(Properties p) {
        if (p != null) {
            Author = p.Author;
            Link = p.Link;
            Address = p.Address;
            Snippit = p.Snippit;
            SnippitMaxLines = p.SnippitMaxLines;
            Region = p.Region;
            ExtendedData = p.ExtendedData;
        }
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSnippit() {
        return Snippit;
    }

    public void setSnippit(String snippit) {
        Snippit = snippit;
    }

    public Integer getSnippitMaxLines() {
        return SnippitMaxLines;
    }

    public void setSnippitMaxLines(Integer snippitMaxLines) {
        SnippitMaxLines = snippitMaxLines;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public com.usda.fmsc.utilities.kml.ExtendedData getExtendedData() {
        return ExtendedData;
    }

    public void setExtendedData(com.usda.fmsc.utilities.kml.ExtendedData extendedData) {
        ExtendedData = extendedData;
    }
}
