package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

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
            sender.addChatMessage(new ChatComponentTranslation("quickplay.command.not_on_hypixel").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
    }
}
