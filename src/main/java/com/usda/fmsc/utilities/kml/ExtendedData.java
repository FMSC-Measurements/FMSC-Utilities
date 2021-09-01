package com.usda.fmsc.utilities.kml;

import java.util.ArrayList;

public class ExtendedData {
    public static class Data {
        private String ID;
        private String Name;
        private String Value;

        public Data() {
            this(null, null, null);
        }

        public Data(String name, String value) {
            this(name, value, null);
        }

        public Data(String name, String value, String id) {
            Name = name;
            Value = value;
            ID = id;
        }


        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

    }

    private final ArrayList<Data> _DataItems;

    public ExtendedData() {
        _DataItems = new ArrayList<>();
    }

    public ArrayList<Data> getDataItems() {
        return _DataItems;
    }


    public void add(Data data) {
        if (data != null && data.getName() != null && data.getValue() != null)
            _DataItems.add(data);
    }

    public void remove(String id) {
        for (int i = 0; i < _DataItems.size(); i++) {
            if (_DataItems.get(i).getID().equals(id)) {
                _DataItems.remove(i);
                return;
            }
        }
    }

    public Data get(String id) {
        for (Data d : _DataItems) {
            if (d.getID().equals(id))
                return d;
        }

        return null;
    }
}