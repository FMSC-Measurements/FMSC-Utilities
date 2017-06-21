package com.usda.fmsc.utilities.kml;

public class Types {
    public enum ColorMode {
        normal,
        random
    }

    public enum DisplayMode {
        Default,
        hide;

        @Override
        public String toString() {
            switch(this) {
                case Default: return "default";
                case hide: return "hide";
                default: throw new IllegalArgumentException();
            }
        }
    }

    public enum ListItemType {
        check,
        checkOffOnly,
        checkHideChildern,
        radioFolder
    }

    public enum State {
        open,
        closed,
        error,
        fetching0,
        fetching1,
        fetching2
    }

    public enum XYUnitType {
        fraction,
        pixels,
        insetPixels
    }

    public enum AltitudeMode {
        clampToGround,
        clampToSeaFloor,
        relativeToGround,
        relativeToSeaFloor,
        absolute
    }

    public static class Parse {
        public static AltitudeMode AltitudeMode(String mode) {
            switch (mode) {
                case "clampToSeaFloor": return AltitudeMode.clampToSeaFloor;
                case "relativeToGround": return AltitudeMode.relativeToGround;
                case "relativeToSeaFloor": return AltitudeMode.relativeToSeaFloor;
                case "absolute": return AltitudeMode.absolute;
                case "clampToGround":
                default: return AltitudeMode.clampToGround;
            }
        }
    }
}
