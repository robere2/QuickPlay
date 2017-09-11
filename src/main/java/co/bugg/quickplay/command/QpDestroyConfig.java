package co.bugg.quickplay.command;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
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
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 1) {
            sender.addChatMessage(new ChatComponentText(new ChatComponentTranslation("quickplay.message.config_reset_confirm", ChatFormatting.RED + Reference.MOD_NAME, ChatFormatting.AQUA + "/" + getCommandName() + " yes").getFormattedText()));
        } else if(args.length == 1) {
            for(String arg: args) {
                if(arg.equals("yes")) {
                    QuickPlay.configManager.resetConfig();
                    sender.addChatMessage(new ChatComponentText(new ChatComponentTranslation("quickplay.message.config_reset").getFormattedText()));
                }
            }
        } else {
            sender.addChatMessage(new ChatComponentText(new ChatComponentTranslation("quickplay.message.invalid_syntax").getFormattedText() + getCommandUsage(sender)));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
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
