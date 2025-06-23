package net.justacoder.shadowclient.mixin;

import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.justacoder.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @ModifyConstant(method = "renderFireOverlay", constant = @Constant(floatValue = -0.3F))
    private static float modifyFireOffset(float original) {
        return original - ModuleManager.NoFireOverlayModule.getOffset();
    }

}
