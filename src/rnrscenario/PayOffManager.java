/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import rnrcore.Log;
import rnrcore.eng;
import rnrrating.FactionRater;
import rnrrating.RateSystem;
import scenarioXml.XmlDocument;
import xmlutils.NodeList;

public class PayOffManager {
    public static final String[] PAYOFF_NAMES = new String[]{"dorothy_save_loose", "dorothy_save_success", "pp_race_loose", "pp_race_success", "pp_final_race_success", "chase_topo_success", "track_to_enemy_base_success", "track_to_enemy_base_loose", "enemy_base_assault_success", "koh_chase_success", "economy_victory", "social_victory", "race_victory"};
    public static final int DOROTHY_SAVE_LOOSE = 0;
    public static final int DOROTHY_SAVE_SUCCESS = 1;
    public static final int PP_RACE_LOOSE = 2;
    public static final int PP_RACE_SUCCESS = 3;
    public static final int PP_FINALRACE_SUCCESS = 4;
    public static final int CHASE_TOPO_SUCCESS = 5;
    public static final int TRACK_TO_ENEMY_BASE_SUCCESS = 6;
    public static final int TRACK_TO_ENEMY_BASE_LOOSE = 7;
    public static final int ENEMY_BASE_ASSAULT_SUCCESS = 8;
    public static final int KOH_CHASE_SUCCESS = 9;
    public static final int VICTORY_ECONOMY = 10;
    public static final int VICTORY_SOCIAL = 11;
    public static final int VICTORY_RACE = 12;
    private static final String XML_NAME = "..\\data\\config\\payoff.xml";
    private static final String TOP_NODE = "payoff";
    private static final String ELEMENT_NODE = "element";
    private static final String NAME_ATTR = "name";
    private static final String MONEY_COEFF_ATTR = "money";
    private static final String RATING_COEFF_ATTR = "rating";
    private static final String RANK_COEFF_ATTR = "rank";
    private static final String REAL_MONEY_ATTR = "realmoney";
    private static final String LOC_REF_ATTR = "pager_locref";
    private static final String FACTION_NODE = "faction";
    private static final String FACTION_NAME_ATTR = "name";
    private static final String FACTION_VALUE_ATTR = "rating";
    private HashMap<String, SinglePayOff> m_payOffs = new HashMap();
    private static PayOffManager instance = null;

    private PayOffManager() {
        this.read(XML_NAME);
    }

    public static PayOffManager getInstance() {
        if (instance == null) {
            instance = new PayOffManager();
        }
        return instance;
    }

    private void read(String filename) {
        XmlDocument xml = null;
        try {
            xml = new XmlDocument(filename);
        }
        catch (IOException exception) {
            Log.simpleMessage("Errorr. Cannot find file name " + filename);
            return;
        }
        Document doc = xml.getContent();
        Node top = doc.getFirstChild();
        if (top.getNodeName().compareToIgnoreCase(TOP_NODE) != 0) {
            return;
        }
        xmlutils.Node topNode = new xmlutils.Node(top);
        NodeList elementsList = topNode.getNamedChildren(ELEMENT_NODE);
        if (elementsList != null) {
            for (xmlutils.Node elementNode : elementsList) {
                String attributeName = elementNode.getAttribute("name");
                String attributeMoney = elementNode.getAttribute(MONEY_COEFF_ATTR);
                String attributeRealMoney = elementNode.getAttribute(REAL_MONEY_ATTR);
                String attributeRating = elementNode.getAttribute("rating");
                String attributeRank = elementNode.getAttribute(RANK_COEFF_ATTR);
                String attributeLocRef = elementNode.getAttribute(LOC_REF_ATTR);
                double money_coef = attributeMoney != null ? Double.parseDouble(attributeMoney) : 0.0;
                double real_money = attributeRealMoney != null ? Double.parseDouble(attributeRealMoney) : 0.0;
                double rating_coef = attributeRating != null ? Double.parseDouble(attributeRating) : 0.0;
                double rank_coef = attributeRank != null ? Double.parseDouble(attributeRank) : 0.0;
                SinglePayOff payOff = new SinglePayOff(money_coef, rank_coef, rating_coef, real_money);
                payOff.setAnnouncePaymentLocRef(attributeLocRef);
                NodeList factionsList = elementNode.getNamedChildren(FACTION_NODE);
                if (factionsList != null) {
                    for (xmlutils.Node factionNode : factionsList) {
                        String attributeFactionName = factionNode.getAttribute("name");
                        String attributeFactionValue = factionNode.getAttribute("rating");
                        double valueFactionPayOff = attributeFactionValue != null ? Double.parseDouble(attributeFactionValue) : 0.0;
                        payOff.addFactionPayOff(attributeFactionName, valueFactionPayOff);
                    }
                }
                if (this.m_payOffs.containsKey(attributeName)) {
                    Log.simpleMessage("WARNING. Trying to configue pay off twice. Pay off name " + attributeName);
                    continue;
                }
                this.m_payOffs.put(attributeName, payOff);
            }
        }
    }

    public void makePayOff(String payOffName) {
        if (!this.m_payOffs.containsKey(payOffName)) {
            Log.simpleMessage("ERRORR. Trying to pay off on unexisting pay off: " + payOffName);
            return;
        }
        Log.simpleMessage("Makin pay off: " + payOffName);
        SinglePayOff payOff = this.m_payOffs.get(payOffName);
        assert (payOff != null);
        payOff.makePayOff();
    }

    static class SinglePayOff {
        double money_coeff;
        double rank_coef;
        double rating_coef;
        double real_money;
        String announcePaymentLocRef = null;
        HashMap<String, Double> factionRewards = new HashMap();

        SinglePayOff(double money_coeff, double rank_coef, double rating_coef, double real_money) {
            this.money_coeff = money_coeff;
            this.rank_coef = rank_coef;
            this.rating_coef = rating_coef;
            this.real_money = real_money;
        }

        void setAnnouncePaymentLocRef(String value) {
            this.announcePaymentLocRef = value;
        }

        void addFactionPayOff(String factionName, double value) {
            if (this.factionRewards.containsKey(factionName)) {
                Log.simpleMessage("WARNING. Trying to create faction reward twice. Faction name " + factionName + " reward is " + value);
                return;
            }
            this.factionRewards.put(factionName, value);
        }

        void makePayOff() {
            Set<Map.Entry<String, Double>> factionsRewardSet = this.factionRewards.entrySet();
            for (Map.Entry<String, Double> singleFactionEntry : factionsRewardSet) {
                FactionRater.addFactionRating(singleFactionEntry.getKey(), singleFactionEntry.getValue());
            }
            RateSystem.addLiveRating(this.rating_coef);
            eng.makePayOff(this.money_coeff, this.rank_coef, this.real_money, this.announcePaymentLocRef);
        }
    }
}

