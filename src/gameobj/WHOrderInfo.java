// Decompiled with: CFR 0.152
// Class Version: 5
package gameobj;

public class WHOrderInfo {
    public static final int TYPE_DELIVERY = 0;
    public static final int TYPE_CONTEST = 1;
    public static final int TYPE_TENDER = 2;
    public static final int TYPE_BIGRACE = 3;
    public static final int COOL_NONE = 0;
    public static final int COOL_GOLD = 1;
    public static final int COOL_SILVER = 2;
    public static final int COOL_BRONZE = 3;
    public static final int BIGRACE_MONSTER_CUP = 0;
    public static final int BIGRACE_CHEMPION_CUP = 1;
    public static final int BIGRACE_PRIMER_LEAGUE = 2;
    public static final int BIGRACE_FIRST_LEAGUE = 3;
    public static final int BIGRACE_SECOND_LEAGUE = 4;
    public static final String[] s_feebuttonnames = new String[]{"Max. Fee", "Gran prix", "Max. Fee"};
    public long nativep;
    public int WH_slot_ID;
    public String shipto;
    public String shipto_full;
    public String freight;
    public String shipper;
    public double weight;
    public double fragil;
    public int time_limit_min;
    public int time_limit_hour;
    public int charges;
    public int forfeit;
    public String cargotype;
    public int cargox;
    public int cargoy;
    public boolean competition;
    public double distance;
    public boolean accept;
    public String cargotypeid;
    public int arrowindex;
    private int order_type;
    public int order_coolness;
    public int race_stage;
    public boolean race_has_semi;

    public int GetOrderType() {
        return this.order_type;
    }
}
