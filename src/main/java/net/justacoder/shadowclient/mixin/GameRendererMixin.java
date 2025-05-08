package net.justacoder.shadowclient.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.justacoder.shadowclient.main.util.MixinSharedValues;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.mixininterface.IGameRenderer;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin implements IGameRenderer {

    @Shadow private void setPostProcessor(Identifier id) {}
    @Shadow private static HitResult ensureTargetInRange(HitResult hitResult, Vec3d cameraPos, double interactionRange) { return null; }
    @Shadow @Final private MinecraftClient client;

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void loadShader(@Nullable Identifier id) {
        if (id != null) {
            setPostProcessor(id);
        } else {
            ((GameRenderer) (Object) this).clearPostProcessor();
        }
    }

    @Inject(at = @At("HEAD"), method = "tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V", cancellable = true)
    private void onTiltViewWhenHurt(MatrixStack matrixStack, float f, CallbackInfo ci) {
        if (ModuleManager.NoTiltOnHurtModule.enabled) {
            ci.cancel();
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0), method = "renderWorld")
    private float nauseaLerp(float delta, float start, float end) {
        if (ModuleManager.NoWobbleModule.enabled) {
            return 0;
        }

        return MathHelper.lerp(delta, start, end);
    }

    @Inject(at = @At("HEAD"), method = "getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F", cancellable = true)
    private static void onGetNightVisionStrength(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Float> cir) {
        if (ModuleManager.NightVisionModule.enabled || ModuleManager.XrayModule.enabled) {
            cir.setReturnValue(1f);
        }
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void renderHand(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci) {
        if (ModuleManager.FreecamModule.enabled) {
            ci.cancel();
        }
    }

    @Inject(method = "getFov", at = @At("HEAD"), cancellable = true)
    private void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Float> cir) {
        if (ModuleManager.ZoomModule.enabled) {
            cir.setReturnValue(ModuleManager.ZoomModule.FOV.floatValue());
        }
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void bobView(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (ModuleManager.NoBobModule.enabled) {
            ci.cancel();
        }
    }

    @Redirect(method = "findCrosshairTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;raycast(DFZ)Lnet/minecraft/util/hit/HitResult;"))
    private HitResult changeCrosshairTarget(Entity camEntity, double maxDistance, float tickDelta, boolean includeFluids) {
        if (ModuleManager.WallInteractModule.enabled && !(ModuleManager.WallInteractModule.disableOnSneak.booleanValue() && client.player.isSneaking())) {
            MixinSharedValues.ignoreSolidBlocksRaycasting = true;
            HitResult result = camEntity.raycast(maxDistance, tickDelta, includeFluids);
            MixinSharedValues.ignoreSolidBlocksRaycasting = false;
            return result;
        }
        return camEntity.raycast(maxDistance, tickDelta, includeFluids);
    }

    @Inject(method = "findCrosshairTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;", shift = At.Shift.AFTER), cancellable = true)
    private void changeCrosshairTarget2(Entity camera, double blockInteractionRange, double entityInteractionRange, float tickDelta, CallbackInfoReturnable<HitResult> cir, @Local HitResult hitResult, @Local(ordinal = 0) Vec3d vec3d, @Local(ordinal = 2) Vec3d vec3d3, @Local Box box) {
        EntityHitResult entityHitResult = ProjectileUtil.raycast(camera, vec3d, vec3d3, box, EntityPredicates.CAN_HIT, MathHelper.square(Math.max(blockInteractionRange, entityInteractionRange)));
        if (ModuleManager.WallInteractModule.enabled && !(ModuleManager.WallInteractModule.disableOnSneak.booleanValue() && client.player.isSneaking()) && hitResult.getType() == HitResult.Type.MISS && entityHitResult == null) {
            cir.setReturnValue(ensureTargetInRange(camera.raycast(Math.max(blockInteractionRange, entityInteractionRange), tickDelta, false), camera.getCameraPosVec(tickDelta), blockInteractionRange));
        }
    }

}
