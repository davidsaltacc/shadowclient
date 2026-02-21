package net.justacoder.shadowclient.main.ui.elements;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;

import java.util.List;

public abstract class MultipleChildrenUiElement extends UiElement {

    protected List<UiElement> children;

    protected MultipleChildrenUiElement(String id, List<UiElement> children) {
        super(id);
        this.children = children;
        children.forEach(child -> child.parent = this); // TODO make an abstract allowChildren() method that verifies if children can be added (some elements may need specific amounts of children)
    }

    @Override
    public void updatePositioning(int newX, int newY) {
        super.updatePositioning(newX, newY);
        List<Vector2i> newChildPos = getNewChildPositions();
        for (int i = 0; i < newChildPos.size(); i++) {
            Vector2i pos = newChildPos.get(i);
            children.get(i).updatePositioning(pos.x, pos.y);
        }
    }

    public abstract List<Vector2i> getNewChildPositions();

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        children.forEach(child -> child.render(context, mouseX, mouseY, deltaTicks));
    }

    public void mouseClicked(Click click, boolean doubled) {
        children.forEach(child -> child.mouseClicked(click, doubled));
    }

    public void mouseReleased(Click click) {
        children.forEach(child -> child.mouseReleased(click));
    }

    public void mouseMoved(double mouseX, double mouseY) {
        children.forEach(child -> child.mouseMoved(mouseX, mouseY));
    }

    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        children.forEach(child -> child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount));
    }

}
