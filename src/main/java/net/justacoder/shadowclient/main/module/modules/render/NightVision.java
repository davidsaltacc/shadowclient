package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.mixin.LightmapTextureManagerAccessor;

public class NightVision extends Module {

    public NightVision() {
        super("nightvision", ModuleCategory.RENDER, new String[]{"nightvision", "night vision", "caven vision"});
    }

    @Override
    public boolean onEnable() {

        if (mc.gameRenderer == null) {
            return true;
        }

        ((LightmapTextureManagerAccessor) mc.gameRenderer.getLightmapTextureManager()).markDirty(true);

        return super.onEnable();

    }

    @Override
    public boolean onDisable() {

        if (mc.gameRenderer == null) {
            return true;
        }

        ((LightmapTextureManagerAccessor) mc.gameRenderer.getLightmapTextureManager()).markDirty(true);

        return super.onDisable();

    }

}
