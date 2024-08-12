package net.justacoder.shadowclient.main.util;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonParseException;
import net.justacoder.shadowclient.main.SCMain;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.TextVisitFactory;
import net.minecraft.util.Language;

import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class LanguageUtils {

    private static Language instance;

    public static Language create() {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        BiConsumer<String, String> biConsumer = builder::put;
        LanguageUtils.load(biConsumer, "/assets/shadowclient/lang/en_us.json");
        final ImmutableMap<String, String> map = builder.build();
        return new Language(){
            @Override
            public String get(String key, String fallback) {
                return map.getOrDefault(key, fallback);
            }
            @Override
            public boolean hasTranslation(String key) {
                return map.containsKey(key);
            }
            @Override
            public boolean isRightToLeft() {
                return false;
            }
            @Override
            public OrderedText reorder(StringVisitable text) {
                return visitor -> text.visit((style, string) -> TextVisitFactory.visitFormatted(string, style, visitor) ? Optional.empty() : StringVisitable.TERMINATE_VISIT, Style.EMPTY).isPresent();
            }
        };
    }

    public static void setInstance(Language lang) {
        instance = lang;
    }

    private static void load(BiConsumer<String, String> entryConsumer, String path) {
        try (InputStream inputStream = Language.class.getResourceAsStream(path)){
            Language.load(inputStream, entryConsumer);
        } catch (JsonParseException | IOException e) {
            SCMain.error("Could not load language file: ");
            SCMain.error(JavaUtils.stackTraceFromThrowable(e));
        }
    }

    public static Language getInstance() {
        return instance;
    }

    public static String translate(String key, Object ... args) {
        String string = instance.get(key);
        try {
            return String.format(string, args);
        } catch (IllegalFormatException illegalFormatException) {
            return "Format error: " + string;
        }
    }

    public static boolean hasTranslation(String key) {
        return instance.hasTranslation(key);
    }

}
