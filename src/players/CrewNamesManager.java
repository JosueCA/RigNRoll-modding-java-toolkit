/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.HashMap;
import players.MC;
import players.ScenarioPlayer;
import players.actorveh;
import players.aiplayer;

public final class CrewNamesManager {
    private HashMap<String, aiplayer> registeredNames = new HashMap();
    private static aiplayer mainCharacter;
    private static CrewNamesManager instance;

    public static CrewNamesManager getInstance() {
        if (null == instance) {
            instance = new CrewNamesManager();
        }
        return instance;
    }

    public static void deinitMainCharacter(actorveh carOfMainCharacter) {
        assert (null != carOfMainCharacter);
        mainCharacter.abondoneCar(carOfMainCharacter);
        mainCharacter = null;
    }

    public static void mainCharacterLoaded(aiplayer character) {
        mainCharacter = character;
    }

    public static void createMainCharacter() {
        if (null == mainCharacter) {
            mainCharacter = new aiplayer("SC_NICK");
            mainCharacter.setModelCreator(new MC(), "MC");
        }
    }

    private void add(String name) {
        aiplayer player = new aiplayer(name);
        player.setModelCreator(new ScenarioPlayer(), "sc");
        this.registeredNames.put(name, player);
    }

    private CrewNamesManager() {
        String[] namesSCENARIO;
        for (String name : namesSCENARIO = new String[]{"SC_SECRETARY", "SC_DOROTHY", "SC_MATTHEW", "SC_BANDIT3", "SC_BANDITJOE", "SC_BANDITGUN", "SC_BILL_OF_LANDING", "SC_STRANGER", "SC_PITERPAN", "SC_ONTANIELO", "SC_KOH", "SC_POLICE", "SC_MONICA"}) {
            this.add(name);
        }
    }

    aiplayer gPlayer(String nameRef) {
        if (!this.registeredNames.containsKey(nameRef)) {
            return null;
        }
        return this.registeredNames.get(nameRef);
    }

    static aiplayer getMainCharacterPlayer() {
        return mainCharacter;
    }

    public static void deinit() {
        instance = null;
    }

    static {
        instance = null;
    }
}

