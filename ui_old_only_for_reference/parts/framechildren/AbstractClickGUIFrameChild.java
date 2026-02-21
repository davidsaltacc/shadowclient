package net.justacoder.shadowclient.main.ui_old.parts.framechildren;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.ui_old.MouseInteractableUiElement;
import net.justacoder.shadowclient.main.ui_old.DrawableUiElement;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiPart;
import net.justacoder.shadowclient.main.ui_old.parts.ClickGUIFrame;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractClickGUIFrameChild implements JsonSerializableUiPart, DrawableUiElement, MouseInteractableUiElement {

    private final String id;
    protected ClickGUIFrame parent;
    private int offsetY;

    protected AbstractClickGUIFrameChild(String id) {
        this.id = id;
    }

    public void setParent(ClickGUIFrame parent) {
        this.parent = parent;
    }

    public void setOffsetY(int offsetY) { // offset from the parent frame (from the lower edge outwards)
        this.offsetY = offsetY;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return
                mouseX > parent.getX() &&
                mouseX < parent.getX() + parent.getWidth() &&
                mouseY > parent.getY() + parent.getHeight() + offsetY &&
                mouseY < parent.getY() + parent.getHeight() + offsetY + this.getHeight();
    }

    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        SCMain.warn("AbstractClickGUIFrameChild.render(DrawContext, i, i, f) called, this should not happen");
        // use the other render() function, AbstractClickGUIFrameChild's don't store their own position
        render(context, 0, 0, mouseX, mouseY, deltaTicks);
    }

    public abstract void render(DrawContext context, int posX, int posY, int mouseX, int mouseY, float deltaTicks);

    public abstract int getHeight();

}
