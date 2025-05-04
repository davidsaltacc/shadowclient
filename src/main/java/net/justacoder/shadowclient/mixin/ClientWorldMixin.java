package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.util.MixinSharedValues;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionType;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.module.modules.world.WeatherControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World implements WorldAccess {

    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Inject(method = "getBlockParticle", at = @At("HEAD"), cancellable = true)
    private void onGetBlockParticle(CallbackInfoReturnable<Block> cir) {
        if (ModuleManager.RenderBarriersModule.enabled) {
            cir.setReturnValue(Blocks.BARRIER);
        }
    }

    @Override
    public float getRainGradient(float delta) {
        if (ModuleManager.WeatherControlModule.rainDisabled()) {
            return 0f;
        }
        return super.getRainGradient(delta);
    }

    @Override
    public float getSkyAngle(float tickDelta) {
        WeatherControl weatherControl = ModuleManager.WeatherControlModule;

        long timeOfDay = weatherControl.timeChanged() ? weatherControl.getTime() : getLevelProperties().getTimeOfDay();

        return getDimension().getSkyAngle(timeOfDay);
    }

    @Override
    public int getMoonPhase() {
        WeatherControl weatherControl = ModuleManager.WeatherControlModule;

        if (weatherControl.moonChanged()) {
            return weatherControl.getMoon();
        }

        return getDimension().getMoonPhase(getLunarTime());
    }

    @Override
    public BlockHitResult raycast(RaycastContext context) {
        return BlockView.raycast(context.getStart(), context.getEnd(), context, (innerContext, pos) -> { // sadly can't just override a lambda, and injecting into an interface isn't supported either. a full override is necessary
            BlockState blockState = this.getBlockState(pos);
            FluidState fluidState = this.getFluidState(pos);
            if (MixinSharedValues.ignoreSolidBlocksRaycasting && !blockState.hasBlockEntity()) {
                return null;
            }
            Vec3d vec3d = innerContext.getStart();
            Vec3d vec3d2 = innerContext.getEnd();
            VoxelShape voxelShape = innerContext.getBlockShape(blockState, this, pos);
            BlockHitResult blockHitResult = this.raycastBlock(vec3d, vec3d2, pos, voxelShape, blockState);
            VoxelShape voxelShape2 = innerContext.getFluidShape(fluidState, this, pos);
            BlockHitResult blockHitResult2 = voxelShape2.raycast(vec3d, vec3d2, pos);
            double d = blockHitResult == null ? Double.MAX_VALUE : innerContext.getStart().squaredDistanceTo(blockHitResult.getPos());
            double e = blockHitResult2 == null ? Double.MAX_VALUE : innerContext.getStart().squaredDistanceTo(blockHitResult2.getPos());
            return d <= e ? blockHitResult : blockHitResult2;
        }, innerContext -> {
            Vec3d vec3d = innerContext.getStart().subtract(innerContext.getEnd());
            return BlockHitResult.createMissed(innerContext.getEnd(), Direction.getFacing(vec3d.x, vec3d.y, vec3d.z), BlockPos.ofFloored(innerContext.getEnd()));
        });
    }
}
