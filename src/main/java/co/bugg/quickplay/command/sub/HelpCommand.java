package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.command.QpSubCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.*;

public class HelpCommand extends QpSubCommand {

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
            helpMessage.appendSibling(new TextComponentTranslation("quickplay.command.help.title"));
            helpMessage.appendText("\n");

            for (QpSubCommand command : this.getParent().subCommands) {
                helpMessage.appendSibling(command.getFormattedHelp());
            }

            helpMessage.appendSibling(separator);

            sender.addChatMessage(helpMessage);
        } else {
            QpSubCommand command = getParent().getCommand(args[1]);
            if(command == null) {
                sender.addChatMessage(new TextComponentTranslation("quickplay.command.unknown"));
            } else {
                sender.addChatMessage(command.getFormattedUsage());
            }
        }
    }
}
