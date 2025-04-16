package net.justacoder.shadowclient.mixin;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DebugHud.class)
public interface DebugHudAccessor {

    @Accessor("showDebugHud")
    boolean debugEnabled();

}
