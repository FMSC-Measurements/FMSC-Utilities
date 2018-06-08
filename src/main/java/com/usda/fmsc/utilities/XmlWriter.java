package com.usda.fmsc.utilities;

import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class XmlWriter {
    private XmlSerializer serializer;
    private Stack<String> elements;
    private FileWriter fileWriter;

    public XmlWriter(String fileName) throws IOException {
        serializer = Xml.newSerializer();

        fileWriter = new FileWriter(fileName);
        serializer.setOutput(fileWriter);

        elements = new Stack<>();
    }


    protected void startDocument(String encoding, Boolean standalone) throws IOException {
        serializer.startDocument(encoding, standalone);
    }

    protected void endDocument() throws IOException {
        serializer.endDocument();
    }

    protected void close() throws IOException {
        fileWriter.close();
    }

    protected void setFeature(String name, boolean state) {
        serializer.setFeature(name, state);
    }

    protected boolean getFeature(String name) {
        return serializer.getFeature(name);
    }

    protected void setProperty(String name, Object value) {
        serializer.setProperty(name, value);
    }

    protected Object getProperty(String name) {
        return serializer.getProperty(name);
    }

    protected void cdsect (String text) throws IOException {
        serializer.cdsect(text);
    }

    protected void startElement(String element) throws IOException {
        serializer.startTag(null, element);
        elements.push(element);
    }

    public void attribute(String name, String value) throws IOException {
        serializer.attribute(null, name, value);
    }

    protected void endElement() throws IOException {
        serializer.endTag(null, elements.pop());
    }

    protected void value(String value) throws IOException {
        serializer.text(value);
    }

    protected void writeElement(String element, String value) throws IOException {
        serializer.startTag(null, element);
        serializer.text(value);
        serializer.endTag(null, element);
    }

    public void comment(String comment) throws IOException {
        serializer.comment(comment);
    }
}
