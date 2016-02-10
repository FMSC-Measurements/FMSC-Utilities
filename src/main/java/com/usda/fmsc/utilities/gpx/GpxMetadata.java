package com.usda.fmsc.utilities.gpx;

import org.joda.time.DateTime;

import java.util.List;

public class GpxMetadata {
    private String Name;
    private String Description;
    private String Author;
    private String Copyright;
    private String Link;
    public DateTime Time;
    public List<String> Keywords;
    private String Extensions;

    public GpxMetadata() {
        this(null, null);
    }

    public GpxMetadata(String name) {
        this(name, null);
    }

    public GpxMetadata(String name, String desc) {
        Name = name;
        Description = desc;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getCopyright() {
        return Copyright;
    }

    public void setCopyright(String copyright) {
        Copyright = copyright;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public DateTime getTime() {
        return Time;
    }

    public void setTime(DateTime time) {
        Time = time;
    }

    public List<String> getKeywords() {
        return Keywords;
    }

    public void setKeywords(List<String> keywords) {
        Keywords = keywords;
    }

    public String getExtensions() {
        return Extensions;
    }

    public void setExtensions(String extensions) {
        Extensions = extensions;
    }
}