package co.bugg.quickplay.config;

import co.bugg.quickplay.QuickPlay;
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

        //
        Field[] allOptions = QuickPlay.configManager.getConfig().getClass().getFields();
        for(Field option : allOptions) {
            if(option.getAnnotation(GuiOption.class) != null) {
                System.out.println("Annotated: " + option.getName());
                options.add(option);
            }
        }
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
        Minecraft.getMinecraft().fontRendererObj.drawString(options.get(slotIdx).getAnnotation(GuiOption.class).name(), 5, slotTop, 0xFFFFFF);
        Minecraft.getMinecraft().fontRendererObj.drawString(options.get(slotIdx).getAnnotation(GuiOption.class).description(), 5, slotTop + 10, 0xCCCCCC);
    }

    @Override
    public void actionPerformed(GuiButton button) {

        super.actionPerformed(button);
    }
}
