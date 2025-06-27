package net.justacoder.shadowclient.main.ui.animation;

import net.minecraft.client.MinecraftClient;

public interface Animatable {

    default void startAnimation() {
        setAnimProgress(animProgressesUp() ? 0 : 1);
    }

    default void progressAnimation() {
        if (getAnimProgress() != (animProgressesUp() ? 1 : 0)) {
            setAnimProgress(Math.clamp(getAnimProgress() + (animProgressesUp() ? 1 : -1) * (MinecraftClient.getInstance().getRenderTickCounter().getLastDuration() / 10) / getAnimDuration(), 0, 1)); // WHY /10
        }
    }

    void setAnimProgress(double progress);
    double getAnimProgress();
    double getAnimDuration();
    boolean animProgressesUp();
    void setAnimProgressesUp(boolean up);

}
