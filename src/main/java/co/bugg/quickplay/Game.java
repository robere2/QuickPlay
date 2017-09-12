package co.bugg.quickplay;

import co.bugg.quickplay.gui.button.GameButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class that contains information about games, such as their lobby name, icon position, and /play commands.
 */
public class Game implements Serializable {

    /**
     * Formatted name of the game
     */
    public String name;
    /**
     * Which file this game's icon is in
     */
    public int fileID;
    /**
     * xStart and yStart are the coordinates of the top left pixel of this games icon
     */
    public int xStart;
    public int yStart;
    /**
     * What to append after "/lobby" when the Go To Lobby button is pressed
     */
    public String lobbyName;
    /**
     * What text to put as the lobby button. Defaults to the quickplay.buttons.lobby translation.
     * Main use at the moment is for Housing.
     */
    public String lobbyButtonString = new TextComponentTranslation("quickplay.buttons.lobby").getFormattedText();
    /**
     * HashMap containing all /play commands
     */
    public HashMap<String, String> commands;

    public Game(String name, int fileID, int xStart, int yStart, String lobbyName, HashMap<String, String> commands) {
        this.name = name;
        this.fileID = fileID;
        this.xStart = xStart;
        this.yStart = yStart;
        this.lobbyName = lobbyName;
        this.commands = commands;
    }
    public Game(String name, int fileID, int xStart, int yStart, String lobbyName, String lobbyButtonString, HashMap<String, String> commands) {
        this.name = name;
        this.fileID = fileID;
        this.xStart = xStart;
        this.yStart = yStart;
        this.lobbyName = lobbyName;
        this.lobbyButtonString = lobbyButtonString;
        this.commands = commands;
    }

    /**
     * Create new custom Gui button with this game's icon as the background
     * @param x X position on the GUI
     * @param y Y position on the GUI
     * @return GameButton extension of GuiButton with custom texture
     */
    public GuiButton getButton(int buttonId, int x, int y) {
        return new GameButton(buttonId, x, y, this);
    }
}
