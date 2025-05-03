package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.ShadowClientMain;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class JavaUtils {

    public static List<StackWalker.StackFrame> getStack() {
        return StackWalker.getInstance().walk(Stream::toList);
    }

    public static List<String> getStackStrings() {
        List<StackWalker.StackFrame> stackElements = JavaUtils.getStack();
        List<String> stackStrings = new ArrayList<>();
        stackElements.forEach(frame -> stackStrings.add(frame.getClassName() + "." + frame.getMethodName()));
        return stackStrings;
    }

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
                rt.exec(new String[]{ "rundll32", "url.dll,FileProtocolHandler", url });
                return;
            }
            if (getOs() == OS.MAC) {
                Runtime rt = Runtime.getRuntime();
                rt.exec(new String[]{ "open", url });
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

            ShadowClientMain.error("Operating System not found, could not open web browser.");

        } catch (Exception e) {
            ShadowClientMain.error("Error opening web browser: \n" + JavaUtils.stackTraceFromThrowable(e));
        }
    }
}
