package net.justacoder.shadowclient.main.keybinds;

public class Key {

    private int keyCode;
    private int scanCode;
    private int modifiers;

    public Key(int keyCode, int scanCode, int modifiers) {
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.modifiers = modifiers;
    }

    public Key(int keyCode) {
        this.keyCode = keyCode;
        this.scanCode = -1;
        this.modifiers = 0;
    }

    public int getScanCode() {
        return scanCode;
    }

    public void setScanCode(int scanCode) {
        this.scanCode = scanCode;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public boolean matches(Key other) {
        return matches(other, false);
    }

    public boolean matches(Key other, boolean matchModifiers) {
        if (other == null) {
            return false;
        }
        return matchModifiers ?
                keyCode == other.keyCode && modifiers == other.modifiers :
                keyCode == other.keyCode;
    }

}