package net.justacoder.shadowclient.main.util;

import net.minecraft.util.math.MathHelper;

public class ColorUtils {

    public static int RGB2int(int r, int g, int b) {
        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) + (b & 0xFF);
    }

    public static int RGBA2int(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) + ((g & 0xFF) << 8) | (b & 0xFF);
    }

    public static int[] int2RGBA(int color) {
        return new int[]{(color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF};
    }

    public static int[] int2RGB(int color) {
        return new int[]{(color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF};
    }

    public static int[] RGBFloatToRGBInt(float r, float g, float b) {
        int[] color = new int[3];

        color[0] = Math.min(Math.max((int) (r * 255), 0), 255);
        color[1] = Math.min(Math.max((int) (g * 255), 0), 255);
        color[2] = Math.min(Math.max((int) (b * 255), 0), 255);

        return color;
    }

    public static float[] RGBIntToRGBFloat(int r, int g, int b) {
        return new float[]{(float) r / 255, (float) g / 255, (float) b / 255};
    }

    public static float[] rainbowColor() {
        float x = System.currentTimeMillis() % 2000 / 1000F;
        float pi = (float) Math.PI;

        float[] rainbow = new float[3];
        rainbow[0] = 0.5F + 0.5F * MathHelper.sin(x * pi);
        rainbow[1] = 0.5F + 0.5F * MathHelper.sin((x + 4F / 3F) * pi);
        rainbow[2] = 0.5F + 0.5F * MathHelper.sin((x + 8F / 3F) * pi);

        return rainbow;
    }

}
