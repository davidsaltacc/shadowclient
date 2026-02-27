package net.justacoder.shadowclient.main.ui.elements.singlechild.event;

import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import net.minecraft.client.gui.Click;
import org.joml.Vector2i;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

// TODO add other event elements (keyboard, ???...)
public class MouseEventUiElement extends SingleChildUiElement {

    private final List<Consumer<ClickEventData>> clickEventListeners = new ArrayList<>();
    private final List<Consumer<ReleaseEventData>> releaseEventListeners = new ArrayList<>();
    private final List<Consumer<HoverStartEventData>> hoverStartEventListeners = new ArrayList<>();
    private final List<Consumer<HoverEndEventData>> hoverEndEventListeners = new ArrayList<>();
    private final List<Consumer<ScrollEventData>> scrollEventListeners = new ArrayList<>();

    private final boolean notifyBeforeChildren;

    public MouseEventUiElement(UiElement child) {
        this(child, false);
    }

    public MouseEventUiElement(UiElement child, boolean notifyBeforeChildren) {
        super(child);
        this.notifyBeforeChildren = notifyBeforeChildren;
    }

    public void addClickListener(Consumer<ClickEventData> listener) {
        clickEventListeners.add(listener);
    }

    public void addReleaseListener(Consumer<ReleaseEventData> listener) {
        releaseEventListeners.add(listener);
    }

    public void addHoverStartListener(Consumer<HoverStartEventData> listener) {
        hoverStartEventListeners.add(listener);
    }

    public void addHoverEndListener(Consumer<HoverEndEventData> listener) {
        hoverEndEventListeners.add(listener);
    }

    public void addScrollListener(Consumer<ScrollEventData> listener) {
        scrollEventListeners.add(listener);
    }

    @Override
    public Vector2i getNewChildPosition() {
        return new Vector2i(x, y);
    }

    @Override
    public int getWidth() {
        return child.getWidth();
    }

    @Override
    public int getHeight() {
        return child.getHeight();
    }

    @Override
    public boolean widthReliesOnChildWidths() {
        return true;
    }

    @Override
    public boolean heightReliesOnChildHeights() {
        return true;
    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {
        if (!this.notifyBeforeChildren) {
            super.mouseClicked(click, doubled);
        }
        if (isInBounds((int) click.x(), (int) click.y())) {
            clickEventListeners.forEach(listener -> listener.accept(new ClickEventData(click, doubled)));
        }
        if (this.notifyBeforeChildren) {
            super.mouseClicked(click, doubled);
        }
    }

    @Override
    public void mouseReleased(Click click) {
        if (!this.notifyBeforeChildren) {
            super.mouseReleased(click);
        }
        if (isInBounds((int) click.x(), (int) click.y())) {
            releaseEventListeners.forEach(listener -> listener.accept(new ReleaseEventData(click)));
        }
        if (this.notifyBeforeChildren) {
            super.mouseReleased(click);
        }
    }

    private boolean lastHovered = false;

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (!this.notifyBeforeChildren) {
            super.mouseMoved(mouseX, mouseY);
        }
        boolean hovered = isInBounds((int) mouseX, (int) mouseY);
        if (hovered && !lastHovered) {
            hoverStartEventListeners.forEach(listener -> listener.accept(new HoverStartEventData(mouseX, mouseY)));
            lastHovered = true;
        } else if (!hovered && lastHovered) {
            hoverEndEventListeners.forEach(listener -> listener.accept(new HoverEndEventData(mouseX, mouseY)));
            lastHovered = false;
        }
        if (this.notifyBeforeChildren) {
            super.mouseMoved(mouseX, mouseY);
        }
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!this.notifyBeforeChildren) {
            super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        if (isInBounds((int) mouseX, (int) mouseY)) {
            scrollEventListeners.forEach(listener -> listener.accept(new ScrollEventData(mouseX, mouseY, horizontalAmount, verticalAmount)));
        }
        if (this.notifyBeforeChildren) {
            super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
    }

    public record ClickEventData(Click click, boolean doubled) {}
    public record ReleaseEventData(Click click) {}
    public record HoverStartEventData(double mouseX, double mouseY) {}
    public record HoverEndEventData(double mouseX, double mouseY) {}
    public record ScrollEventData(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {}

}
