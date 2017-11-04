package co.bugg.quickplay;

import co.bugg.quickplay.gui.MainGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import co.bugg.quickplay.util.QuickPlayColor;
import co.bugg.quickplay.util.TickDelay;
import co.bugg.quickplay.version.Version;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuickPlayEventHandler {

    /**
     * Used to communicate between onJoin and
     * onWorldLoad to determine whether version
     * update message should be sent or not.
     */
    private boolean justJoined = false;

    @SubscribeEvent
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        // Check if the connection is to the Hypixel network.

        boolean singleplayer = Minecraft.getMinecraft().isSingleplayer();

        if(!singleplayer) {

            ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
            String ip = "";
            if(serverData != null) {
                ip = serverData.serverIP;
            }

            Pattern hypixelPattern = Pattern.compile("^(?:(?:(?:\\w+\\.)?hypixel\\.net)|(?:209\\.222\\.115\\.\\d{1,5}))(?::\\d{1,5})?$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = hypixelPattern.matcher(ip);

            if (matcher.find()) {
                QuickPlay.onHypixel = true;
                System.out.println("Currently on Hypixel!");

                justJoined = true;
            } else {
                QuickPlay.onHypixel = false;
            }
        }
    }

    @SubscribeEvent
    public void onLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        QuickPlay.onHypixel = false;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        if(QuickPlay.onHypixel) {
            // If open GUI key is pressed
            if (QuickPlay.openGui.isKeyDown()) {
                System.out.println("Open GUI key pressed");
                QuickPlayGui.openGui(new MainGui());
            }
        }
    }

    @SubscribeEvent
    public void onGameTick(TickEvent.ClientTickEvent event) {

        if(QuickPlay.onHypixel) {
            for (HashMap.Entry<String, QuickPlayColor> entry : QuickPlay.configManager.getConfig().colors.entrySet()) {
                if (entry.getValue().getIsChroma()) {
                    int rgb = QuickPlayColor.nextChromaFrame(entry.getValue());

                    entry.setValue(new QuickPlayColor(rgb, entry.getValue().getUnlocalizedName(), entry.getValue().getIsChroma()));
                }
            }
        }
    }

    @SubscribeEvent
    public void onJoinWorld(WorldEvent.Load event) {
        // Only run if this is the first time the player
        // is loading the world on tne network
        if(justJoined) {
            if(QuickPlay.configManager.getConfig().updateNotifications) {
                // Get the version information and spit it out into chat
                // if there's an update.
                int delayInSeconds = 2;
                new TickDelay(() -> Version.checkForUpdates(Minecraft.getMinecraft().player), delayInSeconds * 40);
            }
            // Join has been handled
            justJoined = false;
        }
    }
}
