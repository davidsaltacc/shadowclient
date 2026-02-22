package net.justacoder.shadowclient.main.ui.elements.singlechild;

import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.cursor.Cursor;
import org.joml.Vector2i;

public class CursorElement extends SingleChildUiElement {

    private final Cursor cursor;

    public CursorElement(UiElement child, Cursor cursor) {
        super(child);
        this.cursor = cursor;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (isHovered(mouseX, mouseY)) {
            context.setCursor(cursor);
        } else {
            context.setCursor(Cursor.DEFAULT);
        }
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
