package co.bugg.quickplay.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@ParametersAreNonnullByDefault
public class QpColorCommand implements ICommand {

    @Override
    public String getCommandName() {
        return "qpcolor";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qpcolor";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();

        aliases.add("qpc");

        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        sender.addChatMessage(new ChatComponentTranslation("quickplay.command.deprecated","/qp color").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
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
