package co.bugg.quickplay.command;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.List;

public class QpDebugCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "qpdebug";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qpdebug";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();

        aliases.add("qpd");

        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        IChatComponent debugHeader = new ChatComponentText("-------- " + Reference.MOD_NAME + " Debug --------\n");
        ChatStyle headerStyle = new ChatStyle();
        headerStyle.setColor(EnumChatFormatting.DARK_AQUA);
        debugHeader.setChatStyle(headerStyle);

        IChatComponent debugMsg = new ChatComponentText("MC Version: ");
        debugMsg.appendText(Util.getMCVersion() + "\n");
        debugMsg.appendText("MCP Version: ");
        debugMsg.appendText(Util.getMCPVersion() + "\n");
        debugMsg.appendText("Forge Version: ");
        debugMsg.appendText(Util.getForgeVersion() + "\n");
        debugMsg.appendText("Mod Version: ");
        debugMsg.appendText(Reference.VERSION + "\n");
        debugMsg.appendText("Mod ID: ");
        debugMsg.appendText(Reference.MOD_ID + "\n");
        debugMsg.appendText("Connection: ");
        debugMsg.appendText(Util.getIP() + "\n");
        debugMsg.appendText("Is Enabled: ");
        debugMsg.appendText(Boolean.toString(QuickPlay.onHypixel) + "\n");
        debugMsg.appendText("OS: ");
        debugMsg.appendText(System.getProperty("os.name") + " - " + System.getProperty("os.version") + "\n");
        debugMsg.appendText("Java Version: ");
        debugMsg.appendText(System.getProperty("java.version"));

        ChatStyle textStyle = new ChatStyle();
        textStyle.setColor(EnumChatFormatting.AQUA);

        debugMsg.setChatStyle(textStyle);

        debugHeader.appendSibling(debugMsg);

        Minecraft.getMinecraft().thePlayer.addChatMessage(debugHeader);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
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
