package co.bugg.quickplay.config;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.util.GameUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigList extends GuiScrollingList {

    List<Field> options = new ArrayList<>();

    public ConfigList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight) {
        super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);

        // Add all annotated options to the list of config options
        Field[] allOptions = QuickPlay.configManager.getConfig().getClass().getFields();
        for(Field option : allOptions) {
            if(option.getAnnotation(GuiOption.class) != null) {
                options.add(option);
            }
        }

        // Sort the list to be highest priority first
        options.sort((o1, o2) -> (int) (o2.getAnnotation(GuiOption.class).priority() - o1.getAnnotation(GuiOption.class).priority()));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @Override
    protected int getSize() {
        return options.size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        System.out.println("Clicked!");
    }

    @Override
    protected boolean isSelected(int index) {
        return false;
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        // TODO: Make hovering over slots with ellipsis display the whole thing. Will probably have to be split onto multiple lines on the tooltip
        GuiOption info = options.get(slotIdx).getAnnotation(GuiOption.class);
        Minecraft.getMinecraft().fontRendererObj.drawString(GameUtil.getTextWithEllipsis(listWidth, info.name()), 5, slotTop, 0xFFFFFF);
        Minecraft.getMinecraft().fontRendererObj.drawString(GameUtil.getTextWithEllipsis(listWidth, info.description()), 5, slotTop + 10, 0xBBBBBB);
    }

    @Override
    public void actionPerformed(GuiButton button) {

        super.actionPerformed(button);
    }
}
