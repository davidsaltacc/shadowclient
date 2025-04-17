package net.justacoder.shadowclient.main.event.events;

import net.justacoder.shadowclient.main.event.Event;

public class GetAmbientOcclusionLightLevelEvent extends Event {
    public boolean set = false;
    public int lightLevel;

    public void setLightLevel(int l) {
        lightLevel = l;
        set = true;
    }

    public void unsetLightLevel() {
        set = false;
    }
}
