/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.Vector;

public class SceneMacroPairs {
    private Vector<Pair> m_pairs = new Vector();

    private SceneMacroPairs() {
    }

    public static SceneMacroPairs create() {
        return new SceneMacroPairs();
    }

    public void addPair(String key, String value) {
        assert (key != null);
        assert (value != null);
        this.m_pairs.add(new Pair(key, value));
    }

    public Vector getMacroPairs() {
        return this.m_pairs;
    }

    static class Pair {
        String key;
        String value;

        Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}

