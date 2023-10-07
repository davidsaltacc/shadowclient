package net.shadowclient.mixin;

import net.minecraft.client.util.Window;
import net.shadowclient.main.SCMain;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Window.class)
public abstract class WindowMixin {
    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(((Window) (Object) this).getHandle(), SCMain.getWindowTitle() + " | " + title);
    }
}