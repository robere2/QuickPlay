package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.config.ConfigGui;
import co.bugg.quickplay.util.QuickPlayColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;

public class ColorGui extends QuickPlayGui {

    // An ID that is used in saving to point back
    // to the original color in MainColorGui.colors
    int id;
    // The color this GUI is for
    QuickPlayColor color;
    // The color drawn on samples and stuff
    // Separated in order to not interfere
    // with sliders when chroma is enabled
    QuickPlayColor drawColor;

    public ColorGui(int id, QuickPlayColor color) {
        this.color = color;
        this.id = id;

        MinecraftForge.EVENT_BUS.register(this);
    }

    int sampleWidth = 50;
    int sampleHeight = 50;
    int sliderWidth = 200;
    int sliderHeight = 20;

    /**
     * Draw the Minecraft default dirt/block background
     */
    public void drawDirtBackground() {
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
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDirtBackground();

        // Get the colors from before it is updated to
        // see if the colors have been changed at all
        Color oldColor = new Color(color.getRGB());

        // Update the color with the slider values
        GuiSlider red = (GuiSlider) buttonList.get(0);
        GuiSlider green = (GuiSlider) buttonList.get(1);
        GuiSlider blue = (GuiSlider) buttonList.get(2);

        color = new QuickPlayColor(red.getValueInt(), green.getValueInt(), blue.getValueInt(), color.getUnlocalizedName(), color.getIsChroma());

        // The color has changed so let's reset the draw color
        if(oldColor.getRGB() != color.getRGB() || drawColor == null || color.getIsChroma() != drawColor.getIsChroma()) {
            drawColor = color;
        }

        // Draw the sample view
        drawRect((width / 2) - (sampleWidth / 2), (int) (height * 0.2) - (sampleHeight / 2), (width / 2) + (sampleWidth / 2), (int) (height * 0.2) + (sampleHeight / 2), drawColor.getRGB());
        drawCenteredString(fontRendererObj, new ChatComponentTranslation("quickplay.color.name." + color.getUnlocalizedName()).getFormattedText(), width / 2, (int) (height * 0.2) - (sampleHeight / 2) - 15, 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();

        int sliderSpacing = 4;
        int buttonId = 0;

        buttonList.add(new GuiSlider(buttonId, width / 2 - sliderWidth / 2, (int) (height * 0.2) + (sampleHeight / 2) + ((sliderHeight + sliderSpacing) * buttonId) + sliderSpacing, sliderWidth, sliderHeight, new ChatComponentTranslation("quickplay.color.red").getFormattedText() + ": ", "", 0, 255, color.getRed(), false, true));
        buttonId++;
        buttonList.add(new GuiSlider(buttonId, width / 2 - sliderWidth / 2, (int) (height * 0.2) + (sampleHeight / 2) + ((sliderHeight + sliderSpacing) * buttonId) + sliderSpacing, sliderWidth, sliderHeight, new ChatComponentTranslation("quickplay.color.green").getFormattedText() + ": ", "", 0, 255, color.getGreen(), false, true));
        buttonId++;
        buttonList.add(new GuiSlider(buttonId, width / 2 - sliderWidth / 2, (int) (height * 0.2) + (sampleHeight / 2) + ((sliderHeight + sliderSpacing) * buttonId) + sliderSpacing, sliderWidth, sliderHeight, new ChatComponentTranslation("quickplay.color.blue").getFormattedText() + ": ", "", 0, 255, color.getBlue(), false, true));
        buttonId++;

        // Chroma Button
        buttonList.add(new GuiButton(buttonId, width / 2 - sliderWidth / 2, (int) (height * 0.2) + (sampleHeight / 2) + ((sliderHeight + sliderSpacing) * buttonId) + sliderSpacing, new ChatComponentTranslation("quickplay.color.chroma").getFormattedText()+ ": " + (color.getIsChroma() ? new ChatComponentTranslation("quickplay.config.enabled").getFormattedText() : new ChatComponentTranslation("quickplay.config.disabled").getFormattedText())));
        buttonId++;

        // Buttons on the bottom of the
        // screen (save & cancel)
        // Save button
        int bottomButtonWidth = 100 - sliderSpacing / 2 ;
        buttonList.add(new GuiButton(buttonId, width / 2 - bottomButtonWidth - sliderSpacing / 2, (int) (height * 0.2) + (sampleHeight / 2) + ((sliderHeight + sliderSpacing) * buttonId) + sliderSpacing, bottomButtonWidth, 20, new ChatComponentTranslation("quickplay.config.cancel").getFormattedText()));
        buttonId++;
        // Button height is based on button ID, so to put
        // the button on the same row, buttonId - 1 is used
        // in this line of code.
        buttonList.add(new GuiButton(buttonId, width / 2 + sliderSpacing / 2, (int) (height * 0.2) + (sampleHeight / 2) + ((sliderHeight + sliderSpacing) * (buttonId - 1)) + sliderSpacing, bottomButtonWidth, 20, new ChatComponentTranslation("quickplay.config.save").getFormattedText()));

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        // If the button pressed was not any of the color selectors
        if(button.id > 2) {
            // Chroma button was pressed
            if(button.id == 3) {
                color.setIsChroma(!color.getIsChroma());
                buttonList.get(3).displayString = new ChatComponentTranslation("quickplay.color.chroma").getFormattedText() + ": " + (color.getIsChroma() ? new ChatComponentTranslation("quickplay.config.enabled").getFormattedText() : new ChatComponentTranslation("quickplay.config.disabled").getFormattedText());
            } else
            // Save or Cancel button was pressed
            if(button.id == 4 || button.id == 5) {

                // If button was save button
                if(button.id == 5) {
                    QuickPlay.configManager.getConfig().colors.put(color.getUnlocalizedName(), color);
                    QuickPlay.configManager.saveConfig();
                }
                // Close the GUI
                openGui(new ConfigGui(2));
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        // This GUI does not obey key close settings
        //obeySettings();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onGameTick(TickEvent.ClientTickEvent event) {
        // Update the draw color to the next chroma frame if chroma is enabled
        if(color.getIsChroma() && drawColor != null) {
            drawColor = new QuickPlayColor(QuickPlayColor.nextChromaFrame(drawColor), drawColor.getUnlocalizedName(), drawColor.getIsChroma());
        }
    }
}
