package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

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
            IChatComponent separator = new ChatComponentTranslation("quickplay.command.separator");
            ChatStyle separatorStyle = new ChatStyle();
            separatorStyle.setColor(EnumChatFormatting.GOLD);
            separator.setChatStyle(separatorStyle);

            IChatComponent helpMessage = new ChatComponentText("");

            helpMessage.appendSibling(separator);
            helpMessage.appendText("\n");
            helpMessage.appendSibling(new ChatComponentTranslation("quickplay.command.help.title").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
            helpMessage.appendText("\n");

            for (AbstractSubCommand command : this.getParent().subCommands) {
                helpMessage.appendSibling(command.getFormattedHelp());
            }

            helpMessage.appendSibling(separator);

            sender.addChatMessage(helpMessage);
        } else {
            AbstractSubCommand command = getParent().getCommand(args[1]);
            if(command == null) {
                sender.addChatMessage(new ChatComponentTranslation("quickplay.command.unknown").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
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
