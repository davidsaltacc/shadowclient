package net.justacoder.shadowclient.main.ui;

import net.minecraft.util.math.random.Random;

import java.util.List;

public abstract class SplashTexts {

    private static final Random random = Random.create();

    public static final List<String> TEXTS = List.of(
            "better than §5meteor§r...",
            "star shadowclient on github",
            "wurst worst client",
            "shadowclient best client",
            "justacoder the §4§lGOAT§r",
            "good utility client",
            "§lTechnoblade never dies.§r",
            "No hacks, just utility!",
            "No utility, just hacks!",
            "One does not simply §lfind§r diamonds.",
            "§l§cAlt+F4§r for a surprise!",
            "It's not X-ray, it's called §ostrategy§r!",
            "Not yet consumed by brainrot",
            "You seem like a nice person.",
            "racism will NOT be tolerated",
            "First, we mine. Then we craft. LET'S MINECRAFT!!",
            "CHICKEN JOCKEY!!!!",
            "absolute cinema",
            "Cats are cool. Period.",
            "This statement is false",
            "This is a splash text.",
            "",
            "Why? WHYY????",
            "What is the purpose of our lives?",
            "Can be used to troll friends."
    );

    public static String getRandom() {
        return TEXTS.get(random.nextBetween(0, TEXTS.size() - 1));
    }

}
