package net.justacoder.shadowclient.main.ui.animation;

public abstract class ContinuousAnimationStateContainer {

    public static class Looping extends AnimationStateContainer {

        public Looping(double duration, boolean startHigh) {
            super(duration, startHigh);
        }

        @Override
        public void progressAnimation(float delta) {
            super.progressAnimation(delta);
            if (getAnimProgressLinear() == 0 && !animProgressesUp()) {
                setAnimProgressLinear(1);
            } else if (getAnimProgressLinear() == 1 && animProgressesUp()) {
                setAnimProgressLinear(0);
            }
        }

    }

    public static class BackAndForth extends AnimationStateContainer {

        public BackAndForth(double duration, boolean startHigh) {
            super(duration, startHigh);
        }

        @Override
        public void progressAnimation(float delta) {
            super.progressAnimation(delta);
            if (getAnimProgressLinear() == 1 && animProgressesUp()) {
                setAnimProgressesUp(false);
            } else if (getAnimProgressLinear() == 0 && !animProgressesUp()) {
                setAnimProgressesUp(true);
            }
        }

    }

}