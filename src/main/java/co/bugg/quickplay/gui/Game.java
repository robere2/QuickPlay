package co.bugg.quickplay.gui;

import net.minecraft.client.gui.GuiButton;

import java.util.HashMap;

public class Game {

    public String name;
    public int fileID;
    public int xStart;
    public int yStart;
    public int buttonID;
    public String lobbyName;
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

    public GuiButton button(int x, int y) {
        return new GameButton(this.buttonID, x, y, this.xStart, this.yStart, this.fileID);
    }
}
