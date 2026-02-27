package net.justacoder.shadowclient.main.ui.elements.singlechild.positioning;

import net.justacoder.shadowclient.main.ui.elements.Properties;
import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import org.joml.Vector2i;

public class CenterElement extends SingleChildUiElement {

    private final Properties.CenterAxes axes;

    public CenterElement(UiElement child, Properties.CenterAxes axes) {
        super(child);
        this.axes = axes;
    }

    @Override
    public Vector2i getNewChildPosition() {
        return new Vector2i(
                x + getOffsetX(),
                y + getOffsetY()
        );
    }

    @Override
    public void updatePositioning(int newX, int newY) {
        cachedWidthInvalid = true;
        cachedHeightInvalid = true;
        cachedOffsetXInvalid = true;
        cachedOffsetYInvalid = true;
        super.updatePositioning(newX, newY);
    }

    private int width;
    private int height;
    private int offsetX;
    private int offsetY;
    private boolean cachedWidthInvalid;
    private boolean cachedHeightInvalid;
    private boolean cachedOffsetXInvalid;
    private boolean cachedOffsetYInvalid;

    public int getOffsetX() {
        if (cachedOffsetXInvalid) {
            if (axes == Properties.CenterAxes.ONLY_Y) {
                offsetX = 0;
            } else if (axes == Properties.CenterAxes.ONLY_Y_SAME_X) {
                offsetX = getOffsetY();
            } else {
                offsetX = getWidth() / 2 - child.getWidth() / 2;
            }
        }
        return offsetX;
    }

    public int getOffsetY() {
        if (cachedOffsetYInvalid) {
            if (axes == Properties.CenterAxes.ONLY_X) {
                offsetY = 0;
            } else if (axes == Properties.CenterAxes.ONLY_X_SAME_Y) {
                offsetY = getOffsetX();
            } else {
                offsetY = getHeight() / 2 - child.getHeight() / 2;
            }
        }
        return offsetY;
    }

    @Override
    public int getWidth() {
        if (cachedWidthInvalid) {
            if (axes == Properties.CenterAxes.ONLY_Y) {
                width = child.getWidth();
            } else if (axes == Properties.CenterAxes.ONLY_Y_SAME_X) {
                width = child.getWidth() + getOffsetY();
            } else {
                width = parent.widthReliesOnChildWidths() ? child.getWidth() : parent.getWidth();
            }
        }
        return width;
    }

    @Override
    public int getHeight() {
        if (cachedHeightInvalid) {
            if (axes == Properties.CenterAxes.ONLY_X) {
                height = child.getHeight();
            } else if (axes == Properties.CenterAxes.ONLY_X_SAME_Y) {
                height = child.getHeight() + getOffsetX();
            } else {
                height = parent.heightReliesOnChildHeights() ? child.getHeight() : parent.getHeight();
            }
        }
        return height;
    }

    @Override
    public boolean widthReliesOnChildWidths() {
        return axes == Properties.CenterAxes.ONLY_Y || axes == Properties.CenterAxes.ONLY_Y_SAME_X || parent.widthReliesOnChildWidths();
    }

    @Override
    public boolean heightReliesOnChildHeights() {
        return axes == Properties.CenterAxes.ONLY_X || axes == Properties.CenterAxes.ONLY_X_SAME_Y || parent.heightReliesOnChildHeights();
    }

}
