package net.justacoder.shadowclient.main.ui.elements.singlechild.visual;

import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;

public class BackgroundElement extends SingleChildUiElement {

    private final int color;

    public BackgroundElement(UiElement child, int color) {
        super(child);
        this.color = color;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.fill(x, y, x + child.getWidth(), y + child.getHeight(), color);
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    public Vector2i getNewChildPosition() {
        return new Vector2i(x, y);
    }

    @Override
    public int getWidth() {
        return child.getWidth();
    }

    @Override
    public int getHeight() {
        return child.getHeight();
    }

    @Override
    public boolean widthReliesOnChildWidths() {
        return true;
    }

    @Override
    public boolean heightReliesOnChildHeights() {
        return true;
    }

}
