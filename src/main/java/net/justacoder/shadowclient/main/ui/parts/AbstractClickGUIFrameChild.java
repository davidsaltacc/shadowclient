package net.justacoder.shadowclient.main.ui.parts;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.ui.DrawableUiElement;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiElement;
import net.minecraft.client.gui.DrawContext;
import org.jspecify.annotations.NonNull;

public abstract class AbstractClickGUIFrameChild implements JsonSerializableUiElement, DrawableUiElement {

    private final String id;

    protected AbstractClickGUIFrameChild(String id) {
        this.id = id;
    }

    @Override
    public @NonNull String getId() {
        return id;
    }

    @Override
    public final void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        SCMain.warn("AbstractClickGUIFrameChild.render(DrawContext, i, i, f) called, this should not happen");
        // use the other render() function, AbstractClickGUIFrameChild's don't store their own position
        render(context, 0, 0, mouseX, mouseY, deltaTicks);
    }

    public abstract void render(DrawContext context, int posX, int posY, int scaledMouseX, int scaledMouseY, float deltaTicks);

    public abstract int getHeight();

}
