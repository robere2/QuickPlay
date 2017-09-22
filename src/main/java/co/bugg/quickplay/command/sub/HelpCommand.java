package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.command.QpSubCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

public class HelpCommand extends QpSubCommand {

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
            helpMessage.appendSibling(new ChatComponentTranslation("quickplay.command.help.title"));
            helpMessage.appendText("\n");

            for (QpSubCommand command : this.getParent().subCommands) {
                helpMessage.appendSibling(command.getFormattedHelp());
            }

            helpMessage.appendSibling(separator);

            sender.addChatMessage(helpMessage);
        } else {
            sender.addChatMessage(getParent().getCommand(args[1]).getFormattedUsage());
        }
    }
}
