package net.shadowclient.mixininterface;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface IMinecraftClient {
    @Accessor("itemUseCooldown")
    public void setItemUseCooldown(int itemUseCooldown);
}
