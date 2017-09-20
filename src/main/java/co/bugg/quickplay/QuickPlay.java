package co.bugg.quickplay;

import co.bugg.quickplay.command.QpColorCommand;
import co.bugg.quickplay.command.QpDebugCommand;
import co.bugg.quickplay.command.QpDestroyConfig;
import co.bugg.quickplay.command.QpLimbo;
import co.bugg.quickplay.config.ConfigManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.HashMap;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
        clientSideOnly = true,
        updateJSON = "https://raw.githubusercontent.com/bugfroggy/QuickPlay/master/versions.json"
)
public class QuickPlay {

    public static KeyBinding openGui;
    public static KeyBinding openFavorite;
    public static boolean onHypixel = false;

    public static ConfigManager configManager;

    public static final String credit = Reference.MOD_NAME + " v" + Reference.VERSION + " by @bugfroggy";

    // HashMap containing all GUI image files (only one at the moment but in preparation for the future)
    public static final HashMap<Integer, ResourceLocation> icons = new HashMap<>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Register the configuration
        configManager = new ConfigManager();

        // Add the icon files to the HashMap
        Icons.registerFiles();

        openGui = new KeyBinding("quickplay.controls.open", configManager.getConfig().openGuiKey, "key.categories.quickplay");
        openFavorite = new KeyBinding("quickplay.controls.favorite", configManager.getConfig().openFavoriteKey, "key.categories.quickplay");
        ClientRegistry.registerKeyBinding(openGui);
        ClientRegistry.registerKeyBinding(openFavorite);

        MinecraftForge.EVENT_BUS.register(new QuickPlayEventHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new QpColorCommand());
        ClientCommandHandler.instance.registerCommand(new QpDebugCommand());
        ClientCommandHandler.instance.registerCommand(new QpDestroyConfig());
        ClientCommandHandler.instance.registerCommand(new QpLimbo());
    }
}
