package net.justacoder.shadowclient.main.ui.screens;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.HopefullyLaterConfigurableSettings;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiElement;
import net.justacoder.shadowclient.main.ui.parts.ClickGUIFrame;
import net.justacoder.shadowclient.main.util.UiRenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClickGUI extends Screen implements JsonSerializableUiElement {

    private final String id;
    private final List<ClickGUIFrame> frames;

    public ClickGUI(Text title, String id, List<ClickGUIFrame> frames) {
        super(title);
        this.id = id;
        this.frames = frames;
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        // no darkening
    }

    @Override
    protected void renderDarkening(DrawContext context, int x, int y, int width, int height) {
        // no darkening
    }

    @Override
    protected void applyBlur(DrawContext context) {
        if (HopefullyLaterConfigurableSettings.BLUR_BACKGROUND) {
            super.applyBlur(context);
        }
    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float deltaTicks) {

        super.render(context, scaledMouseX, scaledMouseY, deltaTicks);
        float disableScaleFactor = UiRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        UiRenderUtils.beforeUIRender(context);
        frames.forEach(frame -> frame.render(context, mouseX, mouseY, deltaTicks));
        UiRenderUtils.afterUIRender(context);

    }

    @Override
    public @NotNull JsonObject serialize() {
        JsonObject data = new JsonObject();
        frames.forEach(frame -> data.add(frame.getId(), frame.serialize()));
        return data;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public void deserialize(@NotNull JsonObject in) {
        in.keySet().forEach(key -> frames.stream().findFirst().ifPresent(frame -> frame.deserialize(in.get(key).getAsJsonObject())));
    }
    // TODO if we create this screen every time it is needed, probably add some caching to avoid reading the json files constantly

}
