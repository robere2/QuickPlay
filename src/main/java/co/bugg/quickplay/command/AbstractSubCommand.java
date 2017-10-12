package co.bugg.quickplay.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class AbstractSubCommand {

    QpBaseCommand parent;

    String name;
    String help;
    String usage;

    public AbstractSubCommand(QpBaseCommand parent, String name, String help, String usage) {
        this.parent = parent;

        this.name = name;
        this.help = help;
        this.usage = usage;
    }

    public abstract void run(ICommandSender sender, String[] args);

    @Nonnull
    public abstract List<String> getTabCompletions(ICommandSender sender, String[] args, BlockPos pos);


    /**
     * Get a formatted help message (with the line return)
     * to display in the help menu
     * @return IChatComponent of the help message
     */
    public ITextComponent getFormattedHelp() {
        ITextComponent command = new TextComponentString("/");
        command.appendText(getParent().getCommandName());
        command.appendText(" ");
        command.appendText(getName());

        Style commandStyle = new Style();
        commandStyle.setColor(TextFormatting.AQUA);
        commandStyle.setItalic(true);
        commandStyle.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + getParent().getCommandName() + " " + getName() + " "));
        commandStyle.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to put command in chat.").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true))));

        command.setStyle(commandStyle);

        ITextComponent separator = new TextComponentString(" - ");
        separator.setStyle(new Style().setColor(TextFormatting.GRAY));

        ITextComponent helpText = new TextComponentString(getHelp());
        helpText.appendText("\n");
        helpText.setStyle(new Style().setColor(TextFormatting.YELLOW));

        ITextComponent message = new TextComponentString("");
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
    public ITextComponent getFormattedUsage() {
        ITextComponent message = new TextComponentString("Syntax:\n");
        message.appendText("/");
        message.appendText(getParent().getCommandName());
        message.appendText(" ");
        message.appendText(getName());
        message.appendText(" ");
        message.appendText(getUsage());
        message.appendText("\n");

        Style style = new Style();
        style.setColor(TextFormatting.RED);

        message.setStyle(style);

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
