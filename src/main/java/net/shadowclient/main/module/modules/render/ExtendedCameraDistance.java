package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;

@SearchTags({"extended camera distance", "extcamdistance", "cam distance", "bigger camera distance"})
public class ExtendedCameraDistance extends Module {

    public NumberSetting DISTANCE = new NumberSetting("Distance", -5, 50, 10, 1);

    public ExtendedCameraDistance() {
        super("extcamdistance", "Extended Cam Dist", "Distances your camera further away.", ModuleCategory.RENDER);
        addSetting(DISTANCE);
    }

    public float getDistance() {
        return DISTANCE.floatValue();
    }
}
