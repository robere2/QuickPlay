package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.util.QuickPlayColor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ChatComponentTranslation;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainColorGui extends QuickPlayGui {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    LinkedHashMap<Integer, QuickPlayColor> buttonIds = new LinkedHashMap<>();

    @Override
    public void initGui() {

        // Put all the configurable colors into a hashmap of colors corresponding to button IDs
        int buttonId = 0;

        for(HashMap.Entry<String, QuickPlayColor> entry : QuickPlay.configManager.getConfig().colors.entrySet()) {
            buttonList.add(new GuiButton(buttonId, width / 2 - 100, (int) (height * 0.4) - 10 + (25 * buttonId), new ChatComponentTranslation("quickplay.color.name." + entry.getValue().getUnlocalizedName()).getFormattedText()));
            buttonIds.put(buttonId, entry.getValue());
            buttonId++;
        }
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        QuickPlayColor color = buttonIds.get(button.id);
        openGui(new ColorGui(button.id, color));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        obeySettings();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
