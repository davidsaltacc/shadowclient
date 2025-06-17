package net.justacoder.shadowclient.main.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class CompressionUtils {

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
