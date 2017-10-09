package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.util.GameUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DebugCommand extends AbstractSubCommand {

    public DebugCommand(QpBaseCommand parent) {
        super(parent, "debug", "View debugging information and mod status.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        IChatComponent debugMsg = new ChatComponentTranslation("quickplay.command.separator");
        debugMsg.appendText("\n");
        debugMsg.appendText("MC Version: ");
        debugMsg.appendText(GameUtil.getMCVersion() + "\n");
        debugMsg.appendText("MCP Version: ");
        debugMsg.appendText(GameUtil.getMCPVersion() + "\n");
        debugMsg.appendText("Forge Version: ");
        debugMsg.appendText(GameUtil.getForgeVersion() + "\n");
        debugMsg.appendText("Mod Version: ");
        debugMsg.appendText(Reference.MOD_ID + " " + Reference.VERSION + " (" + Reference.COMPATIBLE_MC_VERSION_MIN + "-" + Reference.COMPATIBLE_MC_VERSION_MAX + ")\n");
        debugMsg.appendText("Connected To: ");
        debugMsg.appendText(GameUtil.getIP() + "\n");
        debugMsg.appendText("Is Enabled: ");
        debugMsg.appendText(Boolean.toString(QuickPlay.onHypixel) + " (" + Keyboard.getKeyName(QuickPlay.openGui.getKeyCode()) + ")\n");
        debugMsg.appendText("OS: ");
        debugMsg.appendText(System.getProperty("os.name") + " - " + System.getProperty("os.version") + "\n");
        debugMsg.appendText("Java Version: ");
        debugMsg.appendText(System.getProperty("java.version"));
        debugMsg.appendSibling(new ChatComponentTranslation("quickplay.command.separator"));

        debugMsg.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.AQUA));

        Minecraft.getMinecraft().thePlayer.addChatMessage(debugMsg);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Nonnull
    @Override
    public List<String> getTabCompletions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> tabCompletions = new ArrayList<>();
        return tabCompletions;
    }
}
