package net.justacoder.shadowclient.main.setting.settings;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.translations.TranslatableString;

public class PaddingSetting extends Setting {
    public PaddingSetting() {
        super(TranslatableString.of("name.shadowclient.empty"));
    }

    @Override
    public void reset() {}
    // TODO yeah we are going to need to make a better system for registering a setting UI. what the fuck do you mean "padding setting" (empty setting that adds padding)??????????
}
