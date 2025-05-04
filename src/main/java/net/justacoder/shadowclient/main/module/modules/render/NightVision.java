package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.mixin.LightmapTextureManagerAccessor;

public class NightVision extends Module {

    public NightVision() {
        super("nightvision", ModuleCategory.RENDER, new String[]{"nightvision", "night vision", "caven vision"});
    }

    @Override
    public void onEnable() {

        ((LightmapTextureManagerAccessor) mc.gameRenderer.getLightmapTextureManager()).markDirty(true);

        super.onEnable();

    }

    @Override
    public void onDisable() {

        ((LightmapTextureManagerAccessor) mc.gameRenderer.getLightmapTextureManager()).markDirty(true);

        super.onDisable();

    }

}
