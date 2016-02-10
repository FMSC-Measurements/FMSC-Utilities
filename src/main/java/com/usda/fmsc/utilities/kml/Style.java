package com.usda.fmsc.utilities.kml;

import com.usda.fmsc.utilities.kml.Types.ColorMode;
import com.usda.fmsc.utilities.kml.Types.DisplayMode;
import com.usda.fmsc.utilities.kml.Types.ListItemType;
import com.usda.fmsc.utilities.kml.Types.State;

public class Style {

    private String   ID;
    private String   IconID;
    private String   IconUrl;
    private Color    IconColor;
    private Double   IconScale;
    private ColorMode IconColorMode;
    private Double   IconHeading;
    private HotSpot  IconHotSpot;

    private String   LabelID;
    private Color    LabelColor;
    private Double   LabelScale;
    private ColorMode LabelColorMode;

    private String   LineID;
    private Color    LineColor;
    private Double   LineWidth;
    private ColorMode LineColorMode;
    private Color    LineOuterColor;
    private Double   LineOuterWidth;
    private Double   LinePhysicalWidth;
    private Boolean  LineLabelVisibility;

    private String   PolygonID;
    private Color    PolygonColor;
    private ColorMode PolygonColorMode;
    private Boolean  PolygonFill;
    private Boolean  PolygonOutline;

    private String   BalloonID;
    private Color    BalloonBgColor;
    private Color    BalloonTextColor;
    private String   BalloonText;
    private DisplayMode BalloonDisplayMode;

    private String   ListID;
    private ListItemType ListListItemType;
    private Color    ListBgColor;
    private State    ListItemState;
    private String   ListItemIconUrl;


    public Style() {
        this(null, null);
    }

    public Style(String id) {
        this(null, id);
    }

    public Style(Style s) {
        this(s, null);
    }

    public Style(Style s, String id) {
        if (id != null)
            ID = id;
        else
            ID = java.util.UUID.randomUUID().toString();

        if (s != null) {
            PolygonID = s.PolygonID;
            PolygonColorMode = s.PolygonColorMode;
            PolygonColor = s.PolygonColor;
            PolygonFill = s.PolygonFill;
            PolygonOutline = s.PolygonOutline;

            IconID = s.IconID;
            IconUrl = s.IconUrl;
            IconColor = s.IconColor;
            IconScale = s.IconScale;
            IconHeading = s.IconHeading;
            IconHotSpot = s.IconHotSpot;

            LabelID = s.LabelID;
            LabelColor = s.LabelColor;
            LabelScale = s.LabelScale;
            LabelColorMode = s.LabelColorMode;

            LineID = s.LineID;
            LineColor = s.LineColor;
            LineWidth = s.LineWidth;
            LineColorMode = s.LineColorMode;
            LineOuterColor = s.LineOuterColor;
            LineOuterWidth = s.LineOuterWidth;
            LinePhysicalWidth = s.LinePhysicalWidth;
            LineLabelVisibility = s.LineLabelVisibility;

            BalloonID = s.BalloonID;
            BalloonBgColor = s.BalloonBgColor;
            BalloonTextColor = s.BalloonTextColor;
            BalloonText = s.BalloonText;
            BalloonDisplayMode = s.BalloonDisplayMode;

            ListID = s.ListID;
            ListListItemType = s.ListListItemType;
            ListBgColor = s.ListBgColor;
            ListItemState = s.ListItemState;
            ListItemIconUrl = s.ListItemIconUrl;
        }
    }


    public String getID() {
        return ID;
    }
    
    public String getStyleUrl() {
        return '#' + ID;
    }

    public String getIconID() {
        return IconID;
    }

