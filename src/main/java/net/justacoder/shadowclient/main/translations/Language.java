package net.justacoder.shadowclient.main.translations;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.util.JavaUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Language {

    public final String languageCode;
    private final Map<String, String> keyToTranslation;

    public Language(String languageCode) {

        this.languageCode = languageCode;
        this.keyToTranslation = new HashMap<>(512);

        try {

            String data;

            try (InputStream stream = Language.class.getResourceAsStream("/assets/shadowclient/lang/" + languageCode + ".json")) {
                data = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            }

            JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
            jsonObject.asMap().forEach((key, value) -> keyToTranslation.put(key, value.getAsString()));

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize language " + this.languageCode + ": " + JavaUtils.stackTraceFromThrowable(e));
        }

    }

    public String getTranslationFor(String key) {
        return this.keyToTranslation.getOrDefault(key, key);
    }

}
