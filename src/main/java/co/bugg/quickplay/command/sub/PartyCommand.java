package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.command.QpSubCommand;
import co.bugg.quickplay.util.PartyUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;

public class PartyCommand extends QpSubCommand {

    public PartyCommand(QpBaseCommand parent) {
        super(parent, "party", "Join a randomized game.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(QuickPlay.onHypixel) {
            if(QuickPlay.configManager.getConfig().enabledPartyCommands.size() > 0) {
                sender.sendMessage(new TextComponentTranslation("quickplay.party.joining"));
                ((EntityPlayerSP) sender).sendChatMessage("/play " + PartyUtil.getRandomPlayCommand());
            } else {
                sender.sendMessage(new TextComponentTranslation("quickplay.party.no_games"));
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("quickplay.command.not_on_hypixel"));
        }
    }
}
