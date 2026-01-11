package net.justacoder.shadowclient.main.ui;

public enum Colors {

    FRAME_COLOR(-16777216),
    TEXT_NORMAL(-1),
    TEXT_ENABLED(-8942167),
    TEXT_DISABLED(-13355464),
    FRAME_CHILD_BACKGROUND_NORMAL(-1272962525),
    FRAME_CHILD_BACKGROUND_HOVERED(-1271974858);

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    private int color;

    Colors(int color) {
        this.color = color;
    }
}