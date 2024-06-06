package net.justacoder.shadowclient.main.ui;

import net.justacoder.shadowclient.mixin.FontManagerAccessor;
import net.justacoder.shadowclient.mixin.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomFont {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static TextRenderer renderer = null;

    public static Pair<TextRenderer, Boolean> getTextRenderer() {
        FontManagerAccessor fma = ((FontManagerAccessor) ((MinecraftClientAccessor) mc).getFontManager());
        AtomicBoolean d = new AtomicBoolean(false);
        TextRenderer tr = new TextRenderer(id -> {
            FontStorage storage = fma.getFontStorages().getOrDefault(new Identifier("arial"), fma.getFontStorages().getOrDefault(new Identifier("default"), fma.getMissingStorage()));
            if (storage == fma.getFontStorages().get(new Identifier("default"))) {
                d.set(true);
            }
            return storage;
        }, true);
        return new Pair<>(tr, d.get());
    }

    public static void initTextRenderer() {
        renderer = mc.textRenderer;
        //Pair<TextRenderer, Boolean> textRendererAndDefault = getTextRenderer();
        //renderer = textRendererAndDefault.getLeft();
        //if (textRendererAndDefault.getRight()) {
        //    SCMain.error("Error initializing TTF renderer, defaulting to minecraft font");
        //}
    }
}
