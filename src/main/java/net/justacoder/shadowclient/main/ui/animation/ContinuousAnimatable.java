package net.justacoder.shadowclient.main.ui.animation;

public abstract class ContinuousAnimatable {

    public interface Looping extends Animatable {
        @Override
        default void progressAnimation() {
            Animatable.super.progressAnimation();
            if (getAnimProgress() == 0 && !animProgressesUp()) {
                setAnimProgress(1);
            } else if (getAnimProgress() == 1 && animProgressesUp()) {
                setAnimProgress(0);
            }
        }
    }

    public interface BackAndForth extends Animatable {
        @Override
        default void progressAnimation() {
            Animatable.super.progressAnimation();
            if (getAnimProgress() == 1 && animProgressesUp()) {
                setAnimProgressesUp(false);
            } else if (getAnimProgress() == 0 && !animProgressesUp()) {
                setAnimProgressesUp(true);
            }
        }
    }

}
