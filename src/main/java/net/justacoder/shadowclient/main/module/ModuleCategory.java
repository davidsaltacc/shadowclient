package net.justacoder.shadowclient.main.module;

import net.justacoder.shadowclient.main.translations.TranslatableString;

public enum ModuleCategory {
    COMBAT("combat"),
    PLAYER("player"),
    MOVEMENT("movement"),
    WORLD("world"),
    RENDER("render"),
    FUN("fun"),
    OTHER("other"),
    MENUS("menus"),


    SETTINGS("settings", true),
    OPTIONS("options", true),   // not really module categories
    SEARCH("search", true);


    public final String id;
    public TranslatableString name;
    public final boolean hiddenFromMain;

    ModuleCategory(String id) {
        this(id, false);
    }

    ModuleCategory(String id, boolean hiddenFromMain) {
        this.id = id;
        this.name = new TranslatableString("category.module.shadowclient." + id);
        this.hiddenFromMain = hiddenFromMain;
    }
}
