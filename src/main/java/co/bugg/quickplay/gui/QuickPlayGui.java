package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class QuickPlayGui extends GuiScreen {

    /**
     * Close any open GUI
     */
    public void closeGui() {
        openGui(null);
    }

    /**
     * Open a GUI
     * @param gui The GUI to open
     */
    public static void openGui(GuiScreen gui) {
        Minecraft.getMinecraft().displayGuiScreen(gui);
    }

    /**
     * Override of keyTyped, super should probably
     * be called by children. This method obeys
     * the users config settings, and closes the GUI
     * depending on what key they typed.
     * @param typedChar Typed key
     * @param keyCode Key code of typed key
     * @throws IOException IOException
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        // Close GUI if the key pressed is inventory key
        if(Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode() == keyCode) {
            closeGui();
        }
    }

    /**
     * Obey the users QP settings and close
     * the GUI if any key is pressed
     */
    protected void obeySettings() {
        // Close the GUI if the settings say any key closes GUI
        if(QuickPlay.configManager.getConfig().anyKeyClosesGui) {
            closeGui();
        }
    }
}
