package co.bugg.quickplay.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ConfigGui extends GuiScreen {

    ConfigList list;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        list.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        list = new ConfigList(Minecraft.getMinecraft(), width / 2, height, 0, height, 0, 20, width, height);

        super.initGui();
    }
}
