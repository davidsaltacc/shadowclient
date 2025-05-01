package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

public class SpoofRenderDistance extends Module {

    private NumberSetting spoofedDistance = new NumberSetting("Spoofed Distance", 2, 32, 8, 0);

    public SpoofRenderDistance() {

        super("spoofrenderdistance", ModuleCategory.RENDER, new String[]{"spoof view distance", "spoof render distance", "spoofviewdist", "spoofrenderdist", "entity render distance"});

        addSetting(spoofedDistance);

        spoofedDistance.addOnFinishCallback((old, nev) -> mc.worldRenderer.reload());

    }

    @Override
    public void onEnable() {
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
    }

    @Override
    public void onDisable() {
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
    }

    public int getDistanceBlocks(int original) {
        if (!enabled) {
            return original;
        }
        return spoofedDistance.intValue() * 16;
    }

    public int getDistanceChunks(int original) {
        if (!enabled) {
            return original;
        }
        return spoofedDistance.intValue();
    }

}
