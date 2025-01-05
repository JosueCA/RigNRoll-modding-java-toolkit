/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import rnrcore.Log;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FactionRater {
    public static double DEFAULT_FACTION_RATING = 0.0;
    public static final String[] FACTION_NAMES = new String[]{"MonikaRank", "DorothyRank"};
    public static final int MONIKA = 0;
    public static final int DOROTHY = 1;
    private HashMap<String, Double> m_FactionsValues = new HashMap();
    private static FactionRater instance = null;

    private FactionRater() {
    }

    private static FactionRater getInstance() {
        if (instance == null) {
            instance = new FactionRater();
        }
        return instance;
    }

    private void checkFactionIsPredefined(String factionName) {
        for (String name : FACTION_NAMES) {
            if (name.compareTo(factionName) != 0) continue;
            return;
        }
        Log.simpleMessage("Faction rater errorr. Working with unknown faction " + factionName);
    }

    private void addFactionValue(String factionName, double value) {
        this.checkFactionIsPredefined(factionName);
        double newFactionValue = value;
        if (this.m_FactionsValues.containsKey(factionName)) {
            Double factionValue = this.m_FactionsValues.get(factionName);
            newFactionValue += factionValue.doubleValue();
        }
        Log.rating("Faction " + factionName + " has changed value to " + newFactionValue);
        this.m_FactionsValues.put(factionName, newFactionValue);
    }

    private void setFactionValues(List<Pair<String, String>> data) {
        assert (data != null);
        this.m_FactionsValues = new HashMap();
        for (Pair<String, String> pairValues : data) {
            String factionName = pairValues.getFirst();
            assert (factionName != null);
            String factionValueString = pairValues.getSecond();
            assert (factionValueString != null);
            try {
                double factionValue = Double.parseDouble(factionValueString);
                this.m_FactionsValues.put(factionName, factionValue);
            }
            catch (NumberFormatException exception) {
                Log.rating("Cannot format faction " + factionName + " value " + factionValueString + " to Double.");
            }
        }
    }

    private List<Pair<String, String>> getFactionValues() {
        if (this.m_FactionsValues != null) {
            ArrayList<Pair<String, String>> resultList = new ArrayList<Pair<String, String>>();
            Set<Map.Entry<String, Double>> factionsEntrieSet = this.m_FactionsValues.entrySet();
            for (Map.Entry<String, Double> singleFactionEntry : factionsEntrieSet) {
                resultList.add(new Pair<String, String>(singleFactionEntry.getKey(), singleFactionEntry.getValue().toString()));
            }
            return resultList;
        }
        return null;
    }

    private double getFactionValue(String factionName) {
        this.checkFactionIsPredefined(factionName);
        if (this.m_FactionsValues.containsKey(factionName)) {
            return this.m_FactionsValues.get(factionName);
        }
        return DEFAULT_FACTION_RATING;
    }

    public static void deinit() {
        instance = null;
    }

    public static void addFactionRating(String factionName, double value) {
        FactionRater.getInstance().addFactionValue(factionName, value);
    }

    public static double getFactionRating(String factionName) {
        return FactionRater.getInstance().getFactionValue(factionName);
    }

    public static List<Pair<String, String>> getSerializationData() {
        return FactionRater.getInstance().getFactionValues();
    }

    public static void setSerializationData(List<Pair<String, String>> data) {
        FactionRater.getInstance().setFactionValues(data);
    }
}

