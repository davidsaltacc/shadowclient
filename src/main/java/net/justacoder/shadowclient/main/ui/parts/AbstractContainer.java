package net.justacoder.shadowclient.main.ui.parts;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ui.DrawableUiElement;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiElement;
import net.justacoder.shadowclient.main.ui.MouseInteractableUiElement;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractContainer implements MouseInteractableUiElement, DrawableUiElement, JsonSerializableUiElement {

    // TODO this contains multiple ContainerChildren - positioning is determined by the container type (StackContainer, ScrollableStackContainer, ...)

    private final String id;

    public AbstractContainer(String id) {
        this.id = id;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

    }

    @Override
    public @NotNull JsonObject serialize() {
        return new JsonObject();
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public void deserialize(@Nullable JsonObject in) {

    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {

    }

    @Override
    public void mouseReleased(Click click) {

    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {

    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {

    }

}
