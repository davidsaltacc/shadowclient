package net.shadowclient.main.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.ui.clickgui.Colors;

public class HudElement {
    public boolean shouldBeRendered;
    private String textContent;
    private MinecraftClient mc = SCMain.mc;

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
        context.fill(0, offset, mc.textRenderer.getWidth(this.textContent), getHeight(), Colors.HUD_ELEMENT_BACKGROUND.color);
        context.drawTextWithShadow(mc.textRenderer, this.textContent, 0, offset, Colors.HUD_ELEMENT_TEXT.color);
    }

    public int getHeight() {
        return mc.textRenderer.fontHeight + 1;
    }
}
