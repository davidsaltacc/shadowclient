package net.shadowclient.main.util;

import net.shadowclient.main.SCMain;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;

public class JavaUtils {
    public static String stackTraceFromThrowable(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public enum OS {
        WIN, MAC, NIX, NOT_FOUND
    }

    public static OS getOs() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return OS.WIN;
        }
        if (os.contains("mac")) {
            return OS.MAC;
        }
        if (os.contains("nix") || os.contains("nux")) {
            return OS.NIX;
        }
        return OS.NOT_FOUND;
    }

    public static void openBrowser(String url) {
        try {
            if (getOs() == OS.WIN) {
                Runtime rt = Runtime.getRuntime();
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                return;
            }
            if (getOs() == OS.MAC) {
                Runtime rt = Runtime.getRuntime();
                rt.exec("open " + url);
                return;
            }
            if (getOs() == OS.NIX) {
                Runtime rt = Runtime.getRuntime();
                String[] browsers = { "google-chrome", "firefox", "mozilla", "epiphany", "konqueror", "netscape", "opera", "links", "lynx" };

                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++) {
                    if (i == 0) {
                        cmd.append(String.format("%s \"%s\"", browsers[i], url));
                    } else {
                        cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                    }
                }

                rt.exec(new String[] { "sh", "-c", cmd.toString() });
                return;
            }

            SCMain.error("Operating System not found, could not open web browser.");

        } catch (Exception e) {
            SCMain.error(JavaUtils.stackTraceFromThrowable(e));
        }
    }
}
