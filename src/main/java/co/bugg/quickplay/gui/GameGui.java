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

public class GameGui extends GuiScreen {
    Game game;

    // HashMap mapping button IDs to which button they are
    private HashMap<Integer, String> buttons = new HashMap<Integer, String>();

    public GameGui(Game game) {
        this.game = game;


    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        mc.renderEngine.bindTexture(QuickPlay.icons.get(this.game.fileID));
        drawTexturedModalRect((width / 2 - Icons.iconWidth / 2), (float) (height * 0.05), this.game.xStart, this.game.yStart, Icons.iconWidth, Icons.iconHeight);
        drawString(fontRendererObj, "QuickPlay by bugfroggy", width - fontRendererObj.getStringWidth("QuickPlay by bugfroggy") - 3, 3, 0x00FFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();

        // Get the button count (+1 for the lobby button, or 1 if no commands)
        int buttonCount = game.commands != null ? game.commands.size() + 1 : 1;

        // Default sizes
        int defaultButtonWidth = 300;
        int defaultButtonHeight = 20;
        int buttonWidth = defaultButtonWidth;
        int buttonHeight = defaultButtonHeight;
        int buttonSpacing = 5;

        int lobbyY = (int) ((height * 0.05) + Icons.iconHeight + 10);
        int lobbyX = (width / 2) - ((buttonWidth / 2));

        // What Y position buttons are not created above
        int startingHeight = (int) ((height * 0.05) + Icons.iconHeight + 10 + buttonHeight + buttonSpacing);

        // buttonId incremented by 1 every time a button is added
        int buttonId = 0;
        int trueHeight = height - startingHeight;
        // How many buttons can fit on the screen in one column
        int maxButtonPerColumn = trueHeight / (buttonHeight + buttonSpacing);
        // How many columns are required
        double columnCount = Math.ceil(((double)buttonCount / (double)maxButtonPerColumn));

        // Furthest left column
        int startingX = (width / 2) - ((buttonWidth / 2) + (buttonSpacing * ((int) columnCount - 1) / 2));

        // Resizing button width depending on how many columns there are
        buttonWidth = buttonWidth / (int) columnCount;

        // Where to position the top left corner of the button
        int buttonY = startingHeight;
        int buttonX = startingX;

        String buttonText;
        // Create the lobby button
        if(game.lobbyName.equals("home")) {
            buttonText = "Go Home";
        } else {
            buttonText = "Go To Lobby";
        }

        buttonList.add(new GuiButton(buttonId, lobbyX, lobbyY, defaultButtonWidth, defaultButtonHeight, buttonText));
        buttons.put(buttonId, buttonText);
        buttonId++;

        if(game.commands != null) {
            int currentColumn = 1;
            for (Map.Entry<String, String> entry : game.commands.entrySet()) {

                // Reached the end of the columns so go back to the beginning
                if(currentColumn > columnCount) {
                    currentColumn = 1;
                    // Update the button Y position
                    buttonY += (buttonHeight + buttonSpacing);
                    buttonX = startingX;
                }

                buttonList.add(new GuiButton(buttonId, buttonX, buttonY, buttonWidth, buttonHeight, entry.getKey()));
                buttons.put(buttonId, entry.getKey());
                buttonId++;

                currentColumn++;
                buttonX += (buttonWidth + buttonSpacing);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        // If the button is the lobby button
        if(button.id == 0) {
            // This GUI is for housing
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
        Minecraft.getMinecraft().displayGuiScreen(null);

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
