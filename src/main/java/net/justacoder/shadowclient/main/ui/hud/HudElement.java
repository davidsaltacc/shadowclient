package net.justacoder.shadowclient.main.ui.hud;

import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;

public class HudElement {
    public boolean shouldBeRendered;
    private String textContent;
    private final MinecraftClient mc = ShadowClientMain.mc;

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
        context.fill(0, offset, Font.getWidth(this.textContent) + 8, offset + Font.getHeight() + 8, Colors.HUD_ELEMENT_BACKGROUND.color);
        Font.renderString(context, this.textContent, 2, offset + 4, Colors.HUD_ELEMENT_TEXT.color);
    }

    public void render(DrawContext context, float tickDelta, int offset, boolean rightSide) {
        if (!rightSide) {
            render(context, tickDelta, offset);
            return;
        }
        int width = context.getScaledWindowWidth();
        context.fill(width - 6 - mc.textRenderer.getWidth(this.textContent), 4 + offset, width - 4, 4 + offset + Font.getHeight(), Colors.HUD_ELEMENT_BACKGROUND.color);
        Font.renderString(context, this.textContent, width - mc.textRenderer.getWidth(this.textContent) - 4, 6 + offset, Colors.HUD_ELEMENT_TEXT.color);
    }

    public int getHeight() {
        return Font.getHeight() + 8;
    }
}
