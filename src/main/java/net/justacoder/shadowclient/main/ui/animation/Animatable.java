package net.justacoder.shadowclient.main.ui.animation;

public interface Animatable {

    default void restartAnimation() {
        setAnimProgress(animProgressesUp() ? 0 : 1);
    }
    default void endAnimation() {
        setAnimProgress(animProgressesUp() ? 1 : 0);
    }

    default void progressAnimation(float delta) {
        if (getAnimProgress() != (animProgressesUp() ? 1 : 0)) {
            setAnimProgress(Math.clamp(getAnimProgress() + (animProgressesUp() ? 1 : -1) * (delta / 20.) / getAnimDuration(), 0, 1));
        }
    }

    void setAnimProgress(double progress);
    double getAnimProgress();
    double getAnimDuration();
    boolean animProgressesUp();
    void setAnimProgressesUp(boolean up);

}