package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.util.PartyUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class PartyCommand extends AbstractSubCommand {

    public PartyCommand(QpBaseCommand parent) {
        super(parent, "party", "Join a randomized game.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(QuickPlay.onHypixel) {
            if(QuickPlay.configManager.getConfig().enabledPartyCommands.size() > 0) {
                sender.addChatMessage(new ChatComponentTranslation("quickplay.party.joining").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
                ((EntityPlayerSP) sender).sendChatMessage("/play " + PartyUtil.getRandomPlayCommand());
            } else {
                sender.addChatMessage(new ChatComponentTranslation("quickplay.party.no_games").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            }
        } else {
            sender.addChatMessage(new ChatComponentTranslation("quickplay.command.not_on_hypixel").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
    }
}
