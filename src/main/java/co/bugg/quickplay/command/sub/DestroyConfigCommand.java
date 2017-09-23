package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.command.QpSubCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class DestroyConfigCommand extends QpSubCommand {

    static String confirmWord = "yes";

    public DestroyConfigCommand(QpBaseCommand parent) {
        super(parent, "destroyconfig", "Reset your " + Reference.MOD_NAME + " configuration.", "<" + confirmWord + ">");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.addChatMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm", Reference.MOD_NAME, getConfirmCommand()).setStyle(new Style().setColor(TextFormatting.RED)));
        } else {
            if(args[1].equalsIgnoreCase(confirmWord)) {
                QuickPlay.configManager.resetConfig();
                sender.addChatMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset").setStyle(new Style().setColor(TextFormatting.GREEN)));
            } else {
                sender.addChatMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm", Reference.MOD_NAME, getConfirmCommand()).setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
    }

    public String getConfirmCommand() {
        return "/" + getParent().getCommandName() + " " + getName() + " " + confirmWord;
    }
}
