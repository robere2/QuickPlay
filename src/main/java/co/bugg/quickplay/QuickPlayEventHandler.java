package co.bugg.quickplay;

import co.bugg.quickplay.gui.QuickPlayGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuickPlayEventHandler {

    @SubscribeEvent
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        // Check if the connection is to the Hypixel network.

        boolean singleplayer = Minecraft.getMinecraft().isSingleplayer();

        if(!singleplayer) {

            String ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;

            Pattern hypixelPattern = Pattern.compile("^(?:\\w+.hypixel\\.net)|(?:209\\.222\\.115\\.(?:18|27|8|40|36|33|19|38|16|43|10|46|48|47|39|20|30|23|21|99))$");
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

        // If open GUI key is pressed on Hypixel
        if(QuickPlay.openGui.isKeyDown() && QuickPlay.onHypixel) {
            System.out.println("Open GUI key pressed");
            Minecraft.getMinecraft().displayGuiScreen(new QuickPlayGui());
        }
    }
}
