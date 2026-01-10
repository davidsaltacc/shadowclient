package net.justacoder.shadowclient.main.ui.screens;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.HopefullyLaterConfigurableSettings;
import net.justacoder.shadowclient.main.render.font.Font;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class ClickGUI extends Screen implements JsonSerializableUiElement {

    private final String id;

    public ClickGUI(Text title, String id) {
        super(title);
        this.id = id;
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        // no darkening
    }

    @Override
    protected void applyBlur(DrawContext context) {
        if (HopefullyLaterConfigurableSettings.BLUR_BACKGROUND) {
            super.applyBlur(context);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

        super.render(context, mouseX, mouseY, deltaTicks);

        Font.renderString(context, "hello hello 123 test", 0, 0, -1);
        // TODO make everything we render unaffected by gui scaling (like before)

    }

    @Override
    public @NotNull JsonObject serialize() { // TODO store serialized data of children (by id) (once we have children)
        // TODO if we create this screen every time it is needed, probably add some config caching to avoid reading the json files constantly
        return new JsonObject();
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public void deserialize(@NotNull JsonObject in) {}

}
