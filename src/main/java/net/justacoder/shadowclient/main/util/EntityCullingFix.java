package net.justacoder.shadowclient.main.util;

import net.justacoder.shadowclient.main.SCMain;
import java.lang.reflect.Field;

public abstract class EntityCullingFix {

    private static void setShouldCull(boolean cull) {
        try {
            Class<?> configClass = Class.forName("dev.tr7zw.entityculling.versionless.EntityCullingVersionlessBase");
            Field enabledField = configClass.getDeclaredField("enabled");
            enabledField.setBoolean(null, cull);
        } catch (ClassNotFoundException ignored) { // entityCulling not installed
            SCMain.info("EntityCulling not installed. Skipping force " + (cull ? "enable" : "disable"));
        } catch (NoSuchFieldException e) {
            try {
                Class<?> configClass = Class.forName("dev.tr7zw.entityculling.EntityCullingModBase");
                Field enabledField = configClass.getDeclaredField("enabled");
                enabledField.setBoolean(null, cull);
            } catch (Exception e1) {
                SCMain.error("Error force " + (cull ? "enabling" : "disabling") + " Entity Culling: \n" + JavaUtils.stackTraceFromThrowable(e1));
            }
        } catch (Exception e) {
            SCMain.error("Error force " + (cull ? "enabling" : "disabling") + " Entity Culling: \n" + JavaUtils.stackTraceFromThrowable(e));
        }
    }

    private static int enable; // 0: enable 1+: don't

    public static void disableCull() {

        enable += 1;

        setShouldCull(false);

    }

    public static void enableCull() {

        enable -= 1;

        if (enable == 0) { // only enable entityCulling if NO module needs it disabled
            setShouldCull(true);
        }
    }

}
