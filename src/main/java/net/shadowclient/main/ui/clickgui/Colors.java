package net.shadowclient.main.ui.clickgui;

public enum Colors {

    CATEGORY_FRAME(-16777216),
    TEXT_NORMAL(-723719),
    TEXT_ENABLED(-9990235),
    TEXT_DISABLED(-433443794),
    MODULE_BUTTON_NORMAL(-1272962525),
    MODULE_BUTTON_HOVERED(-1271974858),
    SETTING_COMPONENT_NORMAL(-601873885),
    SETTING_COMPONENT_HOVERED(-600886218),
    SLIDER(-14402211),
    NOTIFICATION_NORMAL(-1272962525),
    NOTIFICATION_HOVERED(-1271974858),
    HORIZONTAL_LINE(-923404801),
    HUD_ELEMENT_BACKGROUND(855638015),
    HUD_ELEMENT_TEXT(-1);

    public void setColor(int color) {
        this.color = color;
    }

    public int color;

    Colors(int color) {
        this.color = color;
    }
}
