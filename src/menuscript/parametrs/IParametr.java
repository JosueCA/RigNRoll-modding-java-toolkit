/*
 * Decompiled with CFR 0.151.
 */
package menuscript.parametrs;

public interface IParametr {
    public boolean isBoolean();

    public boolean isInteger();

    public int getInteger();

    public void setInteger(int var1);

    public void setIntegerDefault(int var1);

    public boolean getBoolean();

    public void setBoolean(boolean var1);

    public void setBooleanDefault(boolean var1);

    public void update();

    public void updateDefault();

    public void makeDefault();

    public void readFromChanger(boolean var1);

    public boolean changed();

    public void setIntegerChange(int var1);

    public void setBooleanChange(boolean var1);

    public int getIntegerChange();

    public boolean getBooleanChange();
}

