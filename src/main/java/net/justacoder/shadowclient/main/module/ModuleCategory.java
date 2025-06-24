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
    
    SEARCH("search", true); // not really module categories


    public final String id;
    public TranslatableString name;
    public final boolean hiddenFromMain;

    ModuleCategory(String id) {
        this(id, false);
    }

    ModuleCategory(String id, boolean hiddenFromMain) {
        this.id = id;
        this.name = TranslatableString.of("category.module.shadowclient." + id);
        this.hiddenFromMain = hiddenFromMain;
    }
}
