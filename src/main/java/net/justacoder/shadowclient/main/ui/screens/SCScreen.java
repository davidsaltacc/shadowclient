package net.justacoder.shadowclient.main.ui.screens;

import net.justacoder.shadowclient.main.config.ConfigManager;
import net.justacoder.shadowclient.main.config.HopefullyLaterConfigurableSettings;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public abstract class SCScreen extends Screen implements JsonSerializableUiElement {

    protected final String id;

    protected SCScreen(String id, Text title) {
        super(title);
        this.id = id;
    }

    @Override
    protected void init() {
        this.deserialize(ConfigManager.getData(ConfigManager.ConfigType.UI_DATA, this.id));
    }

    @Override
    public void removed() {
        ConfigManager.setData(ConfigManager.ConfigType.UI_DATA, id, this.serialize());
    }

    @Override
    public @NotNull String getId() {
        return "";
    }

    @Override
    public boolean shouldPause() {
        return false;
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

}
