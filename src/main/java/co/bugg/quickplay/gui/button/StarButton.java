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
public class StarButton extends GuiButton {

    /**
     * Whether this star is enabled or not
     */
    public boolean on;

    /**
     * onXTexture/offXTexture and onYTexture/offYTexture correspond to the coordinates in the
     * icons image file where the top left corner of the icon is
     */
    public static int onXTexture = 25;
    public static int onYTexture = 32;

    public static int offXTexture = 0;
    public static int offYTexture = 32;

    /**
     * Height and width of the icon
     */
    public static int width = 25;
    public static int height = 23;

    /**
     * Constructor
     * @param buttonId ID of the button
     * @param x X position on the GUI screen
     * @param y Y position on the GUI screen
     * @param on Whether this star is enabled or not
     */
    public StarButton(int buttonId, int x, int y, boolean on) {
        super(buttonId, x, y, width, height, "");

        this.on = on;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();

            mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID,"textures/gui/widgets.png"));
            // If the star is on then draw its innards
            if(on) {
                Color starColor = QuickPlay.configManager.getConfig().colors.get("secondary");
                glColor3f((float) starColor.getRed() / 255, (float) starColor.getGreen() / 255, (float) starColor.getBlue() / 255);
                drawTexturedModalRect(xPosition, yPosition, onXTexture, onYTexture, width, height);
                GlUtil.resetGlColor();
            }

            // Draw the star outline
            drawTexturedModalRect(xPosition, yPosition, offXTexture, offYTexture, width, height);

            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
        }
    }
}
