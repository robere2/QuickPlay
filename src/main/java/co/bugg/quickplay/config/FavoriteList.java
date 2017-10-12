package co.bugg.quickplay.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"DeprecatedIsStillUsed", "unused", "UnnecessaryLocalVariable", "deprecation", "ConstantConditions", "SameParameterValue"})
public class FavoriteList extends AbstractConfigList {

    List<Favorite> favorites = new ArrayList<>();

    @Deprecated // We need to know screen size.
    public FavoriteList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, ConfigManager manager)
    {
        this(client, width, height, top, bottom, left, entryHeight, width, height, manager);
    }
    public FavoriteList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight, ConfigManager manager)
    {
        super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight, manager);

        favorites = manager.getConfig().favorites;

        addButtons();

        // Register this as an event listener, so it
        // updates relevant information every tick
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Go through the list of favorites and
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
            button = new GuiButton(i, 0, 0, buttonWidth, buttonHeight, Keyboard.getKeyName(favorites.get(i).getKeyCode()));
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
        return favorites.size();
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
        if((button.x < mouseX && mouseX < button.x + button.width) && (button.y < mouseY && mouseY < button.y + button.height)) {
            actionPerformed(button);
        }
        GuiButton resetButton = resetButtonList.get(index);
        if((resetButton.x < mouseX && mouseX < resetButton.x + resetButton.width) && (resetButton.y < mouseY && mouseY < resetButton.y + resetButton.height)) {
            actionPerformed(resetButton);
        }
    }

    /**
     * Draw anything special on the screen. GL_SCISSOR is enabled for anything that
     * is rendered outside of the view box. Do not mess with SCISSOR unless you support this.
     */
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, int mouseX, int mouseY, Tessellator tess) {
        Favorite favorite = favorites.get(slotIdx);
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        int stringX = 20;
        int stringY = slotTop + 5;
        int stringHeight = font.FONT_HEIGHT;
        int stringWidth = font.getStringWidth(favorite.getGame().name);
        // Draw the string
        font.drawString(favorite.getGame().name, stringX, stringY, 0xFFFFFF);

        buttonX = (int) (listWidth * 0.55);
        buttonY = slotTop;
        resetButtonX = buttonX + buttonWidth + 5;

        GuiButton settingButton = buttonList.get(slotIdx);
        GuiButton resetButton = resetButtonList.get(slotIdx);

        settingButton.x = buttonX;
        settingButton.y = buttonY;
        resetButton.x = resetButtonX;
        resetButton.y = buttonY;

        settingButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY, 0);
        resetButton.drawButton(Minecraft.getMinecraft(), mouseX, mouseY, 0);

//        // FIXME: Tooltips are drawn underneath other items that appear lower on the list
//        // Draw the description tooltip
//        if((buttonX < mouseX && mouseX < (buttonX + buttonWidth)) && (buttonY < mouseY && mouseY < (buttonY + buttonHeight))) {
//            List<String> hoverText = new ArrayList<>();
//            hoverText.add(favorite.getGame().name + " Favorite keybind");
//            drawHoveringText(hoverText, mouseX, mouseY + 5, font);
//        }
    }

    /**
     * Callback for when a button is pressed
     * @param button Pressed button
     */
    public void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            Favorite favorite = favorites.get(button.id);

            // If the button is a reset button
            if(button.displayString.equals(reset)) {
                // Set the value
                favorite.setKeyCode(0);
                // Reload all buttons
                reloadButtons();
            } else {
                listen(button);
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
        Favorite favorite = favorites.get(index);

        if(favorite.getKeyCode() == 0) {
            resetButtonList.get(index).enabled = false;
        } else {
            resetButtonList.get(index).enabled = true;
        }
    }

    /**
     * Listen for key input for the provided button
     * @param button Button that is listening
     */
    public void listen(GuiButton button) {
        Favorite favorite = favorites.get(button.id);

        // Stop listening on all buttons
        for(int i = 0; i < buttonList.size(); i++) {
            favorites.get(i).stopListening();
        }

        // Listen for a key press & set it to
        // the key code on the button specified
        favorite.listen();

        // Reload all buttons display texts and reset capability
        reloadButtons();
    }

    /**
     * Reload the display text, whether reset buttons
     * are enabled, etc. for each button on screen.
     */
    public void reloadButtons() {
        for(int i = 0; i < favorites.size(); i++) {
            try {
                // Double check the reset capability of
                // each button
                checkResetCapability(i);

                if(favorites.get(i).isListening()) {
                    buttonList.get(i).displayString = "> " + TextFormatting.YELLOW  + Keyboard.getKeyName(favorites.get(i).getKeyCode()) + TextFormatting.RESET +  " <";
                } else {
                    TextFormatting color;
                    color = (keyTaken(favorites.get(i))) ? TextFormatting.RED : TextFormatting.RESET;

                    buttonList.get(i).displayString = color + "" + Keyboard.getKeyName(favorites.get(i).getKeyCode());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the provided favorite's key
     * is used somewhere else as well.
     * @param favorite Favorite to check
     * @return boolean Whether the key is used
     */
    public boolean keyTaken(Favorite favorite) {
        if(favorite.getKeyCode() != 0) {
            for (Favorite loopFavorite : favorites) {
                if (!loopFavorite.equals(favorite) && loopFavorite.getKeyCode() == favorite.getKeyCode()) {
                    return true;
                }
            }
        }

        return false;
    }

    @SubscribeEvent
    public void onGameTick(TickEvent.ClientTickEvent event) {
        reloadButtons();
        // If the current GUI isn't a config GUI
        // anymore, then we can stop listening
        if(!(Minecraft.getMinecraft().currentScreen instanceof ConfigGui)) {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
