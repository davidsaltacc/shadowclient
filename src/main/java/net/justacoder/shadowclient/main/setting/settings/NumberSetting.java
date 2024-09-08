package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.util.MathUtils;

public class NumberSetting extends Setting {

    public final int decimalPlaces;
    public final MathUtils.Easing easing;

    protected Number value;
    protected Number minValue;
    protected Number maxValue;

    public Number getMinValue() {
        return minValue;
    }
    public void setMinValue(Number minValue) {
        this.minValue = minValue;
    }
    public Number getMaxValue() {
        return maxValue;
    }
    public void setMaxValue(Number maxValue) {
        this.maxValue = maxValue;
    }

    public Number numberValue() {
        return MathUtils.easedSliderValue(value.doubleValue(), minValue.doubleValue(), maxValue.doubleValue(), easing);
    }
    public Number numberValueUneased() {
        return value;
    }
    public int intValue() {
        return numberValue().intValue();
    }
    public long longValue() {
        return numberValue().longValue();
    }
    public float floatValue() {
        return numberValue().floatValue();
    }
    public double doubleValue() {
        return numberValue().doubleValue();
    }

    public void setNumberValue(Number value) {
        if (value.floatValue() > maxValue.floatValue()) {
            this.value = maxValue;
            return;
        }
        if (value.floatValue() < minValue.floatValue()) {
            this.value = minValue;
            return;
        }
        this.value = value;
        callCallbacks();
    }
    public void setIntValue(int value) {
        if (value > (int) maxValue) {
            this.value = maxValue;
            return;
        }
        if (value < (int) minValue) {
            this.value = minValue;
            return;
        }
        this.value = value;
        callCallbacks();
    }
    public void setLongValue(long value) {
        if (value > (long) maxValue) {
            this.value = maxValue;
            return;
        }
        if (value < (long) minValue) {
            this.value = minValue;
            return;
        }
        this.value = value;
        callCallbacks();
    }
    public void setFloatValue(float value) {
        if (value > (float) maxValue) {
            this.value = maxValue;
            return;
        }
        if (value < (float) minValue) {
            this.value = minValue;
            return;
        }
        this.value = value;
        callCallbacks();
    }
    public void setDoubleValue(double value) {
        if (value > (double) maxValue) {
            this.value = maxValue;
            return;
        }
        if (value < (double) minValue) {
            this.value = minValue;
            return;
        }
        this.value = value;
        callCallbacks();
    }

    public NumberSetting(String name, Number min, Number max, Number defaultValue, int decimalPlaces) {
        this(name, min, max, defaultValue, decimalPlaces, MathUtils.Easing.LINEAR);
    }

    public NumberSetting(String name, Number min, Number max, Number defaultValue, int decimalPlaces, MathUtils.Easing easing) {
        super(name);
        this.minValue = min;
        this.maxValue = max;
        this.value = defaultValue;
        this.decimalPlaces = decimalPlaces;
        this.easing = easing;
    }
}
