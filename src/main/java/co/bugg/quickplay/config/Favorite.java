package co.bugg.quickplay.config;

import co.bugg.quickplay.Game;
import co.bugg.quickplay.gui.GameGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
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

    private transient GuiButton parentButton;

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

    public void unfavorite() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(Keyboard.isKeyDown(getKeyCode()) && Minecraft.getMinecraft().currentScreen == null) {
            QuickPlayGui.openGui(new GameGui(getGame()));
        }
    }

    @SubscribeEvent
    public void onGameTick(TickEvent.ClientTickEvent event) {
        while (listening && Keyboard.next()) {
            setKeyCode(Keyboard.getEventKey());
            stopListening();
        }
    }

    /**
     * Listen for a key press, and set this key's keycode upon
     * completion.
     */
    public void listen(GuiButton button) {
        this.parentButton = button;
        listening = true;
        parentButton.displayString = "> " + EnumChatFormatting.YELLOW  + Keyboard.getKeyName(getKeyCode()) + EnumChatFormatting.RESET +  " <";
    }

    public void stopListening() {
        listening = false;

        if(parentButton != null)
            parentButton.displayString = Keyboard.getKeyName(getKeyCode());
    }
}
