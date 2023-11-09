package net.shadowclient.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.player.Reach;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(at = @At("HEAD"), method = "getReachDistance", cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> ci) {
        Reach reach = ModuleManager.ReachModule;

        if (reach.enabled) {
            ci.setReturnValue(reach.distance());
        }
    }

    @Inject(at = @At("HEAD"), method = "hasExtendedReach", cancellable = true)
    private void hasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
        Reach reach = ModuleManager.ReachModule;

        if (reach.enabled) {
            cir.setReturnValue(true);
        }
    }
}
