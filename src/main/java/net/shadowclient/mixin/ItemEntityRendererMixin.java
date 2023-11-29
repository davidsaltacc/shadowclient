package net.shadowclient.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.RotationAxis;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.module.ModuleManager;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin {

    @Unique public ItemEntity item;
    @Unique public final Quaternionf flat_rotation = RotationAxis.POSITIVE_X.rotation(1.57079633f);

    @Inject(method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void onRender(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        item = itemEntity;
    }

    @Redirect(method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", ordinal = 0))
    private void onTranslate(MatrixStack matrices, float x, float y, float z) {
        if (!ModuleManager.FlatItemsModule.enabled) {
            matrices.translate(x, y, z);
        }
    }

    @Redirect(method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V", ordinal = 0))
    private void onRotate(MatrixStack matrices, Quaternionf quaternion) {
        if (ModuleManager.FlatItemsModule.enabled) {
            float offset = item.uniqueOffset;
            if (ModuleManager.FlatItemsModule.face()) {
                offset = ModuleManager.FlatItemsModule.getItemAngle(item, SCMain.mc.player);
            }
            matrices.multiply(flat_rotation);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(offset));
        } else {
            matrices.multiply(quaternion);
        }
    }
}
