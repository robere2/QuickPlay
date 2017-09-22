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

    /**
     * Get a string that is shortened to the proper length to fit
     * on a button of the provided width
     * @param buttonWidth Width of the button
     * @param text Text to be shortened
     * @return A string that is cut off with ellipsis at the end.
     */
    public static String getButtonTextWithEllipsis(int buttonWidth, String text) {
        StringBuilder builder = new StringBuilder();
        String ellipsis = "...";
        int buttonMargins = (int) (buttonWidth * 0.1);

        // If the string can fit on the button within the margins without the ellipsis then just return the string
        if(buttonWidth > Minecraft.getMinecraft().fontRenderer.getStringWidth(text) + buttonMargins) return text;

        // Otherwise we gotta loop, adding a character each time, until we find a string length
        // that doesn't fit within the margins anymore (with the ellipsis appended).
        int charIndex = 0;
        while(buttonWidth > Minecraft.getMinecraft().fontRenderer.getStringWidth(builder.toString() + ellipsis) + buttonMargins) {
            builder.append(text.charAt(charIndex));
            charIndex++;
        }

        // Once the string that doesn't fit anymore is found,
        // remove the one character that made it overflow
        builder.replace(charIndex - 1, charIndex, "");

        builder.append(ellipsis);
        return builder.toString();
    }
}
