package net.justacoder.shadowclient.main.util;

import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MiscUtils {

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

}
