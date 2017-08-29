package co.bugg.quickplay;

import co.bugg.quickplay.gui.Icons;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = "[1.11,1.12.1]",
        clientSideOnly = true,
        updateJSON = "https://raw.githubusercontent.com/bugfroggy/QuickPlay/master/versions.json"
)
public class QuickPlay {

    public static KeyBinding openGui;
    public static boolean onHypixel = false;

    public static final String credit = Reference.MOD_NAME + " by @bugfroggy";

    // HashMap containing all GUI image files (only one at the moment but in preparation for the future)
    public static final HashMap<Integer, ResourceLocation> icons = new HashMap<Integer, ResourceLocation>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Add the icon files to the HashMap
        Icons.registerFiles();

        openGui = new KeyBinding("Open " + Reference.MOD_NAME, Keyboard.KEY_R, "key.categories.misc");
        ClientRegistry.registerKeyBinding(openGui);

        MinecraftForge.EVENT_BUS.register(new QuickPlayEventHandler());
    }
}
