package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.util.PartyUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PartyCommand extends AbstractSubCommand {

    public PartyCommand(QpBaseCommand parent) {
        super(parent, "party", "Join a randomized game.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(QuickPlay.onHypixel) {
            if(QuickPlay.configManager.getConfig().enabledPartyCommands.size() > 0) {
                sender.sendMessage(new TextComponentTranslation("quickplay.party.joining").setStyle(new Style().setColor(TextFormatting.GREEN)));
                ((EntityPlayerSP) sender).sendChatMessage("/play " + PartyUtil.getRandomPlayCommand());
            } else {
                sender.sendMessage(new TextComponentTranslation("quickplay.party.no_games").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("quickplay.command.not_on_hypixel").setStyle(new Style().setColor(TextFormatting.RED)));
        }
    }



    @SuppressWarnings("UnnecessaryLocalVariable")
    @Nonnull
    @Override
    public List<String> getTabCompletions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> tabCompletions = new ArrayList<>();
        return tabCompletions;
    }
}
