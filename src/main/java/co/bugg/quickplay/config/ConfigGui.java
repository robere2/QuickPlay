package co.bugg.quickplay.config;

import co.bugg.quickplay.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ConfigGui extends GuiScreen {

    ConfigList list;

    int listTop;
    int listBottom;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        Tessellator tess = Tessellator.getInstance();
        WorldRenderer worldr = tess.getWorldRenderer();

        // Draw the default dirt background
        double scale = 32.0;
        Minecraft.getMinecraft().renderEngine.bindTexture(Gui.optionsBackground);
        worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

        int left = 0;
        int right = width;
        int top = 0;
        int bottom = height;
        worldr.pos(left,  bottom, 0.0D).tex(left  / scale, bottom / scale).color(0x40, 0x40, 0x40, 0xFF).endVertex();
        worldr.pos(right, bottom, 0.0D).tex(right / scale, bottom / scale).color(0x40, 0x40, 0x40, 0xFF).endVertex();
        worldr.pos(right, top,    0.0D).tex(right / scale, top    / scale).color(0x40, 0x40, 0x40, 0xFF).endVertex();
        worldr.pos(left,  top,    0.0D).tex(left  / scale, top    / scale).color(0x40, 0x40, 0x40, 0xFF).endVertex();
        tess.draw();

        list.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, Reference.MOD_NAME + " Configuration", width / 2, listTop - 15, 0xFFFFFF);
    }

    @Override
    public void initGui() {

        listTop = (int) (height * 0.1);
        listBottom = (int) (height * 0.8);

        list = new ConfigList(Minecraft.getMinecraft(), width, listBottom - listTop, listTop, listBottom, 0, 25, width, height);

        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }


}
