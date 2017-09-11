package co.bugg.quickplay.command;

import co.bugg.quickplay.QuickPlay;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
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
public class QpLimbo implements ICommand {
    @Override
    public String getCommandName() {
        return "qplimbo";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qplimbo";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(QuickPlay.onHypixel) {
            // Sending this chat message will kick the player, therefore
            // sending them to limbo.
            Minecraft.getMinecraft().thePlayer.sendChatMessage("§");
        } else {
            sender.addChatMessage(new ChatComponentText(new ChatComponentTranslation("quickplay.message.not_on_hypixel").getFormattedText()));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return QuickPlay.onHypixel;
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
