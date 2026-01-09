package net.justacoder.shadowclient.main.ui.screens;

import net.justacoder.shadowclient.main.HopefullyLaterConfigurableSettings;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGUI extends Screen {

    public ClickGUI(Text title) {
        super(title);
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        // no darkening
    }

    @Override
    protected void applyBlur(DrawContext context) {
        if (HopefullyLaterConfigurableSettings.BLUR_BACKGROUND) {
            applyBlur(context);
        }
    }

}
