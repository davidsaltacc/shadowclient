package net.shadowclient.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @Inject(method = "getBlockParticle", at = @At("HEAD"), cancellable = true)
    private void onGetBlockParticle(CallbackInfoReturnable<Block> cir) {
        if (ModuleManager.RenderBarriersModule.enabled) {
            cir.setReturnValue(Blocks.BARRIER);
        }
    }

}
