package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.command.QpSubCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class LimboCommand extends QpSubCommand {

    public LimboCommand(QpBaseCommand parent) {
        super(parent, "limbo", "Send yourself to Limbo.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        if(QuickPlay.onHypixel) {
            // Sending this chat message will kick the player, therefore
            // sending them to limbo.
            Minecraft.getMinecraft().thePlayer.sendChatMessage("§");
        } else {
            sender.addChatMessage(new ChatComponentText(new ChatComponentTranslation("quickplay.command.not_on_hypixel").getFormattedText()));
        }
    }
}