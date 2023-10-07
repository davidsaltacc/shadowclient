package net.shadowclient.main.ui.clickgui;

public enum Colors {

    // CATEGORY_FRAME(-14803406),
    // TEXT_NORMAL(15791615),
    // TEXT_ENABLED(-15453576),
    // MODULE_BUTTON_NORMAL(-936891086),
    // MODULE_BUTTON_HOVERED(-935575226),
    // SETTING_COMPONENT_NORMAL(-936888496),
    // SETTING_COMPONENT_HOVERED(-935572636),
    // SLIDER(-13477246);

    CATEGORY_FRAME(-16777216),
    TEXT_NORMAL(-723719),
    TEXT_ENABLED(-9990235),
    TEXT_DISABLED(-433443794),
    MODULE_BUTTON_NORMAL(-1272962525),
    MODULE_BUTTON_HOVERED(-1271974858),
    SETTING_COMPONENT_NORMAL(-601873885),
    SETTING_COMPONENT_HOVERED(-600886218),
    SLIDER(-14402211);

    public void setColor(int color) {
        this.color = color;
    }

    public int color;

    Colors(int color) {
        this.color = color;
    }
}
