package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.Reference;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.config.ConfigGui;
import co.bugg.quickplay.util.TickDelay;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;

public class ConfigCommand extends AbstractSubCommand {

    public ConfigCommand(QpBaseCommand parent) {
        super(parent, "config", "Open the main " + Reference.MOD_NAME + " configuration.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        new TickDelay(() -> Minecraft.getMinecraft().displayGuiScreen(new ConfigGui()), 2);
    }
}
