package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.SCMain;
import net.minecraft.client.gui.DrawContext;

public abstract class UiRenderUtils {

    public static float guiScaleDivisor() {
        return 1f / guiScaleFactor();
    }

    public static float guiScaleFactor() {
        return SCMain.mc.getWindow().getScaleFactor();
    }

    public static void beforeUIRender(DrawContext context) {
        context.getMatrices().pushMatrix();
        float scaleFac = guiScaleDivisor();
        context.getMatrices().scale(scaleFac, scaleFac);
    }

    public static void afterUIRender(DrawContext context) {
        context.getMatrices().popMatrix();
    }

}
