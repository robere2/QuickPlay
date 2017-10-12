package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

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
            sender.sendMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm", Reference.MOD_NAME, getConfirmCommand()).setStyle(new Style().setColor(TextFormatting.RED)));
        } else {
            if(args[1].equalsIgnoreCase(confirmWord)) {
                QuickPlay.configManager.resetConfig();
                sender.sendMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset").setStyle(new Style().setColor(TextFormatting.GREEN)));
            } else {
                sender.sendMessage(new TextComponentTranslation("quickplay.command.destroyconfig.config_reset_confirm", Reference.MOD_NAME, getConfirmCommand()).setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
    }

    public String getConfirmCommand() {
        return "/" + getParent().getName() + " " + getName() + " " + confirmWord;
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
