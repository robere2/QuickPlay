package co.bugg.quickplay.version;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.util.GameUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Version implements Comparable<Version> {
    private final String version;

    public Version(String version) {
        this.version = version;
    }

    public String toString() {
        return version;
    }

    /**
     * Compare two versions
     * @param comparing Version to compare against
     * @see "https://stackoverflow.com/questions/198431/how-do-you-compare-two-version-strings-in-java"
     * @return -1 if local is less than comparing, 0 if same, 1 if greater than comparing
     */
    @Override
    public int compareTo(@Nonnull Version comparing) {
        String[] localParts = toString().split("\\.");
        String[] comparingParts = comparing.toString().split("\\.");
        int length = Math.max(localParts.length, comparingParts.length);
        for(int i = 0; i < length; i++) {
            int localPart = i < localParts.length ?
                    Integer.parseInt(localParts[i]) : 0;
            int comparingPart = i < comparingParts.length ?
                    Integer.parseInt(comparingParts[i]) : 0;
            if(localPart < comparingPart)
                return -1;
            if(localPart > comparingPart)
                return 1;
        }
        return 0;
    }

    public int compareTo(@Nonnull String comparing) {
        return compareTo(new Version(comparing));
    }

    /**
     * Get version information from the Update JSON
     * in the reference file, then send it to the callback
     * @param callback Callback to run when the request is done
     */
    public static void getVersionInfo(VersionCallback callback) {
        new Thread(() ->  {
            try {
                URL url = new URL(Reference.UPDATE_JSON);
                InputStream stream = url.openStream();

                JsonObject json = new Gson().fromJson(IOUtils.toString(stream), JsonElement.class).getAsJsonObject();

                String remoteString = json.get("promos").getAsJsonObject().get(GameUtil.getMCVersion() + "-recommended").getAsString();

                if(remoteString != null) {
                    Version remote = new Version(remoteString);
                    callback.VersionCallback(remote);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).run();
    }

    /**
     * Check for new updates & send message to
     * provided player if a new version is out
     * @param player Player to send message to
     */
    public static void checkForUpdates(EntityPlayerSP player) {
        if(player != null) {
            getVersionInfo((remote) -> {
                Version local = new Version(Reference.VERSION);
                // If local is older than remote &
                // player has update notifications on
                if(local.compareTo(remote) < 0 && QuickPlay.configManager.getConfig().updateNotifications) {
                    player.sendMessage(createChatMessage(remote));
                }
            });
        }
    }

    /**
     * Create the chat message to send to the
     * player in the case of a new update
     * @param version New version
     * @return IChatComponent to send
     */
    public static ITextComponent createChatMessage(Version version) {
        ITextComponent message = new TextComponentString("");

        ITextComponent dynamicInfo = new TextComponentString(Reference.MOD_NAME + " v" + version.toString() + " ")
                .setStyle(new Style().setColor(TextFormatting.AQUA));

        ITextComponent staticMessage = new TextComponentTranslation("quickplay.version.notification")
                .setStyle(new Style().setColor(TextFormatting.YELLOW));

        message.appendSibling(dynamicInfo).appendSibling(staticMessage)
                .setStyle(new Style().setClickEvent(
                        new ClickEvent(ClickEvent.Action.OPEN_URL, Reference.UPDATE_THREAD)
                ));

        return message;
    }
    
}
