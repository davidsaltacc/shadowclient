package net.justacoder.shadowclient.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DrawContext.class)
public interface DrawContextAccessor {

    @Accessor("vertexConsumers")
    public VertexConsumerProvider.Immediate getVertexConsumers();

}
