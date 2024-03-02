package net.justacoder.shadowclient.main.setting;

import java.util.ArrayList;
import java.util.List;

public abstract class Setting {

    public final String name;

    private Number value;
    private Number minValue;

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

    private Number maxValue;

    private boolean boolValue;

    public Number numberValue() {
        return value;
    }
    public int intValue() {
        return (int) value.doubleValue();
    }
    public long longValue() {
        return (long) value.doubleValue();
    }
    public float floatValue() {
        return (float) value.doubleValue();
    }
    public double doubleValue() {
        return value.doubleValue();
    }
    public boolean booleanValue() {
        return boolValue;
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
    public void setBooleanValue(boolean value) {
        this.boolValue = value;
        callCallbacks();
    }

    public Setting(String name, Number min, Number max, Number defaultValue) {
        minValue = min;
        maxValue = max;
        value = defaultValue;
        this.name = name;
    }
    public Setting(String name, boolean defaultValue) {
        boolValue = defaultValue;
        this.name = name;
    }
    public Setting(String name) {
        this.name = name;
    }

    public List<Runnable> callbacks = new ArrayList<>();
    private boolean callCallbacks = true;
    public void addChangeCallback(Runnable cb) {
        callbacks.add(cb);
    }
    public void callCallbacks() {
        if (callCallbacks) {
            callbacks.forEach(Runnable::run);
        }
    }
    public void shouldCallCallbacks(boolean call) {
        callCallbacks = call;
    }
}

