package net.justacoder.shadowclient.mixin;

import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import net.justacoder.shadowclient.main.SCMain;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public abstract class WindowMixin {

    @Inject(method = "setTitle", at = @At("HEAD"), cancellable = true)
    public void setTitle(String title, CallbackInfo ci) {
        GLFW.glfwSetWindowTitle(((Window) (Object) this).getHandle(), SCMain.getWindowTitle() + " | " + title);
        ci.cancel();
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwMakeContextCurrent(J)V", shift = At.Shift.AFTER, remap = false))
    private void onceGLFWContextCurrent(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        SCMain.clickGui.repositionFramesProperly();
    }

}