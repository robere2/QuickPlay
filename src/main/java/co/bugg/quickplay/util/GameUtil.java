package co.bugg.quickplay.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.ForgeVersion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Misc utilities relating to Minecraft itself
 */
public final class GameUtil {

    private GameUtil() { throw new AssertionError(); }

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
            Method versionMethod = ReflectUtil.getMethod(ForgeVersion.class, "getVersion");

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
            Field valueField = ReflectUtil.getField(ForgeVersion.class, fieldName);

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
            ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
            ip = (serverData == null) ? null : serverData.serverIP;
        }

        return ip;
    }
}
