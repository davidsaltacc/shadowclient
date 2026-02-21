package net.justacoder.shadowclient.main.ui.elements;

import net.justacoder.shadowclient.main.ui.JsonSerializableUiPart;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

public abstract class UiElement implements JsonSerializableUiPart {

    protected UiElement(String id) {
        this.id = id;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    protected int x;
    protected int y;
    protected UiElement parent;
    private final String id;

    public void updatePositioning(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, float deltaTicks);
    public abstract void mouseClicked(Click click, boolean doubled); // does always get fired, does not check if element is hovered
    public abstract void mouseReleased(Click click); // same behavior as mouseClicked
    public abstract void mouseMoved(double mouseX, double mouseY);
    public abstract void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount); // same behavior as mouseClicked

}
