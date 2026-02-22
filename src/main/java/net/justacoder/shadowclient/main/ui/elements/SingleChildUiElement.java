package net.justacoder.shadowclient.main.ui.elements;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;

public abstract class SingleChildUiElement extends UiElement {

    protected UiElement child;

    protected SingleChildUiElement(UiElement child) {
        this.child = child;
        this.child.parent = this;
    }

    @Override
    public void updatePositioning(int newX, int newY) {
        super.updatePositioning(newX, newY);
        Vector2i newChildPos = getNewChildPosition();
        child.updatePositioning(newChildPos.x, newChildPos.y);
    }

    public abstract Vector2i getNewChildPosition();

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        child.render(context, mouseX, mouseY, deltaTicks);
    }

    public void mouseClicked(Click click, boolean doubled) {
        child.mouseClicked(click, doubled);
    }

    public void mouseReleased(Click click) {
        child.mouseReleased(click);
    }

    public void mouseMoved(double mouseX, double mouseY) {
        child.mouseMoved(mouseX, mouseY);
    }

    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void screenOpening() {
        child.screenOpening();
    }

    @Override
    public void screenClosing() {
        child.screenClosing();
    }
}
