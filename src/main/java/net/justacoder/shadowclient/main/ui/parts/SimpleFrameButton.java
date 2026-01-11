package net.justacoder.shadowclient.main.ui.parts;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

public class SimpleFrameButton extends AbstractClickGUIFrameChild {
    // TODO finish implementing this, add animations and other stuff to it too, so we can build all underlying systems for the rest of the ui

    private final Runnable handler;

    public SimpleFrameButton(String id, TranslatableString text, Runnable handler) {
        super(id);
        this.handler = handler;
    }

    @Override
    public void render(DrawContext context, int posX, int posY, int scaledMouseX, int scaledMouseY, float deltaTicks) {
        
    }

    @Override
    public int getHeight() {
        return 26;
    }

    @Override
    public @NotNull JsonObject serialize() { return new JsonObject(); }

    @Override
    public void deserialize(@NotNull JsonObject in) {}

}
