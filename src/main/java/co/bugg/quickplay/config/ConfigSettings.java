package co.bugg.quickplay.config;

import co.bugg.quickplay.Game;
import co.bugg.quickplay.util.QuickPlayColor;
import org.lwjgl.input.Keyboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ConfigSettings implements Serializable {

    public ConfigSettings() {
        colors.put("primary", new QuickPlayColor(0, 255, 255, "primary", false));
        colors.put("secondary", new QuickPlayColor(255, 255,  0, "secondary", false));
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
    public int openFavoriteKey = Keyboard.KEY_G;

    /*******************
     *    Favorites    *
     *******************/

    public Game favoriteGame = null;

    /*******************
     *   Party Mode    *
     *******************/

    public List<String> enabledPartyCommands = new ArrayList<>();
}
