/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrscenario.ScenarioSave;
import rnrscenario.scrun;

public class sctask
implements scrun {
    protected boolean f_started;
    protected boolean f_finished;
    private boolean f_mark = false;
    private boolean surviveDuringGameDeinit = true;
    private int m_tip = 3;

    public boolean couldSurviveDuringGameDeinit() {
        return this.surviveDuringGameDeinit;
    }

    public sctask(int tip, boolean stopOnGameDeinit) {
        this.surviveDuringGameDeinit = !stopOnGameDeinit;
        this.m_tip = tip;
        switch (tip) {
            case 0: {
                ScenarioSave.getInstance().addTaskOnEveryFrame(this);
                break;
            }
            case 3: {
                ScenarioSave.getInstance().addTaskOn3Seconds(this);
                break;
            }
            case 60: {
                ScenarioSave.getInstance().addTaskOn60Seconds(this);
                break;
            }
            case 600: {
                ScenarioSave.getInstance().addTaskOn600Seconds(this);
            }
        }
        this.f_started = false;
        this.f_finished = false;
    }

    public void add(scrun ob) {
    }

    public void run() {
    }

    public void finish() {
        this.f_finished = true;
    }

    public void finishImmediately() {
        switch (this.m_tip) {
            case 0: {
                ScenarioSave.getInstance().removeTaskOnEveryFrame(this);
                break;
            }
            case 3: {
                ScenarioSave.getInstance().removeTaskOn3Seconds(this);
                break;
            }
            case 60: {
                ScenarioSave.getInstance().removeTaskOn60Seconds(this);
                break;
            }
            case 600: {
                ScenarioSave.getInstance().removeTaskOn600Seconds(this);
            }
        }
    }

    public void start() {
        this.f_started = true;
    }

    public boolean finished() {
        return this.f_finished;
    }

    public boolean started() {
        return this.f_started;
    }

    public void mark(boolean f_mark) {
        this.f_mark = f_mark;
    }

    public boolean marked() {
        return this.f_mark;
    }
}

