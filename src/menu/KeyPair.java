// Decompiled with: CFR 0.152
// Class Version: 5
package menu;

public class KeyPair {
    private String key = null;
    private String value = null;

    public String GetKey() {
        return this.key;
    }

    public String GetValue() {
        return this.value;
    }

    public KeyPair() {
    }

    public KeyPair(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
