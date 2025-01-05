/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import menu.KeyPair;
import rnrcore.MacroBody;

public class Macros {
    private String key;
    private MacroBody body;

    private String makeString() {
        return this.body.makeString();
    }

    public KeyPair getKeyPair() {
        return new KeyPair(this.key, this.makeString());
    }

    public Macros(String key, MacroBody body) {
        this.key = key;
        this.body = body;
    }

    public MacroBody getBody() {
        return this.body;
    }

    public String getKey() {
        return this.key;
    }
}

