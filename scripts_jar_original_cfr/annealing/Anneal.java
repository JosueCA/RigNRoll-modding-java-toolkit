/*
 * Decompiled with CFR 0.151.
 */
package annealing;

import annealing.IMeasure;
import annealing.IRandomSolution;
import annealing.MeasureResult;
import annealing.SingleMeasure;
import java.util.ArrayList;
import java.util.Random;
import ui.ProgressBar;

public class Anneal {
    private double dT = 1.0;
    private static final int FIELDSIZE = 1000;
    private IMeasure function = null;
    private IRandomSolution randomizer = null;
    private double T = 0.0;
    private SingleMeasure currentSolution = null;
    private Object[] solutionsField = null;
    private int fieldSize = 0;
    private Random rnd = new Random();
    public ArrayList history = new ArrayList();

    public Anneal(IMeasure function, IRandomSolution randomizer) {
        this.function = function;
        this.randomizer = randomizer;
    }

    private void updateTemperature() {
        this.T -= this.dT;
    }

    private void initNewSolution() {
        this.currentSolution = new SingleMeasure();
        this.currentSolution.data = this.randomizer.generateInitialSolution();
        this.currentSolution.result = this.function.makeMeasure(this.currentSolution.data);
        this.solutionsField = this.randomizer.generateFieldOfSolutions(1000);
        this.fieldSize = 1000;
    }

    private void setNewSolution(Object S, MeasureResult result) {
        this.history.add(this.currentSolution);
        this.currentSolution = new SingleMeasure();
        this.currentSolution.data = S;
        this.currentSolution.result = result;
        this.solutionsField = this.randomizer.generateFieldOfSolutions(1000);
        this.fieldSize = 1000;
    }

    private Object pickRandomSolution() {
        int num = (int)Math.floor(this.rnd.nextDouble() * (double)this.fieldSize);
        return this.solutionsField[num];
    }

    private boolean acceptNewSolution(double T, MeasureResult res_new, MeasureResult res_old) {
        double D = MeasureResult.difference(res_new, res_old);
        double probability = Math.exp(-D / T);
        return this.rnd.nextDouble() < probability;
    }

    public void start(double T0, double dt) {
        ProgressBar progress = new ProgressBar(0, (int)T0);
        this.T = T0;
        this.dT = dt;
        this.initNewSolution();
        while (this.T > 0.0) {
            Object S1 = this.pickRandomSolution();
            MeasureResult res = this.function.makeMeasure(S1);
            if (MeasureResult.less(res, this.currentSolution.result)) {
                this.setNewSolution(S1, res);
            } else if (this.acceptNewSolution(this.T, res, this.currentSolution.result)) {
                this.setNewSolution(S1, res);
            }
            progress.update((int)(T0 - this.T));
            this.updateTemperature();
        }
        this.history.add(this.currentSolution);
        progress.close();
        progress = null;
    }

    public SingleMeasure getResult() {
        return this.currentSolution;
    }
}

