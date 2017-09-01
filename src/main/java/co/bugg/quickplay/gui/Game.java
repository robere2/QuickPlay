package co.bugg.quickplay.gui;

import net.minecraft.client.gui.GuiButton;

import java.util.HashMap;

/**
 * Class that contains information about games, such as their lobby name, icon position, and /play commands.
 */
public class Game {

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
     * ID for this game's icon in the main menu
     */
    public int buttonID;
    /**
     * What to append after "/lobby" when the Go To Lobby button is pressed
     */
    public String lobbyName;
    /**
     * HashMap containing all /play commands
     */
    public HashMap<String, String> commands;

    public Game(String name, int fileID, int xStart, int yStart, int buttonID, String lobbyName, HashMap<String, String> commands) {
        this.name = name;
        this.fileID = fileID;
        this.xStart = xStart;
        this.yStart = yStart;
        this.buttonID = buttonID;
        this.lobbyName = lobbyName;
        this.commands = commands;
    }

    /**
     * Create new custom Gui button with this game's icon as the background
     * @param x X position on the GUI
     * @param y Y position on the GUI
     * @return GameButton extension of GuiButton with custom texture
     */
    public GuiButton getButton(int x, int y) {
        return new GameButton(this.buttonID, x, y, this.xStart, this.yStart, this.fileID);
    }
}
