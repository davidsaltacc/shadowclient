package net.justacoder.shadowclient.main.ui;

import net.minecraft.client.gui.DrawContext;

public interface DrawableUiElement {

    void render(DrawContext context, int mouseX, int mouseY, float deltaTicks);

}
