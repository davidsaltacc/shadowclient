package net.shadowclient.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.event.events.KnockbackEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        KnockbackEvent event = new KnockbackEvent(x, y, z);
        SCMain.OnEvent(event);
        super.setVelocityClient(event.x, event.y, event.z);
    }

}
