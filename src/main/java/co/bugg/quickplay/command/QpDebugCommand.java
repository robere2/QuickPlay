package co.bugg.quickplay.command;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QpDebugCommand implements ICommand {

    @Override
    public String getName() {
        return "qpdebug";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/qpdebug";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();

        aliases.add("qpd");

        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ITextComponent debugHeader = new TextComponentString("-------- " + Reference.MOD_NAME + " Debug --------\n");
        Style headerStyle = new Style();
        headerStyle.setColor(TextFormatting.DARK_AQUA);
        debugHeader.setStyle(headerStyle);

        ITextComponent debugMsg = new TextComponentString("MC Version: ");
        debugMsg.appendText(Util.getMCVersion() + "\n");
        debugMsg.appendText("MCP Version: ");
        debugMsg.appendText(Util.getMCPVersion() + "\n");
        debugMsg.appendText("Forge Version: ");
        debugMsg.appendText(Util.getForgeVersion() + "\n");
        debugMsg.appendText("Mod Version: ");
        debugMsg.appendText(Reference.VERSION + " (" + Reference.COMPATIBLE_MC_VERSION_MIN + "-" + Reference.COMPATIBLE_MC_VERSION_MAX + ")\n");
        debugMsg.appendText("Mod ID: ");
        debugMsg.appendText(Reference.MOD_ID + "\n");
        debugMsg.appendText("Connected To: ");
        debugMsg.appendText(Util.getIP() + "\n");
        debugMsg.appendText("Is Enabled: ");
        debugMsg.appendText(Boolean.toString(QuickPlay.onHypixel) + " (" + Keyboard.getKeyName(QuickPlay.openGui.getKeyCode()) + ")\n");
        debugMsg.appendText("OS: ");
        debugMsg.appendText(System.getProperty("os.name") + " - " + System.getProperty("os.version") + "\n");
        debugMsg.appendText("Java Version: ");
        debugMsg.appendText(System.getProperty("java.version"));

        Style textStyle = new Style();
        textStyle.setColor(TextFormatting.AQUA);

        debugMsg.setStyle(textStyle);

        debugHeader.appendSibling(debugMsg);

        sender.sendMessage(debugHeader);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
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
