package co.bugg.quickplay.config;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.gui.MainColorGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatComponentTranslation;

import java.io.IOException;

public class ConfigGui extends QuickPlayGui {

    AbstractConfigList list;

    int listTop;
    int listBottom;

    int closeButtonId = 0;
    int colorButtonId = 1;
    int favoriteButtonId = 2;

    boolean onFavorites = false;

    public ConfigGui() {
        this(false);
    }

    public ConfigGui(boolean onFavorites) {
        super();
        this.onFavorites = onFavorites;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

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

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {

        listTop = (int) (height * 0.1);
        listBottom = (int) (height * 0.8);

       if(onFavorites) {
           list = new FavoriteList(Minecraft.getMinecraft(), width, listBottom - listTop, listTop, listBottom, 0, 25, width, height, QuickPlay.configManager);
       } else {
           list = new ConfigList(Minecraft.getMinecraft(), width, listBottom - listTop, listTop, listBottom, 0, 25, width, height, QuickPlay.configManager);
       }
        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonY = (int) (height * 0.85);
        int buttonMargin = 4;

        String close = new ChatComponentTranslation("quickplay.config.close").getUnformattedText();
        String colors = new ChatComponentTranslation("quickplay.color").getUnformattedText();
        String favorites = new ChatComponentTranslation("quickplay.config.favorites").getUnformattedText();
        String settings = new ChatComponentTranslation("quickplay.config.settings").getUnformattedText();

        GuiButton colorButton = new GuiButton(colorButtonId, width / 2 - buttonWidth - buttonWidth / 2 - buttonMargin, buttonY, buttonWidth, buttonHeight, colors);
        GuiButton closeButton = new GuiButton(closeButtonId, width / 2 - buttonWidth / 2, buttonY, buttonWidth, buttonHeight, close);
        GuiButton favoriteButton = new GuiButton(favoriteButtonId, width / 2 + buttonMargin + buttonWidth / 2, buttonY, buttonWidth, buttonHeight, (onFavorites) ? settings : favorites);

        buttonList.add(closeButton);
        buttonList.add(colorButton);
        buttonList.add(favoriteButton);

        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button.id == closeButtonId) {
            closeGui();
        } else if(button.id == colorButtonId) {
            openGui(new MainColorGui());
        } else if(button.id == favoriteButtonId) {
            openGui(new ConfigGui(!onFavorites));
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        QuickPlay.configManager.saveConfig();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        // This GUI does not obey key closing settings
        //obeySettings();
    }
}
