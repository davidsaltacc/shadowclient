package net.justacoder.shadowclient.main.translations;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Translations {

    public static final List<String> languages = List.of(
            "en_us", "de_de"
    );

    private static @Nullable Language language = null;

    public static void reload(String languageCode) {
        language = languages.contains(languageCode) ? new Language(languageCode) :
                (languages.contains("en_us") ? new Language("en_us") : null);
        TranslatableString.reloadAll();
    }

    public static @Nullable Language getLanguage() {
        return language;
    }

    public static String getTranslation(String key) {
        return DirectTranslator.translate(key);
    }

}
