package co.bugg.quickplay.command;

import co.bugg.quickplay.command.sub.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class QpBaseCommand implements ICommand {

    public LinkedList<QpSubCommand> subCommands = new LinkedList<>();

    public QpBaseCommand() {
        subCommands.add(new HelpCommand(this));
        subCommands.add(new ColorCommand(this));
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
            QpSubCommand command = getCommand(args[0]);
            if(command != null) {
                command.run(sender, args);
            } else {
                // Command not found
                sender.sendMessage(new TextComponentTranslation("quickplay.command.unknown"));
            }
        } else {
            // No args provided, run the help command if possible
            QpSubCommand helpCommand = getCommand("help");
            if(helpCommand != null) {
                helpCommand.run(sender, args);
            } else {
                sender.sendMessage(new TextComponentTranslation("quickplay.command.unknown"));
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

        for(QpSubCommand subCommand : this.subCommands) {
            tabCompletions.add(subCommand.getName());
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
    public QpSubCommand getCommand(String name) {
        for(QpSubCommand command : subCommands) {
            if(command.name.equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }
}
