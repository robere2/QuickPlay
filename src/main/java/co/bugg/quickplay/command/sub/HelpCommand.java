package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends AbstractSubCommand {

    public HelpCommand(QpBaseCommand parent) {
        super(parent, "help", "Displays a list and explanation of all commands.", "[command]");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(args.length < 2) {
            ITextComponent separator = new TextComponentTranslation("quickplay.command.separator");
            Style separatorStyle = new Style();
            separatorStyle.setColor(TextFormatting.GOLD);
            separator.setStyle(separatorStyle);

            ITextComponent helpMessage = new TextComponentString("");

            helpMessage.appendSibling(separator);
            helpMessage.appendText("\n");
            helpMessage.appendSibling(new TextComponentTranslation("quickplay.command.help.title").setStyle(new Style().setColor(TextFormatting.GRAY)));
            helpMessage.appendText("\n");

            for (AbstractSubCommand command : this.getParent().subCommands) {
                helpMessage.appendSibling(command.getFormattedHelp());
            }

            helpMessage.appendSibling(separator);

            sender.addChatMessage(helpMessage);
        } else {
            AbstractSubCommand command = getParent().getCommand(args[1]);
            if(command == null) {
                sender.addChatMessage(new TextComponentTranslation("quickplay.command.unknown").setStyle(new Style().setColor(TextFormatting.RED)));
            } else {
                sender.addChatMessage(command.getFormattedUsage());
            }
        }
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> tabCompletions = new ArrayList<>();

        if(args.length < 3) {
            for(AbstractSubCommand command : getParent().subCommands) {
                if(command.getName().startsWith(args[args.length - 1])) {
                    tabCompletions.add(command.getName());
                }
            }
        }

        return tabCompletions;
    }
}
