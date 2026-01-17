package net.justacoder.shadowclient.main.ui.screens;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ui.parts.ClickGUIFrame;
import net.justacoder.shadowclient.main.util.UiUtils;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClickGUIScreen extends SCScreen {

    private final List<ClickGUIFrame> frames;

    public ClickGUIScreen(Text title, String id, List<ClickGUIFrame> frames) {
        super(id, title);
        this.frames = frames;
    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float deltaTicks) {

        super.render(context, scaledMouseX, scaledMouseY, deltaTicks);
        float disableScaleFactor = UiUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        UiUtils.beforeUIRender(context);
        frames.forEach(frame -> frame.render(context, mouseX, mouseY, deltaTicks));
        UiUtils.afterUIRender(context);

    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {

        float disableScaleFactor = UiUtils.guiScaleFactor();

        Click scaledClick = new Click(
                click.x() * disableScaleFactor,
                click.y() * disableScaleFactor,
                click.buttonInfo()
        );

        frames.forEach(frame -> frame.mouseClicked(scaledClick, doubled));

        return super.mouseClicked(click, doubled);
    }

    @Override
    public void mouseMoved(double scaledMouseX, double scaledMouseY) {

        float disableScaleFactor = UiUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        frames.forEach(frame -> frame.mouseMoved(mouseX, mouseY));

    }

    @Override
    public boolean mouseReleased(Click click) {

        float disableScaleFactor = UiUtils.guiScaleFactor();

        Click scaledClick = new Click(
                click.x() * disableScaleFactor,
                click.y() * disableScaleFactor,
                click.buttonInfo()
        );

        frames.forEach(frame -> frame.mouseReleased(scaledClick));

        return super.mouseReleased(click);
    }

    @Override
    public boolean mouseScrolled(double scaledMouseX, double scaledMouseY, double horizontalAmount, double verticalAmount) {

        float disableScaleFactor = UiUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        frames.forEach(frame -> frame.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount));

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public @NotNull JsonObject serialize() {
        JsonObject data = new JsonObject();
        frames.forEach(frame -> data.add(frame.getId(), frame.serialize()));
        return data;
    }

    @Override
    public void deserialize(@Nullable JsonObject in) {
        if (in != null) {
            in.keySet().forEach(key ->
                    frames.stream().filter(
                            child -> child.getId().equals(key)
                    ).findFirst().ifPresent(
                            frame -> frame.deserialize(in.get(key).getAsJsonObject())
                    )
            );
        }
    }

}
