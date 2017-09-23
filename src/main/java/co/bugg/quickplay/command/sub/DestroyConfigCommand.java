package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.command.QpSubCommand;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;

public class DestroyConfigCommand extends QpSubCommand {

    static String confirmWord = "yes";

    public DestroyConfigCommand(QpBaseCommand parent) {
        super(parent, "destroyconfig", "Reset your " + Reference.MOD_NAME + " configuration.", "<" + confirmWord + ">");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.addChatMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm", ChatFormatting.RED + Reference.MOD_NAME, ChatFormatting.AQUA + getConfirmCommand()));
        } else {
            if(args[1].equalsIgnoreCase(confirmWord)) {
                QuickPlay.configManager.resetConfig();
                sender.addChatMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset"));
            } else {
                sender.addChatMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm", ChatFormatting.RED + Reference.MOD_NAME, ChatFormatting.AQUA + getConfirmCommand()));
            }
        }
    }

    public String getConfirmCommand() {
        return "/" + getParent().getCommandName() + " " + getName() + " " + confirmWord;
    }
}
