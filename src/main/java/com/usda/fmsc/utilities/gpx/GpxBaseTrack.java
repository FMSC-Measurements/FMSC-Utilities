package com.usda.fmsc.utilities.gpx;

public class GpxBaseTrack {
    private String Name;
    private String Comment;
    private String Description;
    private String Source;
    private String Link;
    private Integer Number;
    private String Type;
    private String Extensions;

    public GpxBaseTrack() {
        this(null, null);
    }

    public GpxBaseTrack(String name) {
        this(name, null);
    }

    public GpxBaseTrack(String name, String desc) {
        init(name, desc);
    }

    private void init(String name, String desc) {
        Name = name;
        Description = desc;
        Comment = null;
        Source = null;
        Link = null;
        Number = null;
        Type = null;
        Extensions = null;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getExtensions() {
        return Extensions;
    }

    public void setExtensions(String extensions) {
        Extensions = extensions;
    }
}