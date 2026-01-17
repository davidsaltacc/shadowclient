package net.justacoder.shadowclient.main.ui.screens;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ui.parts.AbstractContainer;
import net.justacoder.shadowclient.main.util.UiUtils;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO read devnotes.txt to get an idea of what needs to be done

public class GenericScreen extends SCScreen {

    final List<AbstractContainer> containers;

    public GenericScreen(String id, Text title, List<AbstractContainer> containers) {
        super(id, title);
        this.containers = containers;
    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float deltaTicks) {

        super.render(context, scaledMouseX, scaledMouseY, deltaTicks);
        float disableScaleFactor = UiUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        UiUtils.beforeUIRender(context);
        containers.forEach(container -> container.render(context, mouseX, mouseY, deltaTicks));
        UiUtils.afterUIRender(context);

    }

    @Override
    public @NotNull JsonObject serialize() {
        return new JsonObject(); // TODO just serialize children
    }

    @Override
    public void deserialize(@Nullable JsonObject in) {
        // TODO just deserialize children
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        return super.mouseClicked(click, doubled); // TODO click children
    }

    @Override
    public boolean mouseReleased(Click click) {
        return super.mouseReleased(click); // TODO release children
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY); // TODO move children
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount); // TODO scroll children
    }

}
