package net.justacoder.shadowclient.main.translations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TranslatableString { // "wait what do you mean minecraft already has this"

    private static final Map<String, TranslatableString> translatableStrings = new HashMap<>();

    String key;
    String translation;

    private TranslatableString(String key, boolean pregenerateTranslation) {
        this.key = key;
        if (pregenerateTranslation) {
            reload();
        }
    }

    public static TranslatableString of(String key, boolean pregenerateTranslation) {
        if (translatableStrings.containsKey(key)) {
            return translatableStrings.get(key);
        } else {
            TranslatableString string = new TranslatableString(key, pregenerateTranslation);
            translatableStrings.put(key, string);
            return string;
        }
    }

    public static TranslatableString of(String key) {
        return of(key, true);
    }

    public static void removeString(TranslatableString instance) {
        translatableStrings.remove(instance.getKey());
    }

    public String getKey() {
        return key;
    }

    public String getTranslation() {
        return translation;
    }

    public void reload() {
        this.translation = DirectTranslator.translate(key);
    }

    public static void reloadAll() {
        translatableStrings.forEach((s, translatableString) -> translatableString.reload());
    }

    public void setKey(String key) {
        this.key = key;
        reload();
    }

    @Override
    public String toString() {
        return "TranslatableString(\"" + key + "\")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.hashCode() == obj.hashCode();
    }
}
