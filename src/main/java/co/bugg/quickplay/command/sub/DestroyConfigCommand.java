package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DestroyConfigCommand extends AbstractSubCommand {

    static String confirmWord = "yes";

    public DestroyConfigCommand(QpBaseCommand parent) {
        super(parent, "destroyconfig", "Reset your " + Reference.MOD_NAME + " configuration.", "<" + confirmWord + ">");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.addChatMessage(new ChatComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm",  Reference.MOD_NAME, getConfirmCommand()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        } else {
            if(args[1].equalsIgnoreCase(confirmWord)) {
                QuickPlay.configManager.resetConfig();
                sender.addChatMessage(new ChatComponentTranslation("quickplay.command.destroyconfig.config_reset").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
            } else {
                sender.addChatMessage(new ChatComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm", Reference.MOD_NAME, getConfirmCommand()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            }
        }
    }

    public String getConfirmCommand() {
        return "/" + getParent().getCommandName() + " " + getName() + " " + confirmWord;
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> tabCompletions = new ArrayList<>();
        if(confirmWord.startsWith(args[args.length - 1])) {
            tabCompletions.add(confirmWord);
        }
        return tabCompletions;
    }
}
