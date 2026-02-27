package net.justacoder.shadowclient.main.ui.elements;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

public abstract class UiElement {

    protected UiElement() {}

    public abstract int getWidth();
    public abstract int getHeight();
    protected int x;
    protected int y;
    protected UiElement parent;

    public void updatePositioning(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public boolean isInBounds(int mouseX, int mouseY) {
        return
                mouseX > x && mouseX < x + getWidth() &&
                mouseY > y && mouseY < y + getHeight();
    }

    // to solve issues with recursion, such as a center inside a stackContainer set to fit its children, as the stackContainer would adjust its size to the center, but the center would get its size from the stackContainer, and so on and on and on
    public abstract boolean widthReliesOnChildWidths();
    public abstract boolean heightReliesOnChildHeights();

    public abstract void render(DrawContext context, int mouseX, int mouseY, float deltaTicks);
    public abstract void mouseClicked(Click click, boolean doubled); // does always get fired, does not check if element is hovered
    public abstract void mouseReleased(Click click); // same behavior as mouseClicked
    public abstract void mouseMoved(double mouseX, double mouseY);
    public abstract void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount); // same behavior as mouseClicked
    public abstract void screenOpening();
    public abstract void screenClosing();

}
