package co.bugg.quickplay.config;

import co.bugg.quickplay.gui.ColorGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import co.bugg.quickplay.util.QuickPlayColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"DeprecatedIsStillUsed", "unused", "UnnecessaryLocalVariable", "deprecation", "ConstantConditions", "SameParameterValue"})
public class ColorList extends AbstractConfigList {

    List<QuickPlayColor> colors = new ArrayList<>();

    @Deprecated // We need to know screen size.
    public ColorList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, ConfigManager manager)
    {
        this(client, width, height, top, bottom, left, entryHeight, width, height, manager);
    }
    public ColorList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight, ConfigManager manager)
    {
        super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight, manager);

        for(HashMap.Entry<String, QuickPlayColor> entry : manager.getConfig().colors.entrySet()) {
            colors.add(entry.getValue());
        }

        addButtons();

        // Register this as an event listener, so it
        // updates relevant information every tick
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Go through the list of colors and
     * add the buttons for each one
     */
    public void addButtons() {
        // i represents the button ID. Same button ID is applied to
        // both the reset button and setting button, and a check is
        // performed whenever one of them is clicked.
        int i = 0;
        while(i < getSize()) {

            // Create all the button instances
            GuiButton button = null;
            // These buttons should be wider
            buttonWidth = 200;
            button = new GuiButton(i, 0, 0, buttonWidth, buttonHeight, new ChatComponentTranslation("quickplay.color.name." + colors.get(i).getUnlocalizedName()).getUnformattedText());
            buttonList.put(i, button);

            // Reset buttons are not included in this list

            try {
                checkResetCapability(i);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            i++;
        }
    }

    protected int getSize() {
        return colors.size();
    }

    /**
     * Fired whenever an element on the screen is clicked
     * @param index Index of the element
     * @param doubleClick Whether it was a double click
     * @param mouseX Mouse X location
     * @param mouseY Mouse Y location
     */
    protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
        // Check if the mouse is over a button
        // when the element was clicked. If so,
        // then call actionPerformed(). Only
        // checks within the element, as buttons
        // shouldn't be outside of it.

        GuiButton button = buttonList.get(index);
        if((button.xPosition < mouseX && mouseX < button.xPosition + button.width) && (button.yPosition < mouseY && mouseY < button.yPosition + button.height)) {
            actionPerformed(button);
        }
    }

    /**
     * Draw anything special on the screen. GL_SCISSOR is enabled for anything that
     * is rendered outside of the view box. Do not mess with SCISSOR unless you support this.
     */
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, int mouseX, int mouseY, Tessellator tess) {
        QuickPlayColor color = colors.get(slotIdx);

        GuiButton settingButton = buttonList.get(slotIdx);

        buttonX = listWidth / 2 - settingButton.width / 2;
        buttonY = slotTop;

        settingButton.xPosition = buttonX;
        settingButton.yPosition = buttonY;

        settingButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    /**
     * Callback for when a button is pressed
     * @param button Pressed button
     */
    public void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            QuickPlayGui.openGui(new ColorGui(button.id, colors.get(button.id)));
        }
    }

    @Override
    public void checkResetCapability(int index) throws IllegalAccessException {
        // Buttons can't be reset in this GUI.
    }
}
