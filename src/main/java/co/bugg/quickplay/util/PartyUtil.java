package co.bugg.quickplay.util;

import co.bugg.quickplay.QuickPlay;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class PartyUtil {
    private PartyUtil() { throw new AssertionError(); }

    public static String getRandomPlayCommand() {
        List<String> games = QuickPlay.configManager.getConfig().enabledPartyCommands;

        if(games.size() > 0) {
            return games.get(ThreadLocalRandom.current().nextInt(0, games.size()));
        } else {
            return null;
        }
    }
}
