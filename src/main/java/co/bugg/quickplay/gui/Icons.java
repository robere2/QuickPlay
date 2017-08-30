package co.bugg.quickplay.gui;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedHashMap;


/**
 * Hardcoded in all gamemodes along with which game they use in the icons files
 * This should be the only file that needs modification to add/remove new buttons
 * (With the exception of the GUI image files, of course)
 */
public class Icons {

    /**
     * How wide and how tall each game are. I don't recommend changing this.
     */
    public static int iconWidth = 64;
    public static int iconHeight = 64;

    /* -------------------------------- *
     *             Arcade               *
     * -------------------------------- */
    public static LinkedHashMap<String, String> arcadeCommands = new LinkedHashMap<String, String>();
    static {
        arcadeCommands.put("Mini Walls", "arcade_mini_walls");
        arcadeCommands.put("Football", "arcade_soccer");
    }
    public static final Game ARCADE = new Game("Arcade",1,0,0, 0, "arcade", arcadeCommands);

    /* -------------------------------- *
     *            Bed Wars              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> bedwarsCommands = new LinkedHashMap<String, String>();
    static {
        bedwarsCommands.put("Solo", "bedwars_eight_one");
        bedwarsCommands.put("Doubles", "bedwars_eight_two");
        bedwarsCommands.put("3v3v3v3", "bedwars_four_three");
        bedwarsCommands.put("4v4v4v4", "bedwars_four_four");
    }
    public static final Game BEDWARS = new Game("Bed Wars",1, 64,  0,  1, "bedwars", bedwarsCommands);

    /* -------------------------------- *
     *             Classic              *
     * -------------------------------- */
    public static final Game LEGACY = new Game("Classic",1,128, 0, 2, "classic", null);
    /* -------------------------------- *
     *           Crazy Walls            *
     * -------------------------------- */
    public static LinkedHashMap<String, String> true_combatCommands = new LinkedHashMap<String, String>();
    static {
        true_combatCommands.put("Solo", "crazy_walls_solo");
        true_combatCommands.put("Teams", "crazy_walls_team");
    }
    public static final Game TRUE_COMBAT = new Game("Crazy Walls",1, 192, 0, 3, "crazy", true_combatCommands);

    /* -------------------------------- *
     *          Cops & Crims            *
     * -------------------------------- */
    public static final Game MCGO = new Game("Cops and Crims",1,0, 64, 4, "cvc", null);

    /* -------------------------------- *
     *             Housing              *
     * -------------------------------- */
    // Housing is a special case. "Home" will be recognized as the lobby name and it'll assign the appropriate command.
    public static final Game HOUSING = new Game("Housing",1, 64, 64, 5, "home", null);

    /* -------------------------------- *
     *           Mega Walls             *
     * -------------------------------- */
    public static final Game WALLS3 = new Game("Mega Walls",1,128, 64, 6, "megawalls", null);

    /* -------------------------------- *
     *            Prototype             *
     * -------------------------------- */
    public static LinkedHashMap<String, String> prototypeCommands = new LinkedHashMap<String, String>();
    static {
        prototypeCommands.put("Murder Mystery", "prototype_murder_mystery");

        prototypeCommands.put("Duels - Classic", "prototype_duels:classic_duel");
        prototypeCommands.put("Duels - Bow", "prototype_duels:bow_duel");
        prototypeCommands.put("Duels - Potion", "prototype_duels:potion_duel");
        prototypeCommands.put("Duels - OP", "prototype_duels:op_duel");
        prototypeCommands.put("Duels - MW 1v1", "prototype_duels:mw_duel");
        prototypeCommands.put("Duels - MW 2v2", "prototype_duels:mw_doubles");
        prototypeCommands.put("Duels - MW 4v4", "prototype_duels:mw_four");
        prototypeCommands.put("Duels - UHC 1v1", "prototype_duels:uhc_duel");
        prototypeCommands.put("Duels - UHC 2v2", "prototype_duels:uhc_doubles");
        prototypeCommands.put("Duels - UHC 4v4", "prototype_duels:uhc_four");

        prototypeCommands.put("Zombies - Story (Normal)", "prototype_zombies_story_normal");
        prototypeCommands.put("Zombies - Story (Hard)", "prototype_zombies_story_hard");
        prototypeCommands.put("Zombies - Story (RIP)", "prototype_zombies_story_rip");
        prototypeCommands.put("Zombies - Endless (Normal)", "prototype_zombies_endless_normal");
        prototypeCommands.put("Zombies - Endless (Hard)", "prototype_zombies_endless_hard");
        prototypeCommands.put("Zombies - Endless (RIP)", "prototype_zombies_endless_rip");
    }
    public static final Game PROTOTYPE = new Game("Prototype",1,192, 64, 7, "prototype", prototypeCommands);

