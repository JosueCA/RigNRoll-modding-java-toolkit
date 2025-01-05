/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.Arrays;
import menu.KeyPair;
import menu.MacroKit;
import rnrcore.CoreTime;
import rnrcore.loc;
import rnrorg.Album;

public class AlbumFinishWarehouse {
    public static final String[] NOTES = new String[]{"BIGRACE GOLD 1", "BIGRACE GOLD 2", "BIGRACE GOLD 3", "BIGRACE GOLD 4", "BIGRACE SILVER 1", "BIGRACE SILVER 2", "BIGRACE SILVER 3", "BIGRACE SILVER 4", "BIGRACE BRONZE 1", "BIGRACE BRONZE 2", "BIGRACE BRONZE 3", "BIGRACE BRONZE 4"};
    public static final int[] TYPES = new int[]{25, 26, 27, 28, 30, 31, 32, 33, 35, 36, 37, 38};
    public static final String TEXTUREPREFIX = "bigraceshot";
    public static final String[] PLACES = new String[]{"gold", "silver", "bronze", "noprize"};
    public static final int PLACE_GOLD = 0;
    public static final int PLACE_SILVER = 1;
    public static final int PLACE_BRONZE = 2;
    public static final int PLACE_NOPRIZE = 3;
    public static final String[] PREFIXES = new String[]{"bigraceshot_04_", "bigraceshot_03_", "bigraceshot_02_", "bigraceshot_01_", "bigraceshot_04_", "bigraceshot_03_", "bigraceshot_02_", "bigraceshot_01_", "bigraceshot_04_", "bigraceshot_03_", "bigraceshot_02_", "bigraceshot_01_"};
    public static final String[] SUFFIXES = new String[]{"_" + PLACES[0], "_" + PLACES[0], "_" + PLACES[0], "_" + PLACES[0], "_" + PLACES[1], "_" + PLACES[1], "_" + PLACES[1], "_" + PLACES[1], "_" + PLACES[2], "_" + PLACES[2], "_" + PLACES[2], "_" + PLACES[2]};

    private static String textureName(int index, String logoname) {
        return PREFIXES[index] + logoname + SUFFIXES[index];
    }

    static void postNote(int type, String shortracename, String logoname) {
        int index = Arrays.binarySearch(TYPES, type);
        if (index < 0 || index >= TYPES.length) {
            return;
        }
        CoreTime time = new CoreTime();
        Album.getInstance().addBigRaceShot(shortracename, Integer.toString(index), time, AlbumFinishWarehouse.textureName(index, logoname));
    }

    public static String locText(String shortracename, String index) {
        KeyPair[] keys = new KeyPair[]{new KeyPair("SHORTRACENAME", loc.getBigraceShortName(shortracename))};
        return MacroKit.Parse(loc.getMissionSuccesPictureText(NOTES[Integer.parseInt(index)]), keys);
    }
}

