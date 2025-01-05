/*
 * Decompiled with CFR 0.151.
 */
package players;

public class NickNamesUniqueName {
    private int count_nick_names = 0;
    private static final String nick_names_prefix = "CREWWMAN";

    protected String getNickNameString() {
        return nick_names_prefix + this.count_nick_names++;
    }

    protected void leaveNickNameString(String value) {
    }

    public int getCount_nick_names() {
        return this.count_nick_names;
    }

    public void setCount_nick_names(int count_nick_names) {
        this.count_nick_names = count_nick_names;
    }
}

