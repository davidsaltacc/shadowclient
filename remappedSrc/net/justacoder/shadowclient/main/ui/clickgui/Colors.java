package net.justacoder.shadowclient.main.ui.clickgui;

public enum Colors {

    CATEGORY_FRAME(-16777216),
    TEXT_NORMAL(-1),
    TEXT_ENABLED(-8942167),
    TEXT_DISABLED(-433443794),
    MODULE_BUTTON_NORMAL(-1272962525),
    MODULE_BUTTON_HOVERED(-1271974858),
    SETTING_COMPONENT_NORMAL(-1273620455),
    SETTING_COMPONENT_HOVERED(-1272962525),
    SLIDER(-8942167),
    NOTIFICATION_NORMAL(-1272962525),
    NOTIFICATION_HOVERED(-1271974858),
    HORIZONTAL_LINE(-2039331),
    HUD_ELEMENT_BACKGROUND(853598685),
    HUD_ELEMENT_TEXT(-1);

    public void setColor(int color) {
        this.color = color;
    }

    public int color;

    Colors(int color) {
        this.color = color;
    }
}
