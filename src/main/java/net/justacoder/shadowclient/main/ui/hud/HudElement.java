package net.justacoder.shadowclient.main.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;

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
        context.fill(2, 2 + offset, 3 + mc.textRenderer.getWidth(this.textContent), 2 + offset + getHeight(), Colors.HUD_ELEMENT_BACKGROUND.color);
        context.drawText(mc.textRenderer, this.textContent, 3, 3 + offset, Colors.HUD_ELEMENT_TEXT.color, false);
    }

    public void render(DrawContext context, float tickDelta, int offset, boolean rightSide) {
        if (!rightSide) {
            render(context, tickDelta, offset);
            return;
        }
        int width = context.getScaledWindowWidth();
        context.fill(width - 3 - mc.textRenderer.getWidth(this.textContent), 2 + offset, width - 2, 2 + offset + getHeight(), Colors.HUD_ELEMENT_BACKGROUND.color);
        context.drawText(mc.textRenderer, this.textContent, width - mc.textRenderer.getWidth(this.textContent) - 2, 3 + offset, Colors.HUD_ELEMENT_TEXT.color, false);
    }

    public int getHeight() {
        return mc.textRenderer.fontHeight;
    }
}
