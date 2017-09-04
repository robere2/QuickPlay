package co.bugg.quickplay;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeVersion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Miscellaneous Utilities
 */
public class Util {
    /**
     * Get the version of Minecraft this client is using
     * @return String version number
     */
    public static String getMCVersion() {
        return getForgeVersionField("mcVersion", "Unknown");
    }

    /**
     * Get the version of MCP this client is using
     * @return String version number
     */
    public static String getMCPVersion() {
        return getForgeVersionField("mcpVersion", "Unknown");
    }

    public static String getForgeVersion() {
        String defaultValue = "Unknown";
        try {
            Method versionMethod = getMethod(ForgeVersion.class, "getVersion");

            if(versionMethod == null) {
                return defaultValue;
            }

            return (String) versionMethod.invoke(null);

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * Get a field value from the ForgeVersion class
     * @param fieldName Field to grab
     * @param defaultValue Value to return if the field isn't defined
     * @return Field value, or default in case of undefined
     */
    public static String getForgeVersionField(String fieldName, String defaultValue) {
        try {
            Field valueField = getField(ForgeVersion.class, fieldName);

            if(valueField == null) {
                return defaultValue;
            }

            return (String) valueField.get(null);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * Get the IP of the current server the player is on
     * @return IP, N/A if single player, or null if not on server
     */
    public static String getIP() {
        boolean singleplayer = Minecraft.getMinecraft().isSingleplayer();
        String ip;

        if(singleplayer) {
            ip = "N/A";
        } else {
            ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        }

        return ip;
    }

    /**
     * Get the field of a class reflectively at runtime.
     * @param theClass The class to get the field from
     * @param fieldName The field to get
     * @return Field that was requested
     */
    public static Field getField(Class<?> theClass, String fieldName) {
        try {
            Field field = theClass.getField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the method of a class reflectively at runtime.
     * @param theClass The class to get the field from
     * @param methodName The method to get
     * @return Method that was requested
     */
    public static Method getMethod(Class<?> theClass, String methodName, Class<?>... args) {
        try {
            Method method = theClass.getMethod(methodName, args);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
