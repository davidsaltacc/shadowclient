package net.shadowclient.main.module;

public enum ModuleCategory {
    COMBAT("Combat"),
    PLAYER("Player"),
    MOVEMENT("Movement"),
    // WORLD("World"),    // NO MODULES HERE, SO WE DON'T EVEN REGISTER IT
    RENDER("Render"),
    FUN("Fun"),
    OTHER("Other"),
    MENUS("Menus");

    public final String name;

    ModuleCategory(String name) {
        this.name = name;
    }
}
