package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GameButton extends GuiButton {

    private int xTexture;
    private int yTexture;
    private int fileID;

    public GameButton(int buttonId, int x, int y, int xTexture, int yTexture, int fileID) {
        super(buttonId, x, y, Icons.iconWidth, Icons.iconHeight, "");
        this.xTexture = xTexture;
        this.yTexture = yTexture;
        this.fileID = fileID;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            mc.renderEngine.bindTexture(QuickPlay.icons.get(this.fileID));
            drawTexturedModalRect(xPosition, yPosition, this.xTexture, this.yTexture, Icons.iconWidth, Icons.iconHeight);
        }
    }




}
