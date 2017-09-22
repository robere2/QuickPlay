package co.bugg.quickplay.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public abstract class QpSubCommand {

    QpBaseCommand parent;

    String name;
    String help;
    String usage;

    public QpSubCommand(QpBaseCommand parent, String name, String help, String usage) {
        this.parent = parent;

        this.name = name;
        this.help = help;
        this.usage = usage;
    }

    public abstract void run(ICommandSender sender, String[] args);

    /**
     * Get a formatted help message (with the line return)
     * to display in the help menu
     * @return IChatComponent of the help message
     */
    public IChatComponent getFormattedHelp() {
        IChatComponent command = new ChatComponentText("/");
        command.appendText(getParent().getCommandName());
        command.appendText(" ");
        command.appendText(getName());

        ChatStyle commandStyle = new ChatStyle();
        commandStyle.setColor(EnumChatFormatting.AQUA);
        commandStyle.setItalic(true);
        commandStyle.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + getParent().getCommandName() + " " + getName() + " "));
        commandStyle.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to put command in chat.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true))));

        command.setChatStyle(commandStyle);

        IChatComponent separator = new ChatComponentText(" - ");
        separator.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY));

        IChatComponent helpText = new ChatComponentText(getHelp());
        helpText.appendText("\n");
        helpText.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));

        IChatComponent message = new ChatComponentText("");
        message.appendSibling(command);
        message.appendSibling(separator);
        message.appendSibling(helpText);

        return message;
    }

    /**
     * Get a formatted usage message (with the line return)
     * to display in scenarios such as /help and invalid syntax
     * @return IChatComponent of the help message
     */
    public IChatComponent getFormattedUsage() {
        IChatComponent message = new ChatComponentText("Syntax:\n");
        message.appendText("/");
        message.appendText(getParent().getCommandName());
        message.appendText(" ");
        message.appendText(getName());
        message.appendText(" ");
        message.appendText(getUsage());
        message.appendText("\n");

        ChatStyle style = new ChatStyle();
        style.setColor(EnumChatFormatting.RED);

        message.setChatStyle(style);

        return message;
    }


    public QpBaseCommand getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public String getUsage() {
        return usage;
    }
}
