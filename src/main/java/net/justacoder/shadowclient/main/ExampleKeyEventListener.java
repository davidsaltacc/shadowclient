package net.justacoder.shadowclient.main;

import net.justacoder.shadowclient.main.events.KeyEvent;

public class ExampleKeyEventListener {

    public static void init() {
        KeyEvent.subscribe(ExampleKeyEventListener::onKey);
    }

    public static void onKey(KeyEvent event) {
        SCMain.info("key pressed wooooo {}", (char) event.keyCode);
    }

}
