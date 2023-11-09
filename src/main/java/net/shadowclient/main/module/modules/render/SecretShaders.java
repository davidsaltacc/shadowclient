package net.shadowclient.main.module.modules.render;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.EnumSetting;
import net.shadowclient.main.util.RenderUtils;
import org.jetbrains.annotations.Nullable;

@SearchTags({"shaders", "effects", "vfx", "special effects"})
public class SecretShaders extends Module {

    private Shaders currentShader;
    private Screen currentScreen;

    public final EnumSetting<Shaders> SHADER = new EnumSetting<>("ID", Shaders.NONE);

    public SecretShaders() {
        super("secretshaders", "Secret Shaders", "Shaders coded by Mojang made accessible.", ModuleCategory.FUN);

        addSetting(SHADER);
    }

    @Override
    public void OnEnable() {
        currentShader = Shaders.NONE;
        currentScreen = mc.currentScreen;
    }

    @Override
    public void OnDisable() {
        RenderUtils.loadShader(null);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PreTickEvent)) {
            return;
        }
        boolean newScreen = false;

        if (currentScreen != mc.currentScreen) {
            currentScreen = mc.currentScreen;
            newScreen = true;
        }
        if (currentShader != SHADER.getEnumValue() || newScreen) {
            RenderUtils.loadShader(SHADER.getEnumValue().getId());
            currentShader = SHADER.getEnumValue();
        }
    }

    public enum Shaders { // I wonder why they wasted so much time just to make unused shaders...
        NONE("NONE", null), // wow, they made a lot of shaders
        NOTCH("Notch", new Identifier("shaders/post/notch.json")),
        FXAA("FXAA", new Identifier("shaders/post/fxaa.json")),
        ART("Art", new Identifier("shaders/post/art.json")),
        BUMPY("Bumpy", new Identifier("shaders/post/bumpy.json")),
        BLOBS2("Blobs 2", new Identifier("shaders/post/blobs2.json")),
        PENCIL("Pencil", new Identifier("shaders/post/pencil.json")),
        COLOR_CONVOLVE("ColorConvolve", new Identifier("shaders/post/color_convolve.json")),
        DECONVERGE("Deconverge", new Identifier("shaders/post/deconverge.json")),
        FLIP("Flip", new Identifier("shaders/post/flip.json")),
        INVERT("Invert", new Identifier("shaders/post/invert.json")),
        NTSC("NTSC", new Identifier("shaders/post/ntsc.json")),
        OUTLINE("Outline", new Identifier("shaders/post/outline.json")),
        PHOSPHOR("Phosphor", new Identifier("shaders/post/phosphor.json")),
        SCAN_PINCUSHION("Scan Pincushion", new Identifier("shaders/post/scan_pincushion.json")),
        SOBEL("Sobel", new Identifier("shaders/post/sobel.json")),
        BITS("Bits", new Identifier("shaders/post/bits.json")),
        DESATURATE("Desaturate", new Identifier("shaders/post/desaturate.json")),
        GREEN("Green", new Identifier("shaders/post/green.json")),
        BLUR("Blur", new Identifier("shaders/post/blur.json")),
        WOBBLE("Wobble", new Identifier("shaders/post/wobble.json")),
        BLOBS("Blobs", new Identifier("shaders/post/blobs.json")),
        ANTIALIAS("AntiAlias", new Identifier("shaders/post/antialias.json")),
        CREEPER("Creeper", new Identifier("shaders/post/creeper.json")),
        SPIDER("Spider", new Identifier("shaders/post/spider.json"));

        final String name;
        final @Nullable Identifier id;

        Shaders(String name, @Nullable Identifier id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString() {
            return this.name;
        }
        public @Nullable Identifier getId() {
            return this.id;
        }
    }
}
