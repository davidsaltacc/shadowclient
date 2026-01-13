package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.SCMain;
import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class MiscUtils {

    public static List<StackWalker.StackFrame> getStack() {
        return StackWalker.getInstance().walk(Stream::toList);
    }

    public static List<String> getStackStrings() {
        List<StackWalker.StackFrame> stackElements = getStack();
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

    public static ByteBuffer resourceToByteBuffer(String resourcePath) throws IOException {
        InputStream in = MiscUtils.class.getResourceAsStream(resourcePath);
        assert in != null;
        byte[] data = in.readAllBytes();
        in.close();
        ByteBuffer direct = MemoryUtil.memAlloc(data.length);
        direct.put(data).flip();
        return direct;
    }

    public static int getScreenWidth() {
        return SCMain.mc.getWindow().getMonitor().findClosestVideoMode(SCMain.mc.getWindow().getFullscreenVideoMode()).getWidth();
    }

    public static int getScreenHeight() {
        return SCMain.mc.getWindow().getMonitor().findClosestVideoMode(SCMain.mc.getWindow().getFullscreenVideoMode()).getHeight();
    }

    public static byte[] compressGZIP(String input) throws IOException {

        if (input == null || input.isEmpty()) {
            return new byte[0];
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        GZIPOutputStream gzOs = new GZIPOutputStream(os);
        gzOs.write(input.getBytes(StandardCharsets.UTF_8));
        gzOs.close();

        return os.toByteArray();

    }

    public static String decompressGZIP(byte[] input) throws IOException {

        if (input == null || input.length == 0) {
            return "";
        }

        GZIPInputStream gzIs = new GZIPInputStream(new ByteArrayInputStream(input));
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(gzIs, StandardCharsets.UTF_8));

        StringBuilder output = new StringBuilder();
        String line;

        while ((line = bufReader.readLine()) != null) {
            output.append(line);
        }

        return output.toString();

    }

}
