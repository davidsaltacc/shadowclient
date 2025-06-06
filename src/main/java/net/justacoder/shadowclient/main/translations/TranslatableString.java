package net.justacoder.shadowclient.main.translations;

import net.minecraft.client.resource.language.I18n;

import java.util.ArrayList;
import java.util.List;

public class TranslatableString {

    private static final List<TranslatableString> translatableStrings = new ArrayList<>();

    String key;
    String translation;

    public TranslatableString(String key) {
        this(key, true);
    }

    public TranslatableString(String key, boolean pregenerateTranslation) {
        this.key = key;
        if (pregenerateTranslation) {
            reload();
        }
        translatableStrings.add(this);
    }

    public static void removeString(TranslatableString instance) {
        translatableStrings.remove(instance);
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
        translatableStrings.forEach(TranslatableString::reload);
    }

    public void setKey(String key) {
        this.key = key;
        reload();
    }

    public static class VanillaBacked extends TranslatableString {

        public VanillaBacked(String key) {
            super(key);
        }

        @Override
        public void reload() {
            this.translation = I18n.translate(key);
        }
    }

}
