package co.bugg.quickplay.gui;

import co.bugg.quickplay.Game;
import co.bugg.quickplay.Icons;
import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.gui.button.ArrowButton;
import co.bugg.quickplay.util.GameUtil;
import co.bugg.quickplay.util.GlUtil;
import co.bugg.quickplay.util.PartyUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyGui extends GuiScreen {

    int cameFromPage;

    public PartyGui(int cameFromPage) {
        this.cameFromPage = cameFromPage;
    }

    public PartyGui() {
        this(1);
    }

    /**
     * HashMap mapping button IDs to which game button they are
     */
    private HashMap<Integer, String> buttons = new HashMap<>();

    @SuppressWarnings("Duplicates")
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        Game game = Icons.getGame("Party Mode");

        if(game != null) {
            drawDefaultBackground();
            mc.renderEngine.bindTexture(QuickPlay.icons.get(game.fileID));
            // Draw this games icon at the top
            drawTexturedModalRect((width / 2 - Icons.iconWidth / 2), (height * 0.05f), game.xStart, game.yStart, Icons.iconWidth, Icons.iconHeight);

            // Draw the credits
            drawString(fontRendererObj, QuickPlay.credit, width / 2 - fontRendererObj.getStringWidth(QuickPlay.credit) / 2, height - 10, QuickPlay.configManager.getConfig().colors.get("primary").getRGB());
            GlUtil.resetGlColor();

            super.drawScreen(mouseX, mouseY, partialTicks);

            for (GuiButton button : buttonList) {
                // If the button ends with ellipsis
                if (button.displayString.matches("^.*\\.\\.\\.$")) {
                    if (mouseX > button.xPosition && mouseX < button.xPosition + button.width && mouseY > button.yPosition && mouseY < button.yPosition + button.height) {
                        List<String> hoverText = new ArrayList<>();
                        hoverText.add(buttons.get(button.id));
                        drawHoveringText(hoverText, mouseX, mouseY);
                    }
                }
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        // A lot of this code is the code used in GameGui, just modified,
        // as the buttons are supposed to be laid out the same way. Should
        // probably be minimized into a single method, but that's a task
        // TODO another day.

        // Get the number of buttons to render
        int buttonCount = Icons.list != null ? Icons.list.size() : 0;

        // Default sizes
        int defaultButtonWidth = 300;
        int defaultButtonHeight = 20;
        // Dynamic sizes (Change depending on number of buttons, which button is
        // currently being rendered, etc)
        int buttonWidth = defaultButtonWidth;
        // How far apart each button should be
        int buttonSpacing = 5;

        // Position of the Play button
        int playY = (int) ((height * 0.05) + Icons.iconHeight + 10);
        int playX = (width / 2) - ((buttonWidth / 2));

        // What Y position dynamic (all but lobby) buttons are not created above
        int startingHeight = (int) ((height * 0.05) + Icons.iconHeight + 10 + defaultButtonHeight + buttonSpacing);

        // buttonId incremented by 1 every time a button is added
        int buttonId = 0;
        int trueHeight = height - startingHeight;
        // How many buttons can fit on the screen in one column
        int maxButtonPerColumn = trueHeight / (defaultButtonHeight + buttonSpacing);
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

        // Create the back button
        buttonList.add(new ArrowButton(buttonId, (width / 2) - (Icons.iconWidth / 2 + ArrowButton.width + 5), (int) (height * 0.05) + Icons.iconHeight / 2 - ArrowButton.height / 2, 0));
        buttons.put(buttonId, null);
        buttonId++;

        // Create the lobby button
        buttonList.add(new GuiButton(buttonId, playX, playY, defaultButtonWidth, defaultButtonHeight, new ChatComponentTranslation("quickplay.party.start").getUnformattedText()));
        // Register the button's ID
        buttons.put(buttonId, null);
        buttonId++;

        // if any games exist
        if(Icons.list != null) {
            int currentColumn = 1;
            for (Game game : Icons.list) {

                // Reached the end of the columns so go back to the beginning
                if(currentColumn > columnCount) {
                    currentColumn = 1;
                    buttonX = startingX;

                    // Move one button position lower
                    buttonY += (defaultButtonHeight + buttonSpacing);
                }

                // Only add the game if it actually has /play commands
                if(game.commands != null) {
                    // Button should only be drawn if at least one of the games commands
                    // is a play command (i.e. /qp limbo isn't a game, don't draw it)
                    for(Map.Entry<String, String> command : game.commands.entrySet()) {
                        if(!command.getValue().startsWith("/")) {
                            buttonList.add(new GuiButton(buttonId, buttonX, buttonY, buttonWidth, defaultButtonHeight, GameUtil.getButtonTextWithEllipsis(buttonWidth, game.name)));
                            // Register the button's ID
                            buttons.put(buttonId, game.name);
                            buttonId++;

                            currentColumn++;
                            // Move one button position over
                            buttonX += (buttonWidth + buttonSpacing);

                            // Break, since we've confirmed at least one game doesn't
                            // start with "/", and we can move on.
                            break;
                        }
                    }
                }

            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            // If the button is the back button
            case 0:
                mc.displayGuiScreen(new MainGui(cameFromPage));
                break;

            // If the button is the play button
            case 1:
                mc.thePlayer.addChatMessage(new ChatComponentText("Picking random gamemode...").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
                sendChatMessage("/play " + PartyUtil.getRandomPlayCommand());
                MainGui.closeGui();
                break;

            // Handle like a normal button
            default:
                System.out.println("User clicked on " + button.displayString + " button!");
                mc.displayGuiScreen(new GameGui(Icons.getGame(buttons.get(button.id)), cameFromPage, true));
                break;
        }

        super.actionPerformed(button);
    }
}
