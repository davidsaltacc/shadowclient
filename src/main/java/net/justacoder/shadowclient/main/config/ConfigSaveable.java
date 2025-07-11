package net.justacoder.shadowclient.main.config;

import com.google.gson.JsonObject;

public interface ConfigSaveable {

    JsonObject writeConfig();
    void readConfig(JsonObject in);

}
