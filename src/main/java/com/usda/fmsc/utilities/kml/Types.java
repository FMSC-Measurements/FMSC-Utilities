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
            switch (mode.toLowerCase()) {
                case "clamptoseafloor": return AltitudeMode.clampToSeaFloor;
                case "relativetoground": return AltitudeMode.relativeToGround;
                case "relativetoseafloor": return AltitudeMode.relativeToSeaFloor;
                case "absolute": return AltitudeMode.absolute;
                case "clamptoground":
                default: return AltitudeMode.clampToGround;
            }
        }

        public static ColorMode ColorMode(String mode) {
            switch (mode.toLowerCase()) {
                case "random": return ColorMode.random;
                default:
                case "normal": return ColorMode.normal;
            }
        }

        public static XYUnitType XYUnitType(String mode) {
            switch (mode.toLowerCase()) {
                case "fraction": return XYUnitType.fraction;
                case "insetPixels": return XYUnitType.insetPixels;
                default:
                case "pixels": return XYUnitType.pixels;
            }
        }

        public static DisplayMode DisplayMode(String mode) {
            switch (mode.toLowerCase()) {
                case "hide": return DisplayMode.hide;
                default:
                case "default": return DisplayMode.Default;
            }
        }


        public static ListItemType ListItemType(String mode) {
            switch (mode.toLowerCase()) {
                case "checkHideChildern": return ListItemType.checkHideChildern;
                case "checkOffOnly": return ListItemType.checkOffOnly;
                case "radioFolder": return ListItemType.radioFolder;
                default:
                case "check": return ListItemType.check;
            }
        }


        public static State State(String mode) {
            switch (mode.toLowerCase()) {
                case "closed": return State.closed;
                case "error": return State.error;
                case "fetching0": return State.fetching0;
                case "fetching1": return State.fetching1;
                case "fetching2": return State.fetching2;
                default:
                case "open": return State.open;
            }
        }
    }
}
