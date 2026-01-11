package net.justacoder.shadowclient.main.ui.animation;

import net.justacoder.shadowclient.main.util.MathUtils;

public class AnimationStateContainer {

    public void restartAnimation() {
        setAnimProgressLinear(animProgressesUp() ? 0 : 1);
    }
    public void endAnimation() {
        setAnimProgressLinear(animProgressesUp() ? 1 : 0);
    }

    public void progressAnimation(float delta) {
        if (getAnimProgressLinear() != (animProgressesUp() ? 1 : 0)) {
            setAnimProgressLinear(Math.clamp(getAnimProgressLinear() + (animProgressesUp() ? 1 : -1) * (delta / 20.) / getAnimDuration(), 0, 1));
        }
    }

    private double animProgress;
    private double animDuration;
    private boolean rises;
    private MathUtils.Easing easing;

    public AnimationStateContainer(double duration, boolean risesByDefault) {
        this(duration, risesByDefault, MathUtils.Easing.LINEAR);
    }

    public AnimationStateContainer(double duration, boolean risesByDefault, MathUtils.Easing easing) {
        this.animDuration = duration;
        this.rises = risesByDefault;
        this.easing = easing;
        endAnimation();
    }

    public void setAnimProgressLinear(double progress) {
        this.animProgress = progress;
    }

    public double getAnimProgressLinear() {
        return animProgress;
    }

    // allowInverse would apply the easing correctly when the animation progress is sinking, but causes issues when inverting rising/sinking while progress not at 0 or 1
    public double getAnimProgressEased(boolean allowInverseEasing) {
        if (allowInverseEasing) {
            return rises ?
                    easing.eased(animProgress) :
                    1 - easing.eased(1 - animProgress);
        } else {
            return easing.eased(animProgress);
        }
    }

    public double getAnimProgressEased() {
        return getAnimProgressEased(false);
    }

    public double getAnimDuration() {
        return animDuration;
    }

    public boolean animProgressesUp() {
        return rises;
    }

    public void setAnimProgressesUp(boolean up) {
        rises = up;
    }

}