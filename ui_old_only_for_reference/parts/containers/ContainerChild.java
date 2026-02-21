package net.justacoder.shadowclient.main.ui_old.parts.containers;

import net.justacoder.shadowclient.main.ui_old.DrawableUiElement;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiPart;
import net.justacoder.shadowclient.main.ui_old.MouseInteractableUiElement;
import net.justacoder.shadowclient.main.ui_old.ScreenSizeChangeListeningUiElement;

public abstract class ContainerChild implements MouseInteractableUiElement, DrawableUiElement, JsonSerializableUiPart, ScreenSizeChangeListeningUiElement {

    private int x;
    private int y;

    protected ContainerChild(int x, int y) {
        updatePositioning(x, y);
    }

    public void updatePositioning(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract int getWidth();
    public abstract int getHeight();

}
