package co.bugg.quickplay.command;

import co.bugg.quickplay.QuickPlay;
import com.mojang.realmsclient.gui.ChatFormatting;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class QpDestroyConfig implements ICommand {
    @Override
    public String getName() {
        return "qpdestroyconfig";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/qpdestroyconfig <yes>";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 1) {
            sender.sendMessage(new TextComponentString(ChatFormatting.RED + "Are you sure you want to reset your QuickPlay configuration? Type \"/" + getName() + " yes\" if so."));
        } else if(args.length == 1) {
            for(String arg: args) {
                if(arg.equals("yes")) {
                    QuickPlay.configManager.resetConfig();
                    sender.sendMessage(new TextComponentString(ChatFormatting.GREEN + "Configuration reset! You may have to restart your game."));
                }
            }
        } else {
            sender.sendMessage(new TextComponentString(ChatFormatting.RED + "Invalid syntax! " + getUsage(sender)));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
