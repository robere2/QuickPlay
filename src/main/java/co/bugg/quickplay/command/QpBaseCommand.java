package co.bugg.quickplay.command;

import co.bugg.quickplay.command.sub.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class QpBaseCommand implements ICommand {

    public LinkedList<AbstractSubCommand> subCommands = new LinkedList<>();

    public QpBaseCommand() {
        subCommands.add(new HelpCommand(this));
        subCommands.add(new ConfigCommand(this));
        // subCommands.add(new ColorCommand(this));
        subCommands.add(new PartyCommand(this));
        subCommands.add(new LimboCommand(this));
        subCommands.add(new DebugCommand(this));
        subCommands.add(new DestroyConfigCommand(this));
    }

    @Override
    public String getName() {
        return "qp";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/qp <args...>";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("quickplay");
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length > 0) {
            AbstractSubCommand command = getCommand(args[0]);
            if(command != null) {
                command.run(sender, args);
            } else {
                // Command not found
                sender.sendMessage(new TextComponentTranslation("quickplay.command.unknown").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        } else {
            // No args provided, run the help command if possible
            AbstractSubCommand helpCommand = getCommand("help");
            if(helpCommand != null) {
                helpCommand.run(sender, args);
            } else {
                sender.sendMessage(new TextComponentTranslation("quickplay.command.unknown").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>();

        if(args.length > 1) {
            AbstractSubCommand command = getCommand(args[0]);
            if(command != null) {
                tabCompletions.addAll(command.getTabCompletions(sender, args, targetPos));
            }
        } else {
            for (AbstractSubCommand subCommand : this.subCommands) {
                if (subCommand.getName().startsWith(args[args.length - 1])) {
                    tabCompletions.add(subCommand.getName());
                }
            }
        }

        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    /**
     * Get the subcommand with the provided name
     * @param name Name of the sub command
     * @return Sub command, null if command doesn't exist
     */
    @Nullable
    public AbstractSubCommand getCommand(String name) {
        for(AbstractSubCommand command : subCommands) {
            if(command.name.equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }
}
