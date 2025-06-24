package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class ExtendedCameraDistance extends Module {

    public NumberSetting DISTANCE = new NumberSetting(TranslatableString.of("setting.module.shadowclient.extcamdistance.distance"), -5, 50, 10, 1);

    public ExtendedCameraDistance() {
        super("extcamdistance", ModuleCategory.RENDER, new String[]{"extended camera distance", "extcamdistance", "cam distance", "bigger camera distance"});
        addSetting(DISTANCE);
    }

    public float getDistance() {
        return DISTANCE.floatValueEased();
    }
}
