package co.bugg.quickplay;

import net.minecraftforge.common.ForgeVersion;

/**
 * Miscellaneous Utilities
 */
public class Util {
    /**
     * Get the version of Minecraft this client is using
     * @return String version number
     */
    public static String getMCVersion() {
        String defaultVersion = "1.12.1";
        try {
            String version = ForgeVersion.class.getField("mcVersion").get("mcVersion").toString();
            if(version != null) {
                return version;
            } else {
                return defaultVersion;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return defaultVersion;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return defaultVersion;
        }
    }
}
