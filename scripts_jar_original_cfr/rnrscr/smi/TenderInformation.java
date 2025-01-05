/*
 * Decompiled with CFR 0.151.
 */
package rnrscr.smi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import menu.KeyPair;
import menu.MacroKit;
import menuscript.Converts;
import rnrconfig.WarehouseInformation;
import rnrcore.CoreTime;
import rnrcore.eng;
import rnrcore.loc;
import rnrscr.smi.Article;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TenderInformation
extends Article {
    private String destinationWarehouse = "destination no name";
    private ArrayList<String> warehouses = new ArrayList();
    private FeeMultiplier mulpiplier;
    private String textureName;
    static Random rnd = new Random();
    private CoreTime timeEnd = null;

    private void addWarehouse(String wh_name) {
        this.warehouses.add(wh_name);
    }

    public TenderInformation(String destinationWarehouse, Vector baseNames, int multiplier, int yearArticleLife, int monthArticleLife, int dayArticleLife, int hourArticleLife) {
        this.destinationWarehouse = destinationWarehouse;
        Iterator iter = baseNames.iterator();
        while (iter.hasNext()) {
            this.addWarehouse((String)iter.next());
        }
        this.timeEnd = new CoreTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife, 0);
        this.setDueToTime(yearArticleLife, monthArticleLife, dayArticleLife, hourArticleLife);
        switch (multiplier) {
            case 2: {
                this.mulpiplier = FeeMultiplier.DOUBLE;
                break;
            }
            case 3: {
                this.mulpiplier = FeeMultiplier.TRIPLE;
                break;
            }
            case 4: {
                this.mulpiplier = FeeMultiplier.QUADRUPLE;
                break;
            }
            default: {
                eng.err("TenderInformation multiplier has wrong multiplier parametr.");
            }
        }
        int num = (int)(rnd.nextDouble() * 7.0);
        switch (num) {
            case 0: {
                this.textureName = "tex_menu_News01";
                break;
            }
            case 1: {
                this.textureName = "tex_menu_News02";
                break;
            }
            case 2: {
                this.textureName = "tex_menu_News03";
                break;
            }
            case 3: {
                this.textureName = "tex_menu_News04";
                break;
            }
            case 4: {
                this.textureName = "tex_menu_News05";
                break;
            }
            case 5: {
                this.textureName = "tex_menu_News06";
                break;
            }
            case 6: {
                this.textureName = "tex_menu_News07";
            }
        }
    }

    @Override
    public String getBody() {
        String warehouseRealName = new WarehouseInformation(this.destinationWarehouse).getRealName();
        String warehouses_str = "";
        for (int i = 0; i < this.warehouses.size(); ++i) {
            warehouses_str = warehouses_str + new WarehouseInformation(this.warehouses.get(i)).getRealName();
            if (i == this.warehouses.size() - 1) continue;
            warehouses_str = warehouses_str + ", ";
        }
        String mult_str = "";
        switch (this.mulpiplier) {
            case DOUBLE: {
                mult_str = loc.getNewspaperString("MULTIPLIER DOUBLE");
                break;
            }
            case TRIPLE: {
                mult_str = loc.getNewspaperString("MULTIPLIER TRIPLE");
                break;
            }
            case QUADRUPLE: {
                mult_str = loc.getNewspaperString("MULTIPLIER QUADRUPLE");
            }
        }
        KeyPair[] template = new KeyPair[]{new KeyPair("DESTINATION", warehouseRealName), new KeyPair("WAREHOUSES", warehouses_str), new KeyPair("MULTIPLIER", mult_str)};
        String ret = MacroKit.Parse(loc.getNewspaperString("TENDER BODY"), template);
        return Converts.ConverTimeAbsolute(ret, this.timeEnd.gHour(), this.timeEnd.gMinute());
    }

    @Override
    public String getHeader() {
        return loc.getNewspaperString("TENDER HEADER");
    }

    @Override
    public boolean isNews() {
        return true;
    }

    @Override
    public String getTexture() {
        return this.textureName;
    }

    public String getDestinationWarehouse() {
        return this.destinationWarehouse;
    }

    public FeeMultiplier getMulpiplier() {
        return this.mulpiplier;
    }

    public CoreTime getTimeEnd() {
        return this.timeEnd;
    }

    public ArrayList<String> getWarehouses() {
        return this.warehouses;
    }

    @Override
    public int getPriority() {
        return 15;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum FeeMultiplier {
        DOUBLE,
        TRIPLE,
        QUADRUPLE;

    }
}

