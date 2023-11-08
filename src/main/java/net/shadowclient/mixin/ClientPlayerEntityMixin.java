package net.shadowclient.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.event.events.KnockbackEvent;
import net.shadowclient.main.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackEvent event = new KnockbackEvent(x, y, z);
        SCMain.fireEvent(event);
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
}
