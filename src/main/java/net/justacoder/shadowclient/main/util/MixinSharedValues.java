package net.justacoder.shadowclient.main.util;

import net.minecraft.client.util.math.MatrixStack;

public abstract class MixinSharedValues { // public static members are not allowed in mixins

    public static boolean ignoreSolidBlocksRaycasting = false;

    public static MatrixStack worldRendererStack;
    public static float worldRendererDelta;

}
