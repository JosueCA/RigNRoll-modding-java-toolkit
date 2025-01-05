/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrscenario.sctask;

public class ScenarioPassing
extends sctask {
    public static final long serialVersionUID = 0L;
    private static ScenarioPassing instance = null;
    public static final String[] MESSAGES = new String[0];
    public static final int START_KOH_HELP = 0;
    public static final int KOH_HELP_PHASE1 = 1;
    public static final int KOH_HELP_PHASE2 = 2;

    public static void init() throws InitializedException {
        if (null != instance) {
            throw new InitializedException();
        }
        instance = new ScenarioPassing();
    }

    public static void deinit() throws NotInitializedException {
        if (null == instance) {
            throw new NotInitializedException();
        }
        instance.finishImmediately();
        instance = null;
    }

    public ScenarioPassing() {
        super(3, false);
        this.start();
    }

    static class InitializedException
    extends Exception {
        public static final long serialVersionUID = 0L;

        InitializedException() {
        }

        public String toString() {
            return ScenarioPassing.class.getName() + " initialized already";
        }
    }

    static class NotInitializedException
    extends Exception {
        NotInitializedException() {
        }

        public String toString() {
            return ScenarioPassing.class.getName() + " not initialized";
        }
    }
}

