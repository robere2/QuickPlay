package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GUI screen for individual games, as opposed to the main GUI screen for the mod that lists all games
 */
public class GameGui extends GuiScreen {
    /**
     * Which game this GUI screen instance is for
     */
    Game game;
    /**
     * HashMap mapping join button IDs to which join button they are
     */
    private HashMap<Integer, String> buttons = new HashMap<Integer, String>();

    public GameGui(Game game) {
        this.game = game;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        mc.renderEngine.bindTexture(QuickPlay.icons.get(this.game.fileID));
        // Draw this games icon at the top
        drawTexturedModalRect((width / 2 - Icons.iconWidth / 2), (float) (height * 0.05), this.game.xStart, this.game.yStart, Icons.iconWidth, Icons.iconHeight);
        // Draw the credits
        drawString(fontRendererObj, QuickPlay.credit, width - fontRendererObj.getStringWidth(QuickPlay.credit) - 3, 3, 0x00FFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();

        // Get the number of buttons to render
        int buttonCount = game.commands != null ? game.commands.size() : 0;

        // Default sizes
        int defaultButtonWidth = 300;
        int defaultButtonHeight = 20;
        // Dynamic sizes (Change depending on number of buttons, which button is
        // currently being rendered, etc)
        int buttonWidth = defaultButtonWidth;
        int buttonHeight = defaultButtonHeight;
        // How far apart each button should be
        int buttonSpacing = 5;

        // Position of the lobby button
        int lobbyY = (int) ((height * 0.05) + Icons.iconHeight + 10);
        int lobbyX = (width / 2) - ((buttonWidth / 2));

        // What Y position dynamic (all but lobby) buttons are not created above
        int startingHeight = (int) ((height * 0.05) + Icons.iconHeight + 10 + buttonHeight + buttonSpacing);

        // buttonId incremented by 1 every time a button is added
        int buttonId = 0;
        int trueHeight = height - startingHeight;
        // How many buttons can fit on the screen in one column
        int maxButtonPerColumn = trueHeight / (buttonHeight + buttonSpacing);
        // How many columns are required to fit all the buttons in
        double columnCount = Math.ceil(((double)buttonCount / (double)maxButtonPerColumn));

        // When there's no buttons (besides the lobby button), columnCount is 0 which throws an error.
        if(columnCount < 1) {
            columnCount = 1;
        }

        // Where the first button is rendered on the X axis (the column furthest to the left)
        int startingX = (width / 2) - ((buttonWidth / 2) + (buttonSpacing * ((int) columnCount - 1) / 2));

        // Resizing button width depending on how many columns there are
        buttonWidth = buttonWidth / (int) columnCount;

        // Where to position the top left corner of the button
        int buttonY = startingHeight;
        int buttonX = startingX;

        String lobbyButtonText;
        // Create the lobby button
        if(game.lobbyName.equals("home")) {
            lobbyButtonText = "Go Home";
        } else {
            lobbyButtonText = "Go To Lobby";
        }

        buttonList.add(new GuiButton(buttonId, lobbyX, lobbyY, defaultButtonWidth, defaultButtonHeight, lobbyButtonText));
        // Register the button's ID
        buttons.put(buttonId, lobbyButtonText);
        buttonId++;

        // if any play commands exist
        if(game.commands != null) {
            int currentColumn = 1;
            for (Map.Entry<String, String> entry : game.commands.entrySet()) {

                // Reached the end of the columns so go back to the beginning
                if(currentColumn > columnCount) {
                    currentColumn = 1;
                    buttonX = startingX;

                    // Move one button position lower
                    buttonY += (buttonHeight + buttonSpacing);
                }

                buttonList.add(new GuiButton(buttonId, buttonX, buttonY, buttonWidth, buttonHeight, entry.getKey()));
                // Register the button's ID
                buttons.put(buttonId, entry.getKey());
                buttonId++;

                currentColumn++;
                // Move one button position over
                buttonX += (buttonWidth + buttonSpacing);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        // If the button is the lobby button
        if(button.id == 0) {
            // if this GUI is for housing
            if(game.lobbyName.equals("home")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/home");
            } else {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby " + game.lobbyName);
            }
        } else {
            String command = game.commands.get(buttons.get(button.id));

            Minecraft.getMinecraft().thePlayer.sendChatMessage("/play " + command);
        }

        QuickPlayGui.closeGui();
        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // Close the GUI whenever a key is pressed.
        QuickPlayGui.closeGui();

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
