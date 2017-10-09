package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.gui.button.ArrowButton;
import co.bugg.quickplay.util.QuickPlayColor;
import net.minecraft.client.gui.GuiButton;
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



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

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
        drawCenteredString(fontRendererObj, new ChatComponentTranslation("quickplay.color.name." + color.getUnlocalizedName()).getFormattedText(), width / 2, (int) (height * 0.2) - (sampleHeight / 2) - 12, drawColor.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();

        int sliderSpacing = 5;
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
        // Save button
        buttonList.add(new GuiButton(buttonId, width / 2 - sliderWidth / 2, (int) (height * 0.2) + (sampleHeight / 2) + ((sliderHeight + sliderSpacing) * buttonId) + sliderSpacing, new ChatComponentTranslation("quickplay.config.save").getFormattedText()));
        buttonId++;

        // Create the back button
        buttonList.add(new ArrowButton(buttonId, (width / 2) - (sampleWidth / 2) - ArrowButton.width - sliderSpacing, (int) (height * 0.2) - ArrowButton.height / 2, 0));
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
            // Save button was pressed
            if(button.id == 4) {
                QuickPlay.configManager.getConfig().colors.put(color.getUnlocalizedName(), color);
                QuickPlay.configManager.saveConfig();
            } else
            // Probably the back button was pressed
            {
                openGui(new MainColorGui());
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
