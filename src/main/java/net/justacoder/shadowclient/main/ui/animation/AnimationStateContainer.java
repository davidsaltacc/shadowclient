package net.justacoder.shadowclient.main.ui.animation;

public class AnimationStateContainer implements Animatable { // it is preferred to implement animatable directly. this is used if multiple separate animations need to be played in a single class

    private double animProgress = 1;
    private final double animDuration;
    private boolean rises;

    public AnimationStateContainer(double duration) {
        this.animDuration = duration;
    }

    @Override
    public void setAnimProgress(double progress) {
        this.animProgress = progress;
    }

    @Override
    public double getAnimProgress() {
        return animProgress;
    }

    @Override
    public double getAnimDuration() {
        return animDuration;
    }

    @Override
    public boolean animProgressesUp() {
        return rises;
    }

    @Override
    public void setAnimProgressesUp(boolean up) {
        rises = up;
    }

}