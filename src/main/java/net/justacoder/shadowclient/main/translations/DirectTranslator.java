package net.justacoder.shadowclient.main.translations;

public abstract class DirectTranslator {

    public static String translate(String key) {
        Language language = Translations.getLanguage();
        return language == null ? key : language.getTranslationFor(key);
    }

}
