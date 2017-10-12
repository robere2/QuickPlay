package co.bugg.quickplay.config;

import co.bugg.quickplay.Game;
import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.gui.GameGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.Serializable;

public class Favorite implements Serializable {

    private int keyCode;
    private Game game;
    private boolean listening = false;

    public Favorite(int keyCode, Game game) {
        this.keyCode = keyCode;
        this.game = game;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public Game getGame() {
        return game;
    }
    public int getKeyCode() { return keyCode; }
    public void setKeyCode(int keyCode) {
        // "ESCAPE" is not a valid key, and it should
        // be set to "NONE" when the escape code is passed
        this.keyCode = (keyCode > 1) ? keyCode : 0;
    }

    /**
     * "Unfavorite" by removing self from the event bus
     */
    public void unfavorite() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        // Open this favorite's game GUI if key is pressed
        if(Keyboard.isKeyDown(getKeyCode()) && Minecraft.getMinecraft().currentScreen == null) {
            QuickPlayGui.openGui(new GameGui(getGame()));
        }
    }


    @SubscribeEvent
    public void onGameTick(TickEvent.ClientTickEvent event) {
        // Set the key code on next key input if listening
        while (listening && Keyboard.next()) {
            setKeyCode(Keyboard.getEventKey());
            stopListening();
        }
        // Stop listening for events if no longer favorite
        if(!QuickPlay.configManager.getConfig().favorites.contains(this)) {
            unfavorite();
        }
    }

    /**
     * Getter for listening
     * @return listening
     */
    public boolean isListening() {
        return listening;
    }

    /**
     * Listen for a key press, and set this key's keycode upon
     * completion.
     */
    public void listen() {
        listening = true;
    }

    /**
     * Stop listening for key input to change the key
     */
    public void stopListening() {
        listening = false;
    }
}
