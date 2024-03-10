package net.justacoder.shadowclient.main.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class MathUtils {
    public static double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }
        BigDecimal bdec = new BigDecimal(value);
        bdec = bdec.setScale(place, RoundingMode.HALF_UP);
        return bdec.doubleValue();
    }
}
