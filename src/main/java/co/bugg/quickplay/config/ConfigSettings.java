package co.bugg.quickplay.config;

import co.bugg.quickplay.util.QuickPlayColor;
import org.lwjgl.input.Keyboard;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class ConfigSettings implements Serializable {

    public ConfigSettings() {
        colors.put("primary", new QuickPlayColor(0, 255, 255, "primary", true));
        colors.put("secondary", new QuickPlayColor(0, 255, 0, "secondary", true));
    }

    /*******************
     *     General     *
     *******************/
    public boolean anyKeyClosesGui = false;

    /*******************
     *      Colors     *
     *******************/
    // Colors are added to the hashmap in the constructor
    public LinkedHashMap<String, QuickPlayColor> colors = new LinkedHashMap<>();

    /*******************
     *  Key Bindings   *
     *******************/
    // I'm saving keybinds here because, for some reason,
    // Forge doesn't seem to persistently save them when
    // the game is started without the mod loaded. Next
    // time you try to start the game with the mod, the key
    // is back to its default value. Probably a better way
    // to do this but idk how yet.
    // TODO: Make this key not be the default, but instead
    // TODO: set the key to the key code
    public int openGuiKey = Keyboard.KEY_R;
}