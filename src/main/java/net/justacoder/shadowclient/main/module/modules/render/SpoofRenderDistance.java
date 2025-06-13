package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class SpoofRenderDistance extends Module {

    private NumberSetting spoofedDistance = new NumberSetting(new TranslatableString("setting.module.shadowclient.spoofrenderdistance.distance"), 2, 32, 8, 0);

    public SpoofRenderDistance() {

        super("spoofrenderdistance", ModuleCategory.RENDER, new String[]{"spoof view distance", "spoof render distance", "spoofviewdist", "spoofrenderdist", "entity render distance"});

        addSetting(spoofedDistance);

        spoofedDistance.addOnFinishCallback((old, nev) -> mc.worldRenderer.reload());

    }

    @Override
    public boolean onEnable() {
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
        return super.onEnable();
    }

    @Override
    public boolean onDisable() {
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
        return super.onDisable();
    }

    public int getDistanceBlocks(int original) {
        if (!enabled) {
            return original;
        }
        return spoofedDistance.intValueEased() * 16;
    }

    public int getDistanceChunks(int original) {
        if (!enabled) {
            return original;
        }
        return spoofedDistance.intValueEased();
    }

}
