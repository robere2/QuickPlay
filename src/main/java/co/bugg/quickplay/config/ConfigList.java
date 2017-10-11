package co.bugg.quickplay.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"DeprecatedIsStillUsed", "unused", "UnnecessaryLocalVariable", "deprecation", "ConstantConditions", "SameParameterValue"})
public class ConfigList extends AbstractConfigList{

    List<Field> options = new ArrayList<>();

    @Deprecated // We need to know screen size.
    public ConfigList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, ConfigManager manager)
    {
        this(client, width, height, top, bottom, left, entryHeight, width, height, manager);
    }
    public ConfigList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight, ConfigManager manager)
    {
        super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight, manager);

        // Add all annotated options to the list of config favorites
        Field[] allOptions = manager.getConfig().getClass().getFields();
        for(Field option : allOptions) {
            if(option.getAnnotation(GuiOption.class) != null) {
                options.add(option);
            }
        }

        // Sort the list to be highest priority first
        options.sort((o1, o2) -> (int) (o2.getAnnotation(GuiOption.class).priority() - o1.getAnnotation(GuiOption.class).priority()));

        addButtons();
    }

    /**
     * Go through the list of options and
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
            try {
                button = new GuiButton(i, 0, 0, buttonWidth, buttonHeight, (options.get(i).getBoolean(manager.getConfig())) ? enabled : disabled);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            buttonList.put(i, button);

            GuiButton resetButton = new GuiButton(i, 0, 0, resetButtonWidth, buttonHeight, reset);
            resetButtonList.put(i, resetButton);

            try {
                checkResetCapability(i);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            i++;
        }
    }

    protected int getSize() {
        return options.size();
    }

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
        GuiButton resetButton = resetButtonList.get(index);
        if((resetButton.xPosition < mouseX && mouseX < resetButton.xPosition + resetButton.width) && (resetButton.yPosition < mouseY && mouseY < resetButton.yPosition + resetButton.height)) {
            actionPerformed(resetButton);
        }
    }

    /**
     * Draw anything special on the screen. GL_SCISSOR is enabled for anything that
     * is rendered outside of the view box. Do not mess with SCISSOR unless you support this.
     */
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, int mouseX, int mouseY, Tessellator tess) {
        GuiOption info = options.get(slotIdx).getAnnotation(GuiOption.class);

        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

        int stringX = 20;
        int stringY = slotTop + 5;
        int stringHeight = font.FONT_HEIGHT;
        int stringWidth = font.getStringWidth(info.name());
        // Draw the string
        font.drawString(info.name(), stringX, stringY, 0xFFFFFF);

        buttonX = (int) (listWidth * 0.55);
        buttonY = slotTop;
        resetButtonX = buttonX + buttonWidth + 5;

        GuiButton settingButton = buttonList.get(slotIdx);
        GuiButton resetButton = resetButtonList.get(slotIdx);

        settingButton.xPosition = buttonX;
        settingButton.yPosition = buttonY;
        resetButton.xPosition = resetButtonX;
        resetButton.yPosition = buttonY;

        settingButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        resetButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

        // FIXME: Tooltips are drawn underneath other items that appear lower on the list
        // Draw the description tooltip
        if((buttonX < mouseX && mouseX < (buttonX + buttonWidth)) && (buttonY < mouseY && mouseY < (buttonY + buttonHeight))) {
            List<String> hoverText = new ArrayList<>();
            hoverText.add(info.description());
            drawHoveringText(hoverText, mouseX, mouseY + 5, font);
        }

        // Draw the description
        //font.drawString(GameUtil.getTextWithEllipsis(listWidth, info.description()), 5, slotTop + 10, 0xBBBBBB);
    }

    public void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {

            try {
                Field option = options.get(button.id);
                GuiOption annotation = option.getAnnotation(GuiOption.class);

                switch(annotation.type()) {
                    default:
                    case BOOLEAN:
                        // If the button is a reset button
                        if(button.displayString.equals(reset)) {
                            // Set the value
                            boolean defaultValue = (boolean) manager.getDefaultValue(options.get(button.id).getName());
                            options.get(button.id).setBoolean(manager.getConfig(), defaultValue);

                            // Set the text
                            buttonList.get(button.id).displayString = defaultValue ? enabled : disabled;

                        } else {
                            if(option.getBoolean(manager.getConfig())) {
                                button.displayString = disabled;
                                option.setBoolean(manager.getConfig(), false);
                            } else {
                                button.displayString = enabled;
                                option.setBoolean(manager.getConfig(), true);
                            }
                        }

                        break;

                    case KEYBIND:

                        break;
                }

                checkResetCapability(button.id);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Enable/disable the reset button depending on
     * if the value is already at its default value
     * @param index Index of buttons to check
     * @throws IllegalAccessException when the field can't be accessed
     */
    public void checkResetCapability(int index) throws IllegalAccessException {
        Field option = options.get(index);

        if(option.getBoolean(manager.getConfig()) == (boolean) manager.getDefaultValue(option.getName())) {
            resetButtonList.get(index).enabled = false;
        } else {
            resetButtonList.get(index).enabled = true;
        }
    }
}
