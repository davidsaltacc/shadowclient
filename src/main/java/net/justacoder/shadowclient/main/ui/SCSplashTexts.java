package net.justacoder.shadowclient.main.ui;

import net.minecraft.util.math.random.Random;

import java.util.List;

public abstract class SCSplashTexts {

    private static final Random random = Random.create();

    public static final List<String> TEXTS = List.of(
            "better than §5meteor§r...",
            "star shadowclient on github",
            "wurst worst client",
            "shadowclient best client",
            "justacoder the §4§lGOAT§r",
            "good utility client",
            "join the §1community§r!",
            "funny splash text here",
            "§lTechnoblade never dies!§r",
            "No hacks, just utility!",
            "No utility, just hacks!",
            "One does not simply §lfind§r diamonds.",
            "§l§cAlt+F4§r for a surprise!",
            "One block at a time!",
            "Unleash your inner Enderman!",
            "It's not X-ray, it's §ostrategy§r!",
            "You can't sleep here, monsters nearby!",
            "The cake is a lie!"
    );

    public static String getRandom() {
        return TEXTS.get(random.nextBetween(0, TEXTS.size() - 1));
    }

}
