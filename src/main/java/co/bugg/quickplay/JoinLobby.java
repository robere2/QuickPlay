package co.bugg.quickplay;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JoinLobby {

    EntityPlayerSP player;

    public JoinLobby(String lobbyName, EntityPlayerSP player) {
        this.player = player;

        // If the mod is configured to automatically switch
        // to lobby one whenever the player presses "Go To Lobby"
        if(QuickPlay.configManager.getConfig().swapToLobbyOne) {
            MinecraftForge.EVENT_BUS.register(this);
        }

        // go to the lobby
        player.sendChatMessage(lobbyName);
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        // This code will only run if the player has
        // "swapToLobbyOne" enabled in their config
        MinecraftForge.EVENT_BUS.unregister(this);

        // Send the player to lobby 1
        player.sendChatMessage("/swaplobby 1");
    }
}
