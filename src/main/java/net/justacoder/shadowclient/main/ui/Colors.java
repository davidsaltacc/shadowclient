package net.justacoder.shadowclient.main.ui;

import net.justacoder.shadowclient.main.util.ColorUtils;

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

    public int lerpWithOther(double t, int other) {
        if (t == 0) {
            return color;
        }
        if (t == 1) {
            return other;
        }
        int[] first = ColorUtils.int2RGBA(color);
        int[] second = ColorUtils.int2RGBA(other);
        return ColorUtils.RGBA2int(
                (int) Math.floor(first[0] * (1 - t) + second[0] * t),
                (int) Math.floor(first[1] * (1 - t) + second[1] * t),
                (int) Math.floor(first[2] * (1 - t) + second[2] * t),
                (int) Math.floor(first[3] * (1 - t) + second[3] * t)
        );
    }

    private int color;

    Colors(int color) {
        this.color = color;
    }
}