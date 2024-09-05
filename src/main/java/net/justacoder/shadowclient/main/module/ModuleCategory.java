package net.justacoder.shadowclient.main.module;

import net.minecraft.client.resource.language.I18n;

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


    public final String name;
    public String friendlyName;
    public final boolean hiddenFromMain;

    public void reloadTranslations() {
        this.friendlyName = I18n.translate("category.module.shadowclient." + name);
    }

    ModuleCategory(String name) {
        this.name = name;
        this.friendlyName = "";
        this.hiddenFromMain = false;
    }


    ModuleCategory(String name, boolean hiddenFromMain) {
        this.name = name;
        this.friendlyName = "";
        this.hiddenFromMain = hiddenFromMain;
    }
}
