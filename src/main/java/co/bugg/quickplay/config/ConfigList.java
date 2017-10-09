/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     cpw - implementation
 */

/*
 * Modified by bugfroggy for use in QuickPlay
 * Kind of bad practice, but ran out of options.
 */

package co.bugg.quickplay.config;

import co.bugg.quickplay.util.GameUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatComponentTranslation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"DeprecatedIsStillUsed", "unused", "UnnecessaryLocalVariable", "deprecation", "ConstantConditions", "SameParameterValue"})
public class ConfigList {
    private final Minecraft client;
    protected final int listWidth;
    protected final int listHeight;
    protected final int screenWidth;
    protected final int screenHeight;
    protected final int top;
    protected final int bottom;
    protected final int right;
    protected final int left;
    protected final int slotHeight;
    private int scrollUpActionId;
    private int scrollDownActionId;
    protected int mouseX;
    protected int mouseY;
    private float initialMouseClickY = -2.0F;
    private float scrollFactor;
    private float scrollDistance;
    protected int selectedIndex = -1;
    private long lastClickTime = 0L;
    private boolean highlightSelected = true;
    private boolean hasHeader;
    private int headerHeight;
    protected boolean captureMouse = true;

    List<Field> options = new ArrayList<>();
    ConfigManager manager;
    HashMap<Integer, GuiButton> buttonList = new HashMap<>();
    HashMap<Integer, GuiButton> resetButtonList = new HashMap<>();
    int buttonX;
    int buttonY;
    int buttonHeight = 20;
    int buttonWidth = 100;
    int resetButtonWidth = buttonWidth - 45;
    int resetButtonX = buttonX + buttonWidth + 5;

    // Button texts
    String enabled  = new ChatComponentTranslation("quickplay.config.enabled").getUnformattedText();
    String disabled = new ChatComponentTranslation("quickplay.config.disabled").getUnformattedText();
    String save =     new ChatComponentTranslation("quickplay.config.save").getUnformattedText();
    String cancel =   new ChatComponentTranslation("quickplay.config.cancel").getUnformattedText();
    String reset =    new ChatComponentTranslation("quickplay.config.reset").getUnformattedText();

