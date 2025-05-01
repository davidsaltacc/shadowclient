package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.ShadowClientMain;
import org.joml.Vector2i;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ScreenSizeGetter {

    public static Vector2i getResolution() {

        try {
            String os = System.getProperty("os.name").toLowerCase();
            String[] command = getCommand(os);

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();

            if (line != null) {
                String[] parts = line.split(" x ");
                if (parts.length >= 2) {
                    int screenWidth = Integer.parseInt(parts[0].trim());
                    int screenHeight = Integer.parseInt(parts[1].trim());
                    return new Vector2i(screenWidth, screenHeight);
                } else {
                    ShadowClientMain.warn("Failed to parse resolution from: " + line);
                }
            } else {
                ShadowClientMain.warn("No output from command.");
            }

            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Vector2i(1920, 1080); // default

    }

    private static String[] getCommand(String os) {
        String[] command;

        if (os.contains("linux")) {
            command = new String[]{
                    "bash", "-c", "xrandr | grep ' connected' | awk '{print $3}' | cut -d '+' -f1"
            };
        } else if (os.contains("mac")) {
            command = new String[]{
                    "bash", "-c", "system_profiler SPDisplaysDataType | grep Resolution | awk '{print $2}'"
            };
        } else if (os.contains("win")) {
            command = new String[]{
                    "powershell.exe", "-Command",
                    "Get-CimInstance -ClassName Win32_VideoController | Select-Object -ExpandProperty VideoModeDescription"
            };
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }
        return command;
    }
}