package net.shadowclient.main.module.modules.player.cheststeal.button;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.shadowclient.main.module.ModuleManager;

public class CustomButtonWidget extends ButtonWidget { // just a custom button class to be able to disable rendering without mixins
    protected CustomButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (ModuleManager.ChestStealModule.enabled) {
            super.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public void onPress() {
        if (ModuleManager.ChestStealModule.enabled) {
            super.onPress();
        }
    }

    public static CustomButtonWidget newButton(int x, int y, int width, int height, String text, PressAction action) {
        return new CustomButtonWidget(x, y, width, height, Text.of(text), action, DEFAULT_NARRATION_SUPPLIER);
    }
}
