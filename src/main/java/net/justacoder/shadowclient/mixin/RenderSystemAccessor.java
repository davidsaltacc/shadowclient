package net.justacoder.shadowclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderSystem.class)
public interface RenderSystemAccessor {

    @Accessor("shaderTextures")
    public static int[] getShaderTextures() { return new int[1]; };

}
