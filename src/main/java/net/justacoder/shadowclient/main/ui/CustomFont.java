package net.justacoder.shadowclient.main.ui;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.util.JavaUtils;
import net.justacoder.shadowclient.mixin.FontManagerAccessor;
import net.justacoder.shadowclient.mixin.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.util.Identifier;

public abstract class CustomFont {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static TextRenderer renderer = null;

    public static TextRenderer getTextRenderer() {
        FontManagerAccessor fma = ((FontManagerAccessor) ((MinecraftClientAccessor) mc).getFontManager());
        return new TextRenderer(id -> fma.getFontStorages().getOrDefault(new Identifier("calibri-regular"), fma.getMissingStorage()), true);
    }

    public static void initTextRenderer() {
        try {
            renderer = getTextRenderer();
        } catch (Exception e) {
            renderer = mc.textRenderer;
            SCMain.error("Error initializing TTF renderer, defaulting to minecraft font\n" + JavaUtils.stackTraceFromThrowable(e));
        }
    }
}