    /* -------------------------------- *
     *            Blitz SG              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> survival_gamesCommands = new LinkedHashMap<String, String>();
    static {
        survival_gamesCommands.put("Solo", "blitz_solo_normal");
        survival_gamesCommands.put("Teams", "blitz_teams_normal");
        survival_gamesCommands.put("No Kits", "blitz_solo_nokits");
    }
    public static final Game SURVIVAL_GAMES = new Game("Blitz SG",1,0, 128, 8, "blitz", survival_gamesCommands);

    /* -------------------------------- *
     *            SkyClash              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> skyclashCommands = new LinkedHashMap<String, String>();
    static {
        skyclashCommands.put("Solo", "skyclash_solo");
        skyclashCommands.put("Doubles", "skyclash_doubles");
        skyclashCommands.put("Team War", "skyclash_team_war");
    }
    public static final Game SKYCLASH = new Game("SkyClash",1,64, 128,9, "skyclash", skyclashCommands);

    /* -------------------------------- *
     *             Skywars              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> skywarsCommands = new LinkedHashMap<String, String>();
    static {
        skywarsCommands.put("Solo Normal", "solo_normal");
        skywarsCommands.put("Solo Insane", "solo_insane");
        skywarsCommands.put("Teams Normal", "teams_normal");
        skywarsCommands.put("Teams Insane", "teams_insane");

        skywarsCommands.put("Ranked", "ranked_normal");
        skywarsCommands.put("Mega", "mega_normal");

        skywarsCommands.put("Solo TNT Madness", "solo_insane_tnt_madness");
        skywarsCommands.put("Solo Rush", "solo_insane_rush");
        skywarsCommands.put("Solo Slime", "solo_insane_slime");

        skywarsCommands.put("Teams TNT Madness", "teams_insane_tnt_madness");
        skywarsCommands.put("Teams Rush", "teams_insane_rush");
        skywarsCommands.put("Teams Slime", "teams_insane_slime");
    }
    public static final Game SKYWARS = new Game("SkyWars",1,128, 128, 10, "skywars", skywarsCommands);

    /* -------------------------------- *
     *          Smash Heroes            *
     * -------------------------------- */
    public static LinkedHashMap<String, String> super_smashCommands = new LinkedHashMap<String, String>();
    static {
        super_smashCommands.put("Solo", "super_smash_solo_normal");
        super_smashCommands.put("Teams", "super_smash_teams_normal");
        super_smashCommands.put("Friends", "super_smash_friends_normal");
    }

    public static final Game SUPER_SMASH = new Game("Smash Heroes",1,192, 128,11, "smash", super_smashCommands);

    /* -------------------------------- *
     *            Speed UHC             *
     * -------------------------------- */
    public static LinkedHashMap<String, String> speed_uhcCommands = new LinkedHashMap<String, String>();
    static {
        speed_uhcCommands.put("Solo Normal", "speed_solo_normal");
        speed_uhcCommands.put("Solo Insane", "speed_solo_insane");
        speed_uhcCommands.put("Teams Normal", "speed_team_normal");
        speed_uhcCommands.put("Teams Insane", "speed_team_insane");
    }
    public static final Game SPEED_UHC = new Game("Speed UHC",1,0, 192,12, "speeduhc", speed_uhcCommands);

    /* -------------------------------- *
     *            TNT Games             *
     * -------------------------------- */
    public static LinkedHashMap<String, String> tntgamesCommands = new LinkedHashMap<String, String>();
    static {
        tntgamesCommands.put("TNT Run", "tnt_tntrun");
        tntgamesCommands.put("PVP Run", "tnt_pvprun");
        tntgamesCommands.put("Bow Spleef", "tnt_bowspleef");
        tntgamesCommands.put("TNT Tag", "tnt_tnttag");
        tntgamesCommands.put("TNT Wizards", "tnt_capture");
    }
    public static final Game TNTGAMES = new Game("TNT Games",1, 64, 192, 13, "tnt", tntgamesCommands);

    /* -------------------------------- *
     *          UHC Champions           *
     * -------------------------------- */
    public static final Game UHC = new Game("UHC Champions",1,128, 192, 14, "uhc", null);

    /* -------------------------------- *
     *             Warlords             *
     * -------------------------------- */
    public static final Game BATTLEGROUND = new Game("Warlords",1, 192, 192, 15, "warlords", null);

    public static LinkedHashMap<Integer, Game> map = new LinkedHashMap<Integer, Game>();
    static {
        map.put(ARCADE.buttonID, ARCADE);
        map.put(BEDWARS.buttonID, BEDWARS);
        map.put(LEGACY.buttonID, LEGACY);
        map.put(TRUE_COMBAT.buttonID, TRUE_COMBAT);

        map.put(MCGO.buttonID, MCGO);
        map.put(HOUSING.buttonID, HOUSING);
        map.put(WALLS3.buttonID, WALLS3);
        map.put(PROTOTYPE.buttonID, PROTOTYPE);

        map.put(SURVIVAL_GAMES.buttonID, SURVIVAL_GAMES);
        map.put(SKYCLASH.buttonID, SKYCLASH);
        map.put(SKYWARS.buttonID, SKYWARS);
        map.put(SUPER_SMASH.buttonID, SUPER_SMASH);

        map.put(SPEED_UHC.buttonID, SPEED_UHC);
        map.put(TNTGAMES.buttonID, TNTGAMES);
        map.put(UHC.buttonID, UHC);
        map.put(BATTLEGROUND.buttonID, BATTLEGROUND);
    }

    /**
     * Registers all GUI files with the mod by adding them to a HashMap.
     * fileID provided in the Game constructor corresponds to the Integer provided
     * when registering a file here.
     *
     * Called at preInit
     */
    public static void registerFiles() {
        QuickPlay.icons.put(1, new ResourceLocation(Reference.MOD_ID, "textures/gui/game-icons1.png"));
    }
}