    @Deprecated // We need to know screen size.
    public ConfigList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, ConfigManager manager)
    {
        this(client, width, height, top, bottom, left, entryHeight, width, height, manager);
    }
    public ConfigList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight, ConfigManager manager)
    {
        this.client = client;
        this.listWidth = width;
        this.listHeight = height;
        this.top = top;
        this.bottom = bottom;
        this.slotHeight = entryHeight;
        this.left = left;
        this.right = width + this.left;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.manager = manager;

        // Add all annotated options to the list of config options
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

    public void func_27258_a(boolean p_27258_1_)
    {
        this.highlightSelected = p_27258_1_;
    }

    @Deprecated protected void func_27259_a(boolean hasFooter, int footerHeight){ setHeaderInfo(hasFooter, footerHeight); }
    protected void setHeaderInfo(boolean hasHeader, int headerHeight)
    {
        this.hasHeader = hasHeader;
        this.headerHeight = headerHeight;
        if (!hasHeader) this.headerHeight = 0;
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

    protected boolean isSelected(int index) {

        return false;
    }

    protected int getContentHeight()
    {
        return this.getSize() * this.slotHeight + this.headerHeight;
    }

    protected void drawBackground() {

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
        font.drawString(GameUtil.getTextWithEllipsis(listWidth, info.name()), stringX, stringY, 0xFFFFFF);

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

    @Deprecated protected void func_27260_a(int entryRight, int relativeY, Tessellator tess) {}
    /**
     * Draw anything special on the screen. GL_SCISSOR is enabled for anything that
     * is rendered outside of the view box. Do not mess with SCISSOR unless you support this.
     */
    protected void drawHeader(int entryRight, int relativeY, Tessellator tess) { func_27260_a(entryRight, relativeY, tess); }

    @Deprecated protected void func_27255_a(int x, int y) {}
    protected void clickHeader(int x, int y) { func_27255_a(x, y); }

    @Deprecated protected void func_27257_b(int mouseX, int mouseY) {}
    /**
     * Draw anything special on the screen. GL_SCISSOR is enabled for anything that
     * is rendered outside of the view box. Do not mess with SCISSOR unless you support this.
     */
    protected void drawScreen(int mouseX, int mouseY) { func_27257_b(mouseX, mouseY); }

    public int func_27256_c(int x, int y)
    {
        int left = this.left + 1;
        int right = this.left + this.listWidth - 7;
        int relativeY = y - this.top - this.headerHeight + (int)this.scrollDistance - 4;
        int entryIndex = relativeY / this.slotHeight;
        return x >= left && x <= right && entryIndex >= 0 && relativeY >= 0 && entryIndex < this.getSize() ? entryIndex : -1;
    }

    public void registerScrollButtons(@SuppressWarnings("rawtypes") List buttons, int upActionID, int downActionID)
    {
        this.scrollUpActionId = upActionID;
        this.scrollDownActionId = downActionID;
    }

    private void applyScrollLimits()
    {
        int listHeight = this.getContentHeight() - (this.bottom - this.top - 4);

        if (listHeight < 0)
        {
            listHeight /= 2;
        }

        if (this.scrollDistance < 0.0F)
        {
            this.scrollDistance = 0.0F;
        }

        if (this.scrollDistance > (float)listHeight)
        {
            this.scrollDistance = (float)listHeight;
        }
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

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.drawBackground();

        boolean isHovering = mouseX >= this.left && mouseX <= this.left + this.listWidth &&
                mouseY >= this.top && mouseY <= this.bottom;
        int listLength     = this.getSize();
        int scrollBarWidth = 6;
        int scrollBarRight = this.left + this.listWidth;
        int scrollBarLeft  = scrollBarRight - scrollBarWidth;
        int entryLeft      = this.left;
        int entryRight     = scrollBarLeft - 1;
        int viewHeight     = this.bottom - this.top;
        int border         = 4;

        if (Mouse.isButtonDown(0))
        {
            if (this.initialMouseClickY == -1.0F)
            {
                if (isHovering)
                {
                    int mouseListY = mouseY - this.top - this.headerHeight + (int)this.scrollDistance - border;
                    int slotIndex = mouseListY / this.slotHeight;

                    if (mouseX >= entryLeft && mouseX <= entryRight && slotIndex >= 0 && mouseListY >= 0 && slotIndex < listLength)
                    {
                        this.elementClicked(slotIndex, slotIndex == this.selectedIndex && System.currentTimeMillis() - this.lastClickTime < 250L, mouseX, mouseY);
                        this.selectedIndex = slotIndex;
                        this.lastClickTime = System.currentTimeMillis();
                    }
                    else if (mouseX >= entryLeft && mouseX <= entryRight && mouseListY < 0)
                    {
                        this.clickHeader(mouseX - entryLeft, mouseY - this.top + (int)this.scrollDistance - border);
                    }

                    if (mouseX >= scrollBarLeft && mouseX <= scrollBarRight)
                    {
                        this.scrollFactor = -1.0F;
                        int scrollHeight = this.getContentHeight() - viewHeight - border;
                        if (scrollHeight < 1) scrollHeight = 1;

                        int var13 = (int)((float)(viewHeight * viewHeight) / (float)this.getContentHeight());

                        if (var13 < 32) var13 = 32;
                        if (var13 > viewHeight - border*2)
                            var13 = viewHeight - border*2;

                        this.scrollFactor /= (float)(viewHeight - var13) / (float)scrollHeight;
                    }
                    else
                    {
                        this.scrollFactor = 1.0F;
                    }

                    this.initialMouseClickY = mouseY;
                }
                else
                {
                    this.initialMouseClickY = -2.0F;
                }
            }
            else if (this.initialMouseClickY >= 0.0F)
            {
                this.scrollDistance -= ((float)mouseY - this.initialMouseClickY) * this.scrollFactor;
                this.initialMouseClickY = (float)mouseY;
            }
        }
        else
        {
            while (isHovering && Mouse.next())
            {
                int scroll = Mouse.getEventDWheel();
                if (scroll != 0)
                {
                    if      (scroll > 0) scroll = -1;
                    else if (scroll < 0) scroll =  1;

                    this.scrollDistance += (float)(scroll * this.slotHeight / 2);
                }
            }

            this.initialMouseClickY = -1.0F;
        }

        this.applyScrollLimits();

        Tessellator tess = Tessellator.getInstance();
        WorldRenderer worldr = tess.getWorldRenderer();

        ScaledResolution res = new ScaledResolution(client);
        double scaleW = client.displayWidth / res.getScaledWidth_double();
        double scaleH = client.displayHeight / res.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int)(left      * scaleW), (int)(client.displayHeight - (bottom * scaleH)),
                (int)(listWidth * scaleW), (int)(viewHeight * scaleH));


        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        this.client.renderEngine.bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float scale = 32.0F;
        worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldr.pos(this.left,  this.bottom, 0.0D).tex(this.left  / scale, (this.bottom + (int)this.scrollDistance) / scale).color(0x20, 0x20, 0x20, 0xFF).endVertex();
        worldr.pos(this.right, this.bottom, 0.0D).tex(this.right / scale, (this.bottom + (int)this.scrollDistance) / scale).color(0x20, 0x20, 0x20, 0xFF).endVertex();
        worldr.pos(this.right, this.top,    0.0D).tex(this.right / scale, (this.top    + (int)this.scrollDistance) / scale).color(0x20, 0x20, 0x20, 0xFF).endVertex();
        worldr.pos(this.left,  this.top,    0.0D).tex(this.left  / scale, (this.top    + (int)this.scrollDistance) / scale).color(0x20, 0x20, 0x20, 0xFF).endVertex();
        tess.draw();

        int baseY = this.top + border - (int)this.scrollDistance;

        if (this.hasHeader) {
            this.drawHeader(entryRight, baseY, tess);
        }

        for (int slotIdx = 0; slotIdx < listLength; ++slotIdx)
        {
            int slotTop = baseY + slotIdx * this.slotHeight + this.headerHeight;
            int slotBuffer = this.slotHeight - border;

            if (slotTop <= this.bottom && slotTop + slotBuffer >= this.top)
            {
                if (this.highlightSelected && this.isSelected(slotIdx))
                {
                    int min = this.left;
                    int max = entryRight;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.disableTexture2D();
                    worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    worldr.pos(min,     slotTop + slotBuffer + 2, 0).tex(0, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(max,     slotTop + slotBuffer + 2, 0).tex(1, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(max,     slotTop              - 2, 0).tex(1, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(min,     slotTop              - 2, 0).tex(0, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(min + 1, slotTop + slotBuffer + 1, 0).tex(0, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    worldr.pos(max - 1, slotTop + slotBuffer + 1, 0).tex(1, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    worldr.pos(max - 1, slotTop              - 1, 0).tex(1, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    worldr.pos(min + 1, slotTop              - 1, 0).tex(0, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    tess.draw();
                    GlStateManager.enableTexture2D();
                }

                this.drawSlot(slotIdx, entryRight, slotTop, slotBuffer, mouseX, mouseY, tess);
            }
        }

        GlStateManager.disableDepth();

        int extraHeight = this.getContentHeight() - viewHeight - border;
        if (extraHeight > 0)
        {
            int height = viewHeight * viewHeight / this.getContentHeight();

            if (height < 32) height = 32;

            if (height > viewHeight - border*2)
                height = viewHeight - border*2;

            int barTop = (int)this.scrollDistance * (viewHeight - height) / extraHeight + this.top;
            if (barTop < this.top)
            {
                barTop = this.top;
            }

            GlStateManager.disableTexture2D();
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldr.pos(scrollBarLeft,  this.bottom, 0.0D).tex(0.0D, 1.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            worldr.pos(scrollBarRight, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            worldr.pos(scrollBarRight, this.top,    0.0D).tex(1.0D, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            worldr.pos(scrollBarLeft,  this.top,    0.0D).tex(0.0D, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            tess.draw();
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldr.pos(scrollBarLeft,  barTop + height, 0.0D).tex(0.0D, 1.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            worldr.pos(scrollBarRight, barTop + height, 0.0D).tex(1.0D, 1.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            worldr.pos(scrollBarRight, barTop,          0.0D).tex(1.0D, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            worldr.pos(scrollBarLeft,  barTop,          0.0D).tex(0.0D, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            tess.draw();
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldr.pos(scrollBarLeft,      barTop + height - 1, 0.0D).tex(0.0D, 1.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            worldr.pos(scrollBarRight - 1, barTop + height - 1, 0.0D).tex(1.0D, 1.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            worldr.pos(scrollBarRight - 1, barTop,              0.0D).tex(1.0D, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            worldr.pos(scrollBarLeft,      barTop,              0.0D).tex(0.0D, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            tess.draw();
        }

        this.drawScreen(mouseX, mouseY);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);


    }

    protected void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
        net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, this.screenWidth, this.screenHeight, -1, font);
//        if (!textLines.isEmpty())
//        {
//            GlStateManager.disableRescaleNormal();
//            RenderHelper.disableStandardItemLighting();
//            GlStateManager.disableLighting();
//            GlStateManager.disableDepth();
//            int i = 0;
//
//            for (String s : textLines)
//            {
//                int j = font.getStringWidth(s);
//
//                if (j > i)
//                {
//                    i = j;
//                }
//            }
//
//            int l1 = x + 12;
//            int i2 = y - 12;
//            int k = 8;
//
//            if (textLines.size() > 1)
//            {
//                k += 2 + (textLines.size() - 1) * 10;
//            }
//
//            if (l1 + i > this.screenWidth)
//            {
//                l1 -= 28 + i;
//            }
//
//            if (i2 + k + 6 > this.screenHeight)
//            {
//                i2 = this.screenHeight - k - 6;
//            }
//
//            int l = -267386864;
//            this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
//            this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
//            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
//            this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
//            this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
//            int i1 = 1347420415;
//            int j1 = (i1 & 16711422) >> 1 | i1 & -16777216;
//            this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
//            this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
//            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
//            this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);
//
//            for (int k1 = 0; k1 < textLines.size(); ++k1)
//            {
//                String s1 = (String)textLines.get(k1);
//                font.drawStringWithShadow(s1, (float)l1, (float)i2, -1);
//
//                if (k1 == 0)
//                {
//                    i2 += 2;
//                }
//
//                i2 += 10;
//            }
//
//            GlStateManager.enableLighting();
//            GlStateManager.enableDepth();
//            RenderHelper.enableStandardItemLighting();
//            GlStateManager.enableRescaleNormal();
//        }
    }

    protected void drawGradientRect(int left, int top, int right, int bottom, int color1, int color2)
    {
        float a1 = (float)(color1 >> 24 & 255) / 255.0F;
        float r1 = (float)(color1 >> 16 & 255) / 255.0F;
        float g1 = (float)(color1 >>  8 & 255) / 255.0F;
        float b1 = (float)(color1       & 255) / 255.0F;
        float a2 = (float)(color2 >> 24 & 255) / 255.0F;
        float r2 = (float)(color2 >> 16 & 255) / 255.0F;
        float g2 = (float)(color2 >>  8 & 255) / 255.0F;
        float b2 = (float)(color2       & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0D).color(r1, g1, b1, a1).endVertex();
        worldrenderer.pos(left,  top, 0.0D).color(r1, g1, b1, a1).endVertex();
        worldrenderer.pos(left,  bottom, 0.0D).color(r2, g2, b2, a2).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).color(r2, g2, b2, a2).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
