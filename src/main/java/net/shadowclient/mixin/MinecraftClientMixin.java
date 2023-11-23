package net.shadowclient.mixin;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.ModStatus;
import net.shadowclient.main.config.SCSettings;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.PostTickEvent;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.render.EntitiesESP;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.mixininterface.IMinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClient {

    @Shadow
    private int itemUseCooldown;

    /** mixin won't shut. IDK why.
     * @author ...
     * @reason ...
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
        if (((MinecraftClient) (Object) this).world != null) {
            EventManager.fireEvent(new PreTickEvent());
        }
    }

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;endMonitor(ZLnet/minecraft/util/TickDurationMonitor;)V"))
    private void injectedPostTick(CallbackInfo ci) {
        if (((MinecraftClient) (Object) this).world != null) {
            EventManager.fireEvent(new PostTickEvent());
        }
    }

    @Inject(method = "hasOutline", at = @At(value = "HEAD"), cancellable = true)
    private void injected(Entity entity, CallbackInfoReturnable<Boolean> cir) {

        EntitiesESP entitiesEspModule = ModuleManager.EntitiesESPModule;

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

    @Override
    public void setItemUseCooldown(int cooldown) {
        this.itemUseCooldown = cooldown;
    }

    @Inject(method = "onInitFinished", at = @At("HEAD"))
    private void onInitFinished(RealmsClient realms, ResourceReload reload, RunArgs.QuickPlay quickPlay, CallbackInfo ci) {
        ModuleManager.getAllModules().forEach((n, m) -> m.postInit());
    }
}
