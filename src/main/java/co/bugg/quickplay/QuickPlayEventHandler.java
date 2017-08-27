package co.bugg.quickplay;

import co.bugg.quickplay.gui.QuickPlayGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class QuickPlayEventHandler {

    @SubscribeEvent
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        // Check if the connection is to the Hypixel network.

        boolean singleplayer = Minecraft.getMinecraft().isSingleplayer();

        if(!singleplayer) {
            String ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;
            if (ip.contains(".hypixel.net")) {
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

        // Open GUI key is pressed on Hypixel
        if(QuickPlay.openGui.isKeyDown() && QuickPlay.onHypixel) {
            System.out.println("Open GUI key pressed");
            Minecraft.getMinecraft().displayGuiScreen(new QuickPlayGui());
            QuickPlay.guiOpen = true;
        }
    }
}
