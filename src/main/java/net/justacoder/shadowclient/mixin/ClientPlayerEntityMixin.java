package net.justacoder.shadowclient.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.event.events.DamageEvent;
import net.justacoder.shadowclient.main.event.events.KnockbackEvent;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow @Final protected MinecraftClient client;
    @Shadow public abstract boolean isUsingItem();
    @Shadow public Input input;
    @Shadow private boolean inSneakingPose;
    @Unique public Screen crntScreen;

    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackEvent event = new KnockbackEvent(x, y, z);
        EventManager.fireEvent(event);
        super.setVelocityClient(event.x, event.y, event.z);
    }

    @Override
    public boolean hasStatusEffect(RegistryEntry<StatusEffect> effect) {

        if (effect == StatusEffects.NIGHT_VISION && (ModuleManager.NightVisionModule.enabled || ModuleManager.XrayModule.enabled)) {
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

    @Override
    public @Nullable StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect) {

        if (effect == StatusEffects.LEVITATION && ModuleManager.NoLevitationModule.enabled) {
            return null;
        }

        return super.getStatusEffect(effect);
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", opcode = Opcodes.GETFIELD, ordinal = 0), method = "tickNausea")
    private void beforeUpdateNausea(CallbackInfo ci) {
        if (!ModuleManager.PortalGUIModule.enabled) {
            return;
        }

        crntScreen = client.currentScreen;
        client.currentScreen = null;
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;nauseaIntensity:F", opcode = Opcodes.GETFIELD, ordinal = 1), method = "tickNausea")
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

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"), method = "tickMovement")
    private boolean onIsUsingItem(ClientPlayerEntity instance) {
        if (ModuleManager.NoSlowdownModule.enabled) {
            return false;
        }
        return isUsingItem();
    }

    @Inject(method = "updateHealth", at = @At("HEAD"))
    private void onDamageTaken(float health, CallbackInfo ci) {
        if (getHealth() > health) {
            EventManager.fireEvent(new DamageEvent(getHealth() - health));
        }
    }

    @Override
    public double getBlockInteractionRange() {
        if (ModuleManager.ReachModule.enabled) {
            return ModuleManager.ReachModule.distance();
        }
        return super.getBlockInteractionRange();
    }

    @Override
    public double getEntityInteractionRange() {
        if (ModuleManager.ReachModule.enabled) {
            return ModuleManager.ReachModule.distance();
        }
        return super.getEntityInteractionRange();
    }

    @Override
    public boolean isSneaking() {
        if (ModuleManager.AutoSneakModule.enabled) {
            if (ModuleManager.AutoSneakModule.serverSideOnly.booleanValue()) {
                return input.playerInput.sneak();
            } else {
                return true;
            }
        }
        if (ModuleManager.FreecamModule.enabled) {
            return false;
        }
        return input.playerInput.sneak();
    }

    @Redirect(method = "sendSneakingPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSneaking()Z"))
    private boolean serverSideAutoSneak(ClientPlayerEntity player) {
        if (ModuleManager.AutoSneakModule.enabled && ModuleManager.AutoSneakModule.serverSideOnly.booleanValue()) {
            return true;
        }
        return player.isSneaking();
    }

}