    public void setIconID(String iconID) {
        IconID = iconID;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public void setIconUrl(String iconUrl) {
        IconUrl = iconUrl;
    }

    public Color getIconColor() {
        return IconColor;
    }

    public void setIconColor(Color iconColor) {
        IconColor = iconColor;
    }

    public Double getIconScale() {
        return IconScale;
    }

    public void setIconScale(Double iconScale) {
        IconScale = iconScale;
    }

    public ColorMode getIconColorMode() {
        return IconColorMode;
    }

    public void setIconColorMode(ColorMode iconColorMode) {
        IconColorMode = iconColorMode;
    }

    public Double getIconHeading() {
        return IconHeading;
    }

    public void setIconHeading(Double iconHeading) {
        IconHeading = iconHeading;
    }

    public HotSpot getIconHotSpot() {
        return IconHotSpot;
    }

    public void setIconHotSpot(HotSpot iconHotSpot) {
        IconHotSpot = iconHotSpot;
    }

    public String getLabelID() {
        return LabelID;
    }

    public void setLabelID(String labelID) {
        LabelID = labelID;
    }

    public Color getLabelColor() {
        return LabelColor;
    }

    public void setLabelColor(Color labelColor) {
        LabelColor = labelColor;
    }

    public Double getLabelScale() {
        return LabelScale;
    }

    public void setLabelScale(Double labelScale) {
        LabelScale = labelScale;
    }

    public ColorMode getLabelColorMode() {
        return LabelColorMode;
    }

    public void setLabelColorMode(ColorMode labelColorMode) {
        LabelColorMode = labelColorMode;
    }

    public String getLineID() {
        return LineID;
    }

    public void setLineID(String lineID) {
        LineID = lineID;
    }

    public Color getLineColor() {
        return LineColor;
    }

    public void setLineColor(Color lineColor) {
        LineColor = lineColor;
    }

    public Double getLineWidth() {
        return LineWidth;
    }

    public void setLineWidth(Double lineWidth) {
        LineWidth = lineWidth;
    }

    public ColorMode getLineColorMode() {
        return LineColorMode;
    }

    public void setLineColorMode(ColorMode lineColorMode) {
        LineColorMode = lineColorMode;
    }

    public Color getLineOuterColor() {
        return LineOuterColor;
    }

    public void setLineOuterColor(Color lineOuterColor) {
        LineOuterColor = lineOuterColor;
    }

    public Double getLineOuterWidth() {
        return LineOuterWidth;
    }

    public void setLineOuterWidth(Double lineOuterWidth) {
        LineOuterWidth = lineOuterWidth;
    }

    public Double getLinePhysicalWidth() {
        return LinePhysicalWidth;
    }

    public void setLinePhysicalWidth(Double linePhysicalWidth) {
        LinePhysicalWidth = linePhysicalWidth;
    }

    public Boolean getLineLabelVisibility() {
        return LineLabelVisibility;
    }

    public void setLineLabelVisibility(Boolean lineLabelVisibility) {
        LineLabelVisibility = lineLabelVisibility;
    }

    public String getPolygonID() {
        return PolygonID;
    }

    public void setPolygonID(String polygonID) {
        PolygonID = polygonID;
    }

    public Color getPolygonColor() {
        return PolygonColor;
    }

    public void setPolygonColor(Color polygonColor) {
        PolygonColor = polygonColor;
    }

    public ColorMode getPolygonColorMode() {
        return PolygonColorMode;
    }

    public void setPolygonColorMode(ColorMode polygonColorMode) {
        PolygonColorMode = polygonColorMode;
    }

    public Boolean getPolygonFill() {
        return PolygonFill;
    }

    public void setPolygonFill(Boolean polygonFill) {
        PolygonFill = polygonFill;
    }

    public Boolean getPolygonOutline() {
        return PolygonOutline;
    }

    public void setPolygonOutline(Boolean polygonOutline) {
        PolygonOutline = polygonOutline;
    }

    public String getBalloonID() {
        return BalloonID;
    }

    public void setBalloonID(String balloonID) {
        BalloonID = balloonID;
    }

    public Color getBalloonBgColor() {
        return BalloonBgColor;
    }

    public void setBalloonBgColor(Color balloonBgColor) {
        BalloonBgColor = balloonBgColor;
    }

    public Color getBalloonTextColor() {
        return BalloonTextColor;
    }

    public void setBalloonTextColor(Color balloonTextColor) {
        BalloonTextColor = balloonTextColor;
    }

    public String getBalloonText() {
        return BalloonText;
    }

    public void setBalloonText(String balloonText) {
        BalloonText = balloonText;
    }

    public DisplayMode getBalloonDisplayMode() {
        return BalloonDisplayMode;
    }

    public void setBalloonDisplayMode(DisplayMode balloonDisplayMode) {
        BalloonDisplayMode = balloonDisplayMode;
    }

    public String getListID() {
        return ListID;
    }

    public void setListID(String listID) {
        ListID = listID;
    }

    public ListItemType getListListItemType() {
        return ListListItemType;
    }

    public void setListListItemType(ListItemType listListItemType) {
        ListListItemType = listListItemType;
    }

    public Color getListBgColor() {
        return ListBgColor;
    }

    public void setListBgColor(Color listBgColor) {
        ListBgColor = listBgColor;
    }

    public State getListItemState() {
        return ListItemState;
    }

    public void setListItemState(State listItemState) {
        ListItemState = listItemState;
    }

    public String getListItemIconUrl() {
        return ListItemIconUrl;
    }

    public void setListItemIconUrl(String listItemIconUrl) {
        ListItemIconUrl = listItemIconUrl;
    }

    public void setColorsILP(Color c) {
        IconColor = c;
        LineColor = c;
        PolygonColor = new Color(c);
        PolygonColor.setAlpha(50);

        IconColorMode = ColorMode.normal;
        LineColorMode = ColorMode.normal;
        PolygonColorMode = ColorMode.normal;
    }
}
