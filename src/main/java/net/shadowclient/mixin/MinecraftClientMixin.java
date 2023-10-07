package net.shadowclient.mixin;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ModStatus;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.config.SCSettings;
import net.shadowclient.main.event.events.PostTickEvent;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.render.EntitiesESP;
import net.shadowclient.main.event.events.UpdateEvent;
import net.shadowclient.main.event.events.PreTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    /** MIXIN STFU
     * @author me
     * @reason yes
     */
    @Overwrite
    public static ModStatus getModStatus() {
        if (SCSettings.getSetting("VanillaSpoof").booleanValue()) {
            return new ModStatus(ModStatus.Confidence.PROBABLY_NOT, "Client jar signature and brand is untouched");
        }
        return ModStatus.check("vanilla", ClientBrandRetriever::getClientModName, "Client", MinecraftClient.class);
    }

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;startMonitor(ZLnet/minecraft/util/TickDurationMonitor;)Lnet/minecraft/util/profiler/Profiler;", shift = At.Shift.AFTER))
    private void injectedPreTick(CallbackInfo ci) {
        SCMain.OnEvent(new UpdateEvent());
        if (((MinecraftClient) (Object) this).world != null) {
            SCMain.OnEvent(new PreTickEvent());
        }
    }

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;endMonitor(ZLnet/minecraft/util/TickDurationMonitor;)V"))
    private void injectedPostTick(CallbackInfo ci) {
        SCMain.OnEvent(new UpdateEvent());
        if (((MinecraftClient) (Object) this).world != null) {
            SCMain.OnEvent(new PostTickEvent());
        }
    }

    @Inject(method = "hasOutline", at = @At(value = "HEAD"), cancellable = true)
    private void injected(Entity entity, CallbackInfoReturnable<Boolean> cir) {

        EntitiesESP entitiesEspModule = (EntitiesESP) ModuleManager.getModule("entitiesesp");

        if (!entitiesEspModule.enabled) {
            cir.setReturnValue(cir.getReturnValue());
            return;
        }

        if (entity instanceof PlayerEntity && entitiesEspModule.DrawPlayerEntityOutlines.booleanValue()) {
            cir.setReturnValue(true);
        } else if (entity instanceof Monster && entitiesEspModule.DrawHostileEntityOutlines.booleanValue()) {
            cir.setReturnValue(true);
        } else if (entity instanceof PassiveEntity && entitiesEspModule.DrawPassiveEntityOutlines.booleanValue()) {
            cir.setReturnValue(true);
        } else if (entity instanceof AmbientEntity && entitiesEspModule.DrawAmbientEntityOutlines.booleanValue()) {
            cir.setReturnValue(true);
        } else if (!(entity instanceof PlayerEntity) && !(entity instanceof Monster) && !(entity instanceof PassiveEntity) && !(entity instanceof AmbientEntity) && entitiesEspModule.DrawOtherEntityOutlines.booleanValue()) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(cir.getReturnValue());
        }

    }
}
