package net.shadowclient.mixininterface;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

public interface IMinecraftClient {

    void setItemUseCooldown(int itemUseCooldown);

}
