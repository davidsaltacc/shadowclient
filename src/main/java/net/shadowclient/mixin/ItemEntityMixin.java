package net.shadowclient.mixin;

import net.minecraft.entity.ItemEntity;
import net.shadowclient.mixininterface.IItemEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements IItemEntity {
    @Mutable
    @Shadow
    @Final
    public float uniqueOffset;

    @Override
    public void setUniqueOffset(float offset) {
        uniqueOffset = offset;
    }
}
