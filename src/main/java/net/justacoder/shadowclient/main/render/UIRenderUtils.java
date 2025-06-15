package net.justacoder.shadowclient.main.render;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.minecraft.client.gui.DrawContext;

public abstract class UIRenderUtils {

    public static float guiScaleDivisor() {
        return 1f / guiScaleFactor();
    }

    public static float guiScaleFactor() {
        return (float) ShadowClientMain.mc.getWindow().getScaleFactor();
    }

    public static void beforeUIRender(DrawContext context) {
        float scaleFac = guiScaleDivisor();
        context.getMatrices().scale(scaleFac, scaleFac, 1);
    }

    public static void afterUIRender(DrawContext context) {
        float scaleFac = guiScaleFactor();
        context.getMatrices().scale(scaleFac, scaleFac, 1);
    }

}
