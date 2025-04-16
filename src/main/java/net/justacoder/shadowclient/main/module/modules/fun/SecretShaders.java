package net.justacoder.shadowclient.main.module.modules.fun;

import net.justacoder.shadowclient.main.event.events.PerspectiveChangeEvent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.util.RenderUtils;
import org.jetbrains.annotations.Nullable;

@EventListener({PreTickEvent.class, PerspectiveChangeEvent.class})
@SearchTags({"shaders", "effects", "vfx", "special effects", "super secret settings"})
public class SecretShaders extends Module {

    private Screen currentScreen;

    public final EnumSetting<Shaders> SHADER = new EnumSetting<>("ID", Shaders.NONE);

    public SecretShaders() {
        super("secretshaders", ModuleCategory.FUN);

        SHADER.addChangeCallback((shader, old) -> updateShader());

        addSetting(SHADER);
    }

    @Override
    public void onEnable() {
        currentScreen = mc.currentScreen;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.gameRenderer != null) {
            RenderUtils.loadShader(null);
        }
        super.onDisable();
    }

    private void updateShader() {
        if (enabled) {
            RenderUtils.loadShader(SHADER.getEnumValue().getId());
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof PreTickEvent) {
            if (currentScreen != mc.currentScreen) {
                currentScreen = mc.currentScreen;
                updateShader();
            }
        } else if (event instanceof PerspectiveChangeEvent) {
            updateShader();
        }
    }

    public enum Shaders { // they got removed in 1.21, so I added them back manually
        NONE("NONE", null),
        NOTCH("Notch", Identifier.ofVanilla("shaders/post/notch.json")),
        FXAA("FXAA", Identifier.ofVanilla("shaders/post/fxaa.json")),
        ART("Art", Identifier.ofVanilla("shaders/post/art.json")),
        BUMPY("Bumpy", Identifier.ofVanilla("shaders/post/bumpy.json")),
        BLOBS2("Blobs 2", Identifier.ofVanilla("shaders/post/blobs2.json")),
        PENCIL("Pencil", Identifier.ofVanilla("shaders/post/pencil.json")),
        COLOR_CONVOLVE("ColorConvolve", Identifier.ofVanilla("shaders/post/color_convolve.json")),
        DECONVERGE("Deconverge", Identifier.ofVanilla("shaders/post/deconverge.json")),
        FLIP("Flip", Identifier.ofVanilla("shaders/post/flip.json")),
        INVERT("Invert", Identifier.ofVanilla("shaders/post/invert.json")),
        NTSC("NTSC", Identifier.ofVanilla("shaders/post/ntsc.json")),
        OUTLINE("Outline", Identifier.ofVanilla("shaders/post/outline.json")),
        PHOSPHOR("Phosphor", Identifier.ofVanilla("shaders/post/phosphor.json")),
        SCAN_PINCUSHION("Scan Pincushion", Identifier.ofVanilla("shaders/post/scan_pincushion.json")),
        SOBEL("Sobel", Identifier.ofVanilla("shaders/post/sobel.json")),
        BITS("Bits", Identifier.ofVanilla("shaders/post/bits.json")),
        DESATURATE("Desaturate", Identifier.ofVanilla("shaders/post/desaturate.json")),
        GREEN("Green", Identifier.ofVanilla("shaders/post/green.json")),
        BLUR("Blur", Identifier.ofVanilla("shaders/post/blur.json")),
        WOBBLE("Wobble", Identifier.ofVanilla("shaders/post/wobble.json")),
        BLOBS("Blobs", Identifier.ofVanilla("shaders/post/blobs.json")),
        ANTIALIAS("AntiAlias", Identifier.ofVanilla("shaders/post/antialias.json")),
        CREEPER("Creeper", Identifier.ofVanilla("shaders/post/creeper.json")),
        SPIDER("Spider", Identifier.ofVanilla("shaders/post/spider.json"));

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
