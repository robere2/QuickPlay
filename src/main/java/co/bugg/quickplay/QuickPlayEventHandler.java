package co.bugg.quickplay;

import co.bugg.quickplay.gui.GameGui;
import co.bugg.quickplay.gui.MainGui;
import co.bugg.quickplay.util.QuickPlayColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuickPlayEventHandler {

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

            Pattern hypixelPattern = Pattern.compile("^(?:(?:(?:\\w+\\.)?hypixel\\.net)|(?:209\\.222\\.115\\.(?:18|27|8|40|36|33|19|38|16|43|10|46|48|47|39|20|30|23|21|22|99)))(?::\\d{1,5})?$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = hypixelPattern.matcher(ip);

            if (matcher.find()) {
                QuickPlay.onHypixel = true;
                System.out.println("Currently on Hypixel!");
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
                Minecraft.getMinecraft().displayGuiScreen(new MainGui());

                // If the open Favorite GUI key is pressed
            } else if(QuickPlay.openFavorite.isKeyDown()) {
                System.out.println("Open Favorite key pressed");

                // Check if the user even has a favorite game
                if(QuickPlay.configManager.getConfig().favoriteGame == null) {
                    // If not then open the main GUI
                    Minecraft.getMinecraft().displayGuiScreen(new MainGui());
                } else {
                    // Otherwise open the game
                    Minecraft.getMinecraft().displayGuiScreen(new GameGui(QuickPlay.configManager.getConfig().favoriteGame));
                }
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
}
