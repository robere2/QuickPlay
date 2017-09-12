package co.bugg.quickplay.command;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class QpDestroyConfig implements ICommand {
    @Override
    public String getCommandName() {
        return "qpdestroyconfig";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qpdestroyconfig <yes>";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 1) {
            sender.addChatMessage(new TextComponentString(new TextComponentTranslation("quickplay.message.config_reset_confirm", TextFormatting.RED + Reference.MOD_NAME, TextFormatting.AQUA + "/" + getCommandName() + " yes").getFormattedText()));
        } else if(args.length == 1) {
            for(String arg: args) {
                if(arg.equals("yes")) {
                    QuickPlay.configManager.resetConfig();
                    sender.addChatMessage(new TextComponentString(new TextComponentTranslation("quickplay.message.config_reset").getFormattedText()));
                }
            }
        } else {
            sender.addChatMessage(new TextComponentString(new TextComponentTranslation("quickplay.message.invalid_syntax").getFormattedText() + getCommandUsage(sender)));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
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
