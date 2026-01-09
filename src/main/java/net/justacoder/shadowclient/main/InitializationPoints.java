package net.justacoder.shadowclient.main;

import javax.swing.*;

public class InitializationPoints {

    public static void veryEarly() {

    }

    public static void fabricLaunch() {
        SCMain.init();
    }

    public static void main(String[] args) {

        JFrame parent = new JFrame();
        JLabel text = new JLabel();

        text.setText("<html>Launching the client like this is of no use. You need to install a fabric instance and put it in your mods folder.</html>");
        text.setFont(text.getFont().deriveFont(14f));

        parent.setTitle("ShadowClient Popup");
        parent.add(text);
        parent.setSize(400, 150);
        parent.setVisible(true);

    }

}
