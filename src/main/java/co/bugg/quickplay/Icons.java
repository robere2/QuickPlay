package co.bugg.quickplay;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.LinkedList;


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
     *            Main Lobby            *
     * -------------------------------- */
    public static LinkedHashMap<String, String> mainCommands = new LinkedHashMap<>();
    static {
        mainCommands.put("Limbo", "/qp limbo");
    }
    public static final Game MAIN = new Game("Main Lobby",2,0,0,  "main", mainCommands);
    /* -------------------------------- *
     *             Arcade               *
     * -------------------------------- */
    public static LinkedHashMap<String, String> arcadeCommands = new LinkedHashMap<>();
    static {
        arcadeCommands.put("Mini Walls", "arcade_mini_walls");
        arcadeCommands.put("Football", "arcade_soccer");
    }
    public static final Game ARCADE = new Game("Arcade",1,0,0,  "arcade", arcadeCommands);

    /* -------------------------------- *
     *            Bed Wars              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> bedwarsCommands = new LinkedHashMap<>();
    static {
        bedwarsCommands.put("Solo", "bedwars_eight_one");
        bedwarsCommands.put("Doubles", "bedwars_eight_two");
        bedwarsCommands.put("3v3v3v3", "bedwars_four_three");
        bedwarsCommands.put("4v4v4v4", "bedwars_four_four");
    }
    public static final Game BEDWARS = new Game("Bed Wars",1, 64,  0, "bedwars", bedwarsCommands);

    /* -------------------------------- *
     *            Blitz SG              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> survival_gamesCommands = new LinkedHashMap<>();
    static {
        survival_gamesCommands.put("Solo", "blitz_solo_normal");
        survival_gamesCommands.put("Teams", "blitz_teams_normal");
        survival_gamesCommands.put("No Kits", "blitz_solo_nokits");
    }
    public static final Game SURVIVAL_GAMES = new Game("Blitz SG",1,0, 128, "blitz", survival_gamesCommands);

    /* -------------------------------- *
     *             Classic              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> classicCommands = new LinkedHashMap<>();
    static {
        classicCommands.put("Arena 1v1", "arena_1v1");
        classicCommands.put("Arena 2v2", "arena_2v2");
        classicCommands.put("Arena 4v4", "arena_4v4");
        classicCommands.put("Paintball", "paintball");
        classicCommands.put("Quake Solo", "quake_solo");
        classicCommands.put("Quake Teams", "quake_teams");
        classicCommands.put("The Walls", "walls");
        classicCommands.put("Turbo Kart Racers", "tkr");
        classicCommands.put("VampireZ", "vampirez");
    }
    public static final Game LEGACY = new Game("Classic",1,128, 0, "classic", classicCommands);
    /* -------------------------------- *
     *           Crazy Walls            *
     * -------------------------------- */
    public static LinkedHashMap<String, String> true_combatCommands = new LinkedHashMap<>();
    static {
        true_combatCommands.put("Solo", "crazy_walls_solo");
        true_combatCommands.put("Solo (Lucky)", "crazy_walls_solo_chaos");
        true_combatCommands.put("Teams", "crazy_walls_team");
        true_combatCommands.put("Teams (Lucky)", "crazy_walls_team_chaos");
    }
    public static final Game TRUE_COMBAT = new Game("Crazy Walls",1, 192, 0,  "crazy", true_combatCommands);

    /* -------------------------------- *
     *          Cops & Crims            *
     * -------------------------------- */
    public static final Game MCGO = new Game("Cops and Crims",1,0, 64, "cvc", null);

    /* -------------------------------- *
     *             Housing              *
     * -------------------------------- */
    // Housing is a special case. "Home" will be recognized as the lobby name and it'll assign the appropriate command.
    public static final Game HOUSING = new Game("Housing",1, 64, 64,  "/home", new ChatComponentTranslation("quickplay.buttons.home").getFormattedText(), null);

    /* -------------------------------- *
     *           Mega Walls             *
     * -------------------------------- */
    public static final Game WALLS3 = new Game("Mega Walls",1,128, 64,  "megawalls", null);

    /* -------------------------------- *
     *         Murder Mystery           *
     * -------------------------------- */
    public static LinkedHashMap<String, String> murder_mysteryCommands = new LinkedHashMap<>();
    static {
        murder_mysteryCommands.put("Classic", "murder_classic");
        murder_mysteryCommands.put("Assassins", "murder_assassins");
        murder_mysteryCommands.put("Hardcore", "murder_hardcore");
    }
    public static final Game MURDER_MYSTERY = new Game("Murder Mystery",2,64, 0, "mm", murder_mysteryCommands);
    /* -------------------------------- *
     *            Prototype             *
     * -------------------------------- */
    public static LinkedHashMap<String, String> prototypeCommands = new LinkedHashMap<>();
    static {
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

        prototypeCommands.put("Hide and Seek", "prototype_hide_and_seek");
    }
    public static final Game PROTOTYPE = new Game("Prototype",1,192, 64, "prototype", prototypeCommands);

    /* -------------------------------- *
     *            Party Mode            *
     * -------------------------------- */

    public static final Game PARTY = new Game("Party Mode",2,128, 0, null, null);

    /* -------------------------------- *
     *            SkyClash              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> skyclashCommands = new LinkedHashMap<>();
    static {
        skyclashCommands.put("Solo", "skyclash_solo");
        skyclashCommands.put("Doubles", "skyclash_doubles");
        skyclashCommands.put("Team War", "skyclash_team_war");
    }
    public static final Game SKYCLASH = new Game("SkyClash",1,64, 128, "skyclash", skyclashCommands);

    /* -------------------------------- *
     *             Skywars              *
     * -------------------------------- */
    public static LinkedHashMap<String, String> skywarsCommands = new LinkedHashMap<>();
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
    public static final Game SKYWARS = new Game("SkyWars",1,128, 128,  "skywars", skywarsCommands);

    /* -------------------------------- *
     *          Smash Heroes            *
     * -------------------------------- */
    public static LinkedHashMap<String, String> super_smashCommands = new LinkedHashMap<>();
    static {
        super_smashCommands.put("Solo", "super_smash_solo_normal");
        super_smashCommands.put("2v2", "super_smash_2v2_normal");
        super_smashCommands.put("2v2v2", "super_smash_teams_normal");
        super_smashCommands.put("Friends", "super_smash_friends_normal");
    }

    public static final Game SUPER_SMASH = new Game("Smash Heroes",1,192, 128, "smash", super_smashCommands);

    /* -------------------------------- *
     *            Speed UHC             *
     * -------------------------------- */
    public static LinkedHashMap<String, String> speed_uhcCommands = new LinkedHashMap<>();
    static {
        speed_uhcCommands.put("Solo Normal", "speed_solo_normal");
        speed_uhcCommands.put("Solo Insane", "speed_solo_insane");
        speed_uhcCommands.put("Teams Normal", "speed_team_normal");
        speed_uhcCommands.put("Teams Insane", "speed_team_insane");
    }
    public static final Game SPEED_UHC = new Game("Speed UHC",1,0, 192, "speeduhc", speed_uhcCommands);

    /* -------------------------------- *
     *            TNT Games             *
     * -------------------------------- */
    public static LinkedHashMap<String, String> tntgamesCommands = new LinkedHashMap<>();
    static {
        tntgamesCommands.put("TNT Run", "tnt_tntrun");
        tntgamesCommands.put("PVP Run", "tnt_pvprun");
        tntgamesCommands.put("Bow Spleef", "tnt_bowspleef");
        tntgamesCommands.put("TNT Tag", "tnt_tntag");
        tntgamesCommands.put("TNT Wizards", "tnt_capture");
    }
    public static final Game TNTGAMES = new Game("TNT Games",1, 64, 192, "tnt", tntgamesCommands);

    /* -------------------------------- *
     *          UHC Champions           *
     * -------------------------------- */
    public static final Game UHC = new Game("UHC Champions",1,128, 192, "uhc", null);

    /* -------------------------------- *
     *             Warlords             *
     * -------------------------------- */
    public static final Game BATTLEGROUND = new Game("Warlords",1, 192, 192, "warlords", null);

    public static LinkedList<Game> list = new LinkedList<>();
    static {
        list.add(MAIN);

        list.add(ARCADE);
        list.add(BEDWARS);
        list.add(SURVIVAL_GAMES);
        list.add(LEGACY);

        list.add(TRUE_COMBAT);
        list.add(MCGO);
        list.add(HOUSING);
        list.add(WALLS3);

        list.add(MURDER_MYSTERY);
        list.add(PARTY);
        list.add(PROTOTYPE);
        list.add(SKYCLASH);
        list.add(SKYWARS);

        list.add(SUPER_SMASH);
        list.add(SPEED_UHC);
        list.add(TNTGAMES);
        list.add(UHC);

        list.add(BATTLEGROUND);
    }

    /**
     * Registers all GUI files with the mod by adding them to a HashMap.
     * fileID provided in the Game constructor corresponds to the Integer provided
     * when registering a file here.
     *
     * Called at preInit
     */
    public static void registerFiles() {
        registerFile(1, "games1.png");
        registerFile(2, "games2.png");
    }

    /**
     * Register a single file to the list of game GUI files.
     * @param id File ID that was provided in the Game class constructor
     * @param name Name of the file (including file type, e.g. "game.png")
     */
    private static void registerFile(int id, String name) {
        System.out.println("Registering file: " + name);
        QuickPlay.icons.put(id, new ResourceLocation(Reference.MOD_ID, "textures/gui/button/" + name));
    }

    /**
     * Get the Game object with the provided name in Icons.list
     * @param title Title of the game to search for
     * @return Game, or null if non-existent
     */
    public static Game getGame(String title) {
        for(Game game : list) {
            if(game.name.equalsIgnoreCase(title)) {
                return game;
            }
        }
        return null;
    }
}
