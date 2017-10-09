package co.bugg.quickplay.config;

import co.bugg.quickplay.Game;
import co.bugg.quickplay.gui.GameGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class Favorite extends KeyBinding {

    public Game game;

    public Favorite(int keyCode, Game game) {
        super(game.name + " Keybind", keyCode, "key.categories.quickplay");
        this.game = game;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public Game getGame() {
        return game;
    }

    public void unfavorite() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(isKeyDown()) {
            QuickPlayGui.openGui(new GameGui(getGame()));
        }
    }

    public void listen() {

    }
}
