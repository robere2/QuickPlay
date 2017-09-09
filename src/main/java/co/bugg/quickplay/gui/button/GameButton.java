package co.bugg.quickplay.gui.button;

import co.bugg.quickplay.Icons;
import co.bugg.quickplay.QuickPlay;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Extension of GuiButton that allows for 64x64 buttons (or any size really)
 * with a custom texture background. Used for the main GUI buttons
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GameButton extends GuiButton {

    /**
     * xTexture and yTexture correspond to the coordinates in the
     * icons image file where the top left corner of the icon is
     */
    private int xTexture;
    private int yTexture;
    /**
     * Which file the icon texture is in
     */
    private int fileID;

    public GameButton(int buttonId, int x, int y, int xTexture, int yTexture, int fileID) {
        super(buttonId, x, y, Icons.iconWidth, Icons.iconHeight, "");
        this.xTexture = xTexture;
        this.yTexture = yTexture;
        this.fileID = fileID;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(this.visible) {
            mc.renderEngine.bindTexture(QuickPlay.icons.get(this.fileID));
            drawTexturedModalRect(x, y, this.xTexture, this.yTexture, Icons.iconWidth, Icons.iconHeight);
        }
    }
}
