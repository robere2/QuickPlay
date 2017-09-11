package co.bugg.quickplay.gui.button;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.util.GlUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

import static org.lwjgl.opengl.GL11.glColor3f;

/**
 * Extension of GuiButton that allows for 64x64 buttons (or any size really)
 * with a custom texture background. Used for the main GUI buttons
 */
public class ArrowButton extends GuiButton {

    /**
     * Whether this is a back or forward button. 0 for back and 1 for forward
     */
    public int type;

    /**
     * backXTexture/forwardXTexture and backYTexture/forwardYTexture correspond to the coordinates in the
     * icons image file where the top left corner of the icon is
     */
    public static int backXTexture = 0;
    public static int backYTexture = 16;

    public static int forwardXTexture = 0;
    public static int forwardYTexture = 0;

    /**
     * Height and width of the icon
     */
    public static int width = 19;
    public static int height = 16;

    /**
     * Constructor
     * @param buttonId ID of the button
     * @param x X position on the GUI screen
     * @param y Y position on the GUI screen
     * @param buttonType Whether this is a back or forward button. 0 for back and 1 for forward
     */
    public ArrowButton(int buttonId, int x, int y, int buttonType) {
        super(buttonId, x, y, width, height, "");

        this.type = buttonType;
    }
    public ArrowButton(int buttonId, int x, int y) {
        this(buttonId, x, y, 0);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID,"textures/gui/widgets.png"));

            Color arrowColor = QuickPlay.configManager.getConfig().colors.get("secondary");
            glColor3f((float) arrowColor.getRed() / 255, (float) arrowColor.getGreen() / 255, (float) arrowColor.getBlue() / 255);
            switch (type) {
                case 0:
                default:
                    drawTexturedModalRect(xPosition, yPosition, backXTexture, backYTexture, width, height);
                    break;
                case 1:
                    drawTexturedModalRect(xPosition, yPosition, forwardXTexture, forwardYTexture, width, height);
                    break;
            }
            GlUtil.resetGlColor();
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
        }
    }
}
