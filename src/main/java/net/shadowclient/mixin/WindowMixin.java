package net.shadowclient.mixin;

import net.minecraft.client.util.Window;
import net.shadowclient.main.SCMain;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Window.class)
public abstract class WindowMixin {
    /** mixin, why
     * @author e
     * @reason e
     */
    @Overwrite
    public void setTitle(String title) { // todo
        GLFW.glfwSetWindowTitle(((Window) (Object) this).getHandle(), SCMain.getWindowTitle() + " | " + title);
    }
}