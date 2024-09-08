package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.util.MathUtils;

public class NumberSetting extends Setting {

    public final int decimalPlaces;
    public final MathUtils.Easing easing;

    public NumberSetting(String name, Number min, Number max, Number defaultValue, int decimalPlaces) {
        this(name, min, max, defaultValue, decimalPlaces, MathUtils.Easing.LINEAR);
    }

    public NumberSetting(String name, Number min, Number max, Number defaultValue, int decimalPlaces, MathUtils.Easing easing) {
        super(name, min, max, defaultValue);
        this.decimalPlaces = decimalPlaces;
        this.easing = easing;
    }

    @Override
    public Number numberValue() {
        return MathUtils.easedSliderValue(value.doubleValue(), minValue.doubleValue(), maxValue.doubleValue(), easing);
    }

    public Number numberValueUneased() {
        return value;
    }
}
