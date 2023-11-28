package net.shadowclient.main.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.ui.clickgui.Colors;

public class HudElement {
    public boolean shouldBeRendered;
    private String textContent;
    private final MinecraftClient mc = SCMain.mc;

    public HudElement(boolean rendered, String text) {
        this.shouldBeRendered = rendered;
        this.textContent = text;
    }

    public void shouldBeRendered(boolean rendered) {
        this.shouldBeRendered = rendered;
    }
    public void setTextContent(String text) {
        this.textContent = text;
    }

    public void render(DrawContext context, float tickDelta, int offset) {
        context.fill(2, 2 + offset, mc.textRenderer.getWidth(this.textContent), 2 + offset + getHeight(), Colors.HUD_ELEMENT_BACKGROUND.color);
        context.drawText(mc.textRenderer, this.textContent, 2, 2 + offset, Colors.HUD_ELEMENT_TEXT.color, false);
    }

    public int getHeight() {
        return mc.textRenderer.fontHeight;
    }
}
