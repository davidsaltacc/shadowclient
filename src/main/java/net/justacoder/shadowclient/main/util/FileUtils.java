package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.ShadowClientMain;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public abstract class FileUtils {

    public static boolean writeFile(Path path, String contents) {
        try {
            Files.writeString(path, contents);
            return true;
        } catch (IOException e) {
            ShadowClientMain.error("Failed to write file " + path + " ");
            ShadowClientMain.error(JavaUtils.stackTraceFromThrowable(e));
            return false;
        }
    }

    public static boolean writeFile(File file, String contents) {
        Path path = file.toPath();
        return writeFile(path, contents);
    }

    public static boolean writeFile(String path, String contents) {
        File file = new File(path);
        return writeFile(file, contents);
    }

    public static boolean writeFile(Path path, byte[] contents) {
        try {
            Files.write(path, contents);
            return true;
        } catch (IOException e) {
            ShadowClientMain.error("Failed to write file " + path + " ");
            ShadowClientMain.error(JavaUtils.stackTraceFromThrowable(e));
            return false;
        }
    }

    public static boolean writeFile(File file, byte[] contents) {
        return writeFile(file.toPath(), contents);
    }

    public static boolean writeFile(String path, byte[] contents) {
        return writeFile(new File(path), contents);
    }

    public static @Nullable String readFile(Path path) {
        try {
            return Files.readString(path);
        } catch (NoSuchFileException e) {
            ShadowClientMain.warn("Tried to read nonexistent file " + path);
            return null;
        } catch (IOException e) {
            ShadowClientMain.error("Failed to read file: " + path + " ");
            ShadowClientMain.error(JavaUtils.stackTraceFromThrowable(e));
            return null;
        }
    }
    public static @Nullable String readFile(File file) {
        Path path = file.toPath();
        return readFile(path);
    }
    public static @Nullable String readFile(String path) {
        File file = new File(path);
        return readFile(file);
    }


    public static byte[] readFileBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (NoSuchFileException e) {
            ShadowClientMain.warn("Tried to read nonexistent file " + path);
            return null;
        } catch (IOException e) {
            ShadowClientMain.error("Failed to read file: " + path + " ");
            ShadowClientMain.error(JavaUtils.stackTraceFromThrowable(e));
            return null;
        }
    }
    public static byte[] readFileBytes(File file) {
        Path path = file.toPath();
        return readFileBytes(path);
    }
    public static byte[] readFileBytes(String path) {
        File file = new File(path);
        return readFileBytes(file);
    }

    public static void removeFile(File file) {
        file.delete();
    }

    public static void removeFile(String path) {
        removeFile(new File(path));
    }

}
