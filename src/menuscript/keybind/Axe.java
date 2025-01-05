/*
 * Decompiled with CFR 0.151.
 */
package menuscript.keybind;

public class Axe {
    private String axename;
    private int axecode;
    private boolean inverse;
    private boolean isFullAxe;

    public int getAxecode() {
        return this.axecode;
    }

    public void setAxecode(int axecode) {
        this.axecode = axecode;
    }

    public String getAxename() {
        return this.axename;
    }

    public void setAxename(String axename) {
        this.axename = axename;
    }

    public boolean isInverse() {
        return this.inverse;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    public boolean isFullAxe() {
        return this.isFullAxe;
    }

    public void setFullAxe(boolean isFullAxe) {
        this.isFullAxe = isFullAxe;
    }

    public Axe clone() {
        Axe res = new Axe();
        res.axename = this.axename;
        res.axecode = this.axecode;
        res.inverse = this.inverse;
        res.isFullAxe = this.isFullAxe;
        return res;
    }
}

