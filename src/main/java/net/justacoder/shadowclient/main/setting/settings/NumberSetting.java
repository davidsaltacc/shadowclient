package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.util.JavaUtils;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.justacoder.shadowclient.main.config.hardcoded.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

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

    public Number numberValueEased() {
        return MathUtils.easedSliderValue(value.doubleValue(), minValue.doubleValue(), maxValue.doubleValue(), easing);
    }
    public Number numberValue() {
        return value;
    }
    public int intValueEased() {
        return numberValueEased().intValue();
    }
    public long longValueEased() {
        return numberValueEased().longValue();
    }
    public float floatValueEased() {
        return numberValueEased().floatValue();
    }
    public double doubleValueEased() {
        return numberValueEased().doubleValue();
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
        if (Settings.DEBUG_MODE) {
            ShadowClientMain.info("number setting " + name + " was changed to " + value + " by " + String.join(" -> ", JavaUtils.getStackStrings()));
        }
        Number old = this.value;
        Number newValue = MathUtils.clamp(value, minValue, maxValue);
        this.value = newValue;
        callCallbacks(newValue, old);
    }
    public void setNumberValue(Number value, boolean callCallbacksImmediatly) {
        if (Settings.DEBUG_MODE) {
            ShadowClientMain.info("number setting " + name + " was changed to " + value + " by " + String.join(" -> ", JavaUtils.getStackStrings()));
        }
        Number old = this.value;
        Number newValue = MathUtils.clamp(value, minValue, maxValue);
        this.value = newValue;
        if (callCallbacksImmediatly) {
            callCallbacks(newValue, old);
        }
    }
    public void setIntValue(int value) {
        setNumberValue(value);
    }
    public void setLongValue(long value) {
        setNumberValue(value);
    }
    public void setFloatValue(float value) {
        setNumberValue(value);
    }
    public void setDoubleValue(double value) {
        setNumberValue(value);
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

    public List<BiConsumer<Object, Object>> onFinishCallbacks = new ArrayList<>();

    public void addOnFinishCallback(BiConsumer<Object, Object> cb) {
        onFinishCallbacks.add(cb);
    }

    public void callFinishCallbacks(Object newValue, Object oldValue) {
        if (getShouldCallCallbacks()) {
            onFinishCallbacks.forEach(cb -> cb.accept(newValue, oldValue));
        }
    }

}
