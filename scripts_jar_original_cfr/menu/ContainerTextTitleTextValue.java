/*
 * Decompiled with CFR 0.151.
 */
package menu;

import rnrcore.loc;

public class ContainerTextTitleTextValue {
    private String title;
    private String value;
    public String loc_title;
    public String loc_value;

    private ContainerTextTitleTextValue(ContainerTextTitleTextValue base) {
        this.title = base.title;
        this.value = base.value;
        this.loc_title = base.loc_title;
        this.loc_value = base.loc_value;
    }

    public ContainerTextTitleTextValue(String title, String value) {
        this.title = title;
        this.value = value;
        this.loc_title = loc.getMenuString(title);
        this.loc_value = loc.getMenuString(value);
    }

    public ContainerTextTitleTextValue clone() {
        return new ContainerTextTitleTextValue(this);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String n_value) {
        this.value = n_value;
        this.loc_value = loc.getMenuString(this.value);
    }

    public void cleanValue() {
        this.value = "";
        this.loc_value = "";
    }
}

