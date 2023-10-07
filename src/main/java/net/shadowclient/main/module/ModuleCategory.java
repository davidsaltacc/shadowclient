package net.shadowclient.main.module;

public enum ModuleCategory {
    COMBAT("Combat"),
    PLAYER("Player"),
    MOVEMENT("Movement"),
    // WORLD("World"),
    RENDER("Render"),
    OTHER("Other"),
    FUN("Fun");

    public final String name;

    ModuleCategory(String name) {
        this.name = name;
    }
}
