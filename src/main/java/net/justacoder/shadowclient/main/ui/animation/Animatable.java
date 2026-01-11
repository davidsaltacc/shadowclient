package net.justacoder.shadowclient.main.ui.animation;

public interface Animatable {

    default void startAnimation() {
        setAnimProgress(animProgressesUp() ? 0 : 1);
    }

    default void progressAnimation(float delta) {
        if (getAnimProgress() != (animProgressesUp() ? 1 : 0)) {
            setAnimProgress(Math.clamp(getAnimProgress() + (animProgressesUp() ? 1 : -1) * delta / getAnimDuration(), 0, 1)); // TODO if animation durations are fucked up, i just replaced whatever was there with "delta", that may be the issue
        }
    }

    void setAnimProgress(double progress);
    double getAnimProgress();
    double getAnimDuration();
    boolean animProgressesUp();
    void setAnimProgressesUp(boolean up);

}