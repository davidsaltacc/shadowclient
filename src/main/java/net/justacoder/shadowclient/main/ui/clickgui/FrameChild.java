package net.justacoder.shadowclient.main.ui.clickgui;

import net.minecraft.client.gui.DrawContext;

public abstract class FrameChild {

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
    }

    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
    }

    public void init() {
    }

    public abstract int getHeight();

    public void charTyped(char c, int mod) {}
}
