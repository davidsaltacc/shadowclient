package net.shadowclient.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.shadowclient.main.event.EventManager;
import net.shadowclient.main.event.events.KnockbackEvent;
import net.shadowclient.main.module.ModuleManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow
    @Final
    protected MinecraftClient client;

    public Screen crntScreen;
    public boolean hideItem;

    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackEvent event = new KnockbackEvent(x, y, z);
        EventManager.fireEvent(event);
        super.setVelocityClient(event.x, event.y, event.z);
    }

    @Override
    public boolean hasStatusEffect(StatusEffect effect) {

        if (effect == StatusEffects.NIGHT_VISION && ModuleManager.NightVisionModule.enabled) {
            return true;
        }

        if (effect == StatusEffects.LEVITATION && ModuleManager.NoLevitationModule.enabled) {
            return false;
        }

        if (effect == StatusEffects.BLINDNESS && ModuleManager.NoBlindModule.enabled) {
            return false;
        }

        if (effect == StatusEffects.DARKNESS && ModuleManager.NoBlindModule.enabled) {
            return false;
        }


        return super.hasStatusEffect(effect);
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", opcode = Opcodes.GETFIELD, ordinal = 0), method = "updateNausea()V")
    private void beforeUpdateNausea(CallbackInfo ci) {
        if (!ModuleManager.PortalGUIModule.enabled) {
            return;
        }

        crntScreen = client.currentScreen;
        client.currentScreen = null;
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;nauseaIntensity:F", opcode = Opcodes.GETFIELD, ordinal = 1), method = "updateNausea()V")
    private void afterUpdateNausea(CallbackInfo ci) {
        if (crntScreen == null) {
            return;
        }

        client.currentScreen = crntScreen;
        crntScreen = null;
    }

    @Override
    protected float getJumpVelocity() {
        return ModuleManager.HighJumpModule.increase(super.getJumpVelocity());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0), method = "tickMovement()V")
    private void onTickMovementItemUse(CallbackInfo ci) {
        if (ModuleManager.NoSlowdownModule.enabled) {
            hideItem = true;
        }
    }

    @Inject(at = @At("HEAD"), method = "isUsingItem", cancellable = true)
    private void onIsUsingItem(CallbackInfoReturnable<Boolean> cir) {
        if (!hideItem) {
            return;
        }

        cir.setReturnValue(false);
        hideItem = false;
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;ticksToNextAutojump:I", opcode = Opcodes.GETFIELD, ordinal = 0), method = "tickMovement()V")
    private void afterIsUsingItem(CallbackInfo ci) {
        hideItem = false;
    }

}
