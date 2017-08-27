package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuickPlayGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Draw the credits
        drawString(fontRendererObj, QuickPlay.credit, width - fontRendererObj.getStringWidth(QuickPlay.credit) - 3, 3, 0x00FFFF);

        // These get incremented depending on how many icons are on screen
        int xOffset = 0;
        int yOffset = 0;

        for(Map.Entry<Integer, Game> entry : Icons.map.entrySet()) {

            // Checking if drawing this game on this X level would draw it off the screen at all
            // Have to multiply iconWidth by 1.5 because of the initial 0.5 offset in xImg.
            if(xOffset + Icons.iconWidth * 1.5 > width) {
                xOffset = 0;
                yOffset += 80;
            }

            int yText = (int) (height * 0.05 + 68 + yOffset);
            int xText = xOffset + Icons.iconWidth;

            drawCenteredString(fontRendererObj, entry.getValue().name, xText, yText, 0xFFFFFF);

            xOffset += 70;
        }
    }

    @Override
    public void initGui() {
        buttonList.clear();

        // These get incremented depending on how many icons are on screen
        int xOffset = 0;
        int yOffset = 0;

        for(Map.Entry<Integer, Game> entry : Icons.map.entrySet()) {

            // Checking if drawing this game on this X level would draw it off the screen at all
            // Have to multiply iconWidth by 1.5 because of the initial 0.5 offset in xImg.
            if(xOffset + Icons.iconWidth * 1.5 > width) {
                xOffset = 0;
                yOffset += 80;
            }

            int yImg = (int) (height * 0.05 + yOffset);
            int xImg = xOffset + Icons.iconWidth / 2;

            buttonList.add(entry.getValue().button(xImg, yImg));

            xOffset += 70;
        }
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        // Open a new GameGui for the game corresponding to the button clicked
        Minecraft.getMinecraft().displayGuiScreen(new GameGui(Icons.map.get(button.id)));

        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // Close the GUI whenever a key is pressed.
        closeGui();

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Sets the open GUI to null, which is the equivalent of closing the GUI.
     */
    public static void closeGui() {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }
}
