package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LimboCommand extends AbstractSubCommand {

    public LimboCommand(QpBaseCommand parent) {
        super(parent, "limbo", "Send yourself to Limbo.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(QuickPlay.onHypixel) {
            // Sending this chat message will kick the player, therefore
            // sending them to limbo.
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/achat §");
        } else {
            sender.addChatMessage(new TextComponentTranslation("quickplay.command.not_on_hypixel").setStyle(new Style().setColor(TextFormatting.RED)));
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
