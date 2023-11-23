package net.shadowclient.main.util;

import net.shadowclient.main.SCMain;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public class FileUtils {
    public static boolean writeFile(Path path, String contents) {
        try {
            Files.writeString(path, contents);
            return true;
        } catch (IOException e) {
            SCMain.error("Failed to write file: " + path + " " + e);
            e.printStackTrace();
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

    public static @Nullable String readFile(Path path) {
        try {
            AtomicReference<String> contents = new AtomicReference<>(""); // i hate java
            Files.readAllLines(path).forEach((line) -> contents.set(contents.get() + line + "\n"));
            return contents.get();
        } catch (IOException e) {
            SCMain.error("Failed to read file: " + path + " " + e);
            e.printStackTrace();
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
}
