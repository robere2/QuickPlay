package co.bugg.quickplay.config;

import co.bugg.quickplay.Game;
import co.bugg.quickplay.gui.GameGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.io.Serializable;

public class Favorite implements Serializable {

    private int keyCode;
    private Game game;

    public Favorite(int keyCode, Game game) {
        this.keyCode = keyCode;
        this.game = game;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public Game getGame() {
        return game;
    }
    public int getKeyCode() { return keyCode; }
    public void setKeyCode(int keyCode) { this.keyCode = keyCode; }

    public void unfavorite() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Keyboard.isKeyDown(keyCode)) {
            QuickPlayGui.openGui(new GameGui(getGame()));
        }
    }

    public void listen() {

    }
}
