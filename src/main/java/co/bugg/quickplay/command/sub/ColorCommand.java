package co.bugg.quickplay.command.sub;

import co.bugg.quickplay.Reference;
import co.bugg.quickplay.command.AbstractSubCommand;
import co.bugg.quickplay.command.QpBaseCommand;
import co.bugg.quickplay.config.ConfigGui;
import co.bugg.quickplay.gui.QuickPlayGui;
import co.bugg.quickplay.util.TickDelay;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ColorCommand extends AbstractSubCommand {

    public ColorCommand(QpBaseCommand parent) {
        super(parent, "color", "Customize your " + Reference.MOD_NAME + " colors.", "");
    }

    @Override
    public void run(ICommandSender sender, String[] args) {
        new TickDelay(() -> QuickPlayGui.openGui(new ConfigGui(2)), 2);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Nonnull
    @Override
    public List<String> getTabCompletions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> tabCompletions = new ArrayList<>();
        return tabCompletions;
    }
}
