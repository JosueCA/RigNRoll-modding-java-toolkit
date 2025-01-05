/*
 * Decompiled with CFR 0.151.
 */
package rnrlauncher.data;

public final class SystemInfoDataRecord {
    public static final int COMPONENTS_COUNT = 4;
    private final Object[] data = new Object[4];
    public static final int INFO_TITLE = 0;
    public static final int INFO_USER_COMPONENT = 1;
    public static final int INFO_REQUIRED_COMPONENT = 2;
    public static final int INFO_RESULUTION = 3;

    public Object getInfo(int index) {
        assert (0 <= index && this.data.length > index);
        return this.data[index];
    }

    public void putInfo(int index, Object info) {
        assert (0 <= index && this.data.length > index);
        this.data[index] = info;
    }
}

