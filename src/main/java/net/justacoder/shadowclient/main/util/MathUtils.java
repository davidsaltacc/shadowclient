package net.justacoder.shadowclient.main.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

public abstract class MathUtils {

    public static double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }
        BigDecimal bdec = BigDecimal.valueOf(value);
        bdec = bdec.setScale(place, RoundingMode.HALF_UP);
        return bdec.doubleValue();
    }

    public static double easedSliderValue(double value, double min, double max, Easing easing) {
        double t = (value - min) / (max - min);
        return min + easing.eased(t) * (max - min);
    }

    public static double square(double x) {
        return x * x;
    }
    public static double cube(double x) {
        return x * x * x;
    }

    public enum Easing {

        LINEAR(x -> x),
        EASE_IN_QUADRATIC(x -> x * x),
        EASE_IN_CUBIC(x -> x * x * x),
        EASE_OUT_QUADRATIC(x -> 1 - square(1 - x)),
        EASE_OUT_CUBIC(x -> 1 - cube(1 - x)),
        EASE_IN_OUT_QUADRATIC(x -> x < 0.5 ? 2 * x * x : 1 - square(-2 * x + 2) / 2),
        EASE_IN_OUT_CUBIC(x -> x < 0.5 ? 4 * x * x * x : 1 - cube(-2 * x + 2) / 2);

        private final UnaryOperator<Double> easingFunction;

        Easing(UnaryOperator<Double> easingFunction) {
            this.easingFunction = easingFunction;
        }

        public double eased(double value) {
            return easingFunction.apply(value);
        }

    }

}
