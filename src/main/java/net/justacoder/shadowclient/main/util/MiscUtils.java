package net.justacoder.shadowclient.main.util;

import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MiscUtils {

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
