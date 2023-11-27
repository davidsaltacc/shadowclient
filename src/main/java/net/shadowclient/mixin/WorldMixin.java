package net.shadowclient.mixin;

import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.render.WeatherControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess {
    @Inject(at = @At("HEAD"), method = "getRainGradient", cancellable = true)
    private void onGetRainGradient(float delta, CallbackInfoReturnable<Float> cir) {
        if (ModuleManager.WeatherControlModule.rainDisabled()) {
            cir.setReturnValue(0f);
        }
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
