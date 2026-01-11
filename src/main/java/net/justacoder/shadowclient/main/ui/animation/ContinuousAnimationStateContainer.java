package net.justacoder.shadowclient.main.ui.animation;

public abstract class ContinuousAnimationStateContainer {

    public static class Looping extends AnimationStateContainer implements ContinuousAnimatable.Looping {

        public Looping(double duration) {
            super(duration);
        }

    }

    public static class BackAndForth extends AnimationStateContainer implements ContinuousAnimatable.BackAndForth {

        public BackAndForth(double duration) {
            super(duration);
        }

    }

}