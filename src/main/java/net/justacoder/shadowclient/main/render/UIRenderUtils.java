package net.justacoder.shadowclient.main.render;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.minecraft.client.gui.DrawContext;

public abstract class UIRenderUtils {

    public static float disableGuiScaleFactor() {
        return 1f / enableGuiScaleFactor();
    }

    public static float enableGuiScaleFactor() {
        return (float) ShadowClientMain.mc.getWindow().getScaleFactor();
    }

    public static void beforeUIRender(DrawContext context) {
        float scaleFac = disableGuiScaleFactor();
        context.getMatrices().scale(scaleFac, scaleFac, 1);
    }

    public static void afterUIRender(DrawContext context) {
        float scaleFac = enableGuiScaleFactor();
        context.getMatrices().scale(scaleFac, scaleFac, 1);
    }

}
