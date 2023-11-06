package net.shadowclient.main.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class JavaUtils {
    public static String stackTraceFromThrowable(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
