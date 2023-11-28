package net.shadowclient.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.world.WeatherControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World implements WorldAccess {

    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DynamicRegistryManager registryManager, RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient, debugWorld, biomeAccess, maxChainedNeighborUpdates);
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

}
