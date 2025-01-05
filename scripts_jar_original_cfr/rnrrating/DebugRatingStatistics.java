/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

import annealing.Anneal;
import annealing.IMeasure;
import annealing.IRandomSolution;
import annealing.MeasureResult;
import annealing.SingleMeasure;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import rnrrating.BigRace;
import rnrrating.DebugRating;
import rnrrating.WHRating;
import ui.GraficWrapper;
import ui.Message;
import ui.ProgressBar;
import ui.SliderBox;
import ui.ValueChanged;

public class DebugRatingStatistics {
    protected static DebugRatingStatistics statistica = null;
    private final double a_low = 1.0;
    private final double b_low = 1.0;
    private final double c_low = 1.0;
    private final double L_low = 1.0;
    private double a_current = 1.0;
    private double b_current = 1.0;
    private double c_current = 1.0;
    private double L_current = 1.0;
    private JFrame frame = new JFrame("Statistic");
    Manager M = new Manager();

    private DebugRatingStatistics() {
    }

    public static final void newMeasure() {
        statistica = new DebugRatingStatistics();
    }

    public static final boolean iteration() {
        return statistica._iteration();
    }

    public static final int getRating(int level) {
        return (int)statistica.get_value(level);
    }

    public static final void printStatistics(DebugRating.TextControl logger) {
    }

    public static final void MakeStatisticsLayOut() {
        statistica.constructFrame();
        statistica.buildBonusGraphic();
        statistica.buildRatingGraphic();
    }

    private final double get_value(double level) {
        return this.L_current * Math.exp(this.a_current * level * level + this.b_current * level + this.c_current);
    }

    private final boolean _iteration() {
        return true;
    }

    private void constructFrame() {
        this.frame.setVisible(false);
        this.frame.setDefaultCloseOperation(1);
        this.frame.setBounds(50, 150, 1000, 200);
        this.frame.setResizable(false);
        JPanel mainpanel = (JPanel)this.frame.getContentPane();
        mainpanel.setLayout(new GridLayout(1, 1));
        JPanel changables = new JPanel();
        changables.setLayout(new GridLayout(1, 5));
        JTextField formule = new JTextField("L*exp(a*l^2 + b*l + c)");
        formule.setEditable(false);
        SliderBox sla = new SliderBox(this.a_current - 10.0, this.a_current + 10.0, 100, this.a_current, "A");
        SliderBox slb = new SliderBox(this.b_current - 10.0, this.b_current + 10.0, 100, this.b_current, "B");
        SliderBox slc = new SliderBox(this.c_current - 10.0, this.c_current + 10.0, 100, this.c_current, "C");
        SliderBox slL = new SliderBox(this.L_current - 10.0, this.L_current + 10.0, 100, this.L_current, "L");
        this.M.formulaEdit = new Controls(sla, slb, slc, slL);
        changables.add(formule);
        changables.add(sla);
        changables.add(slb);
        changables.add(slc);
        changables.add(slL);
        mainpanel.add(changables);
        this.frame.doLayout();
        this.frame.setVisible(true);
    }

    protected void buildBonusGraphic() {
        this.M.graficBonus.clean();
        for (int i = 0; i < 6; ++i) {
            WHRating.series_GOLD[i] = DebugRatingStatistics.getRating(i);
            this.M.graficBonus.addPoint(i, WHRating.series_GOLD[i]);
        }
        this.M.graficBonus.refresh();
    }

    protected void buildRatingGraphic() {
        this.M.graficRating.clean();
        int iteracii = DebugRating.daysforgame / 3;
        int r = BigRace.limits_series[1];
        int i = 1;
        int count = 0;
        while (i < BigRace.series_highest && iteracii > count++) {
            this.M.graficRating.addPoint(count, r += WHRating.series_GOLD[i]);
            if (r <= BigRace.limits_series[i + 1]) continue;
            ++i;
        }
        for (int iter = 0; iter < 6; ++iter) {
            if (iter == BigRace.series_highest) {
                this.M.graficRating.addHorizontalLine(BigRace.limits_series[iter], "MONSTERCUP");
                continue;
            }
            this.M.graficRating.addHorizontalLine(BigRace.limits_series[iter], "level " + iter);
        }
        this.M.graficRating.refresh();
    }

    protected void renewValues() {
        this.a_current = this.M.formulaEdit._a_coef.slider.getSliderValue();
        this.b_current = this.M.formulaEdit._b_coef.slider.getSliderValue();
        this.c_current = this.M.formulaEdit._c_coef.slider.getSliderValue();
        this.L_current = this.M.formulaEdit._L_coef.slider.getSliderValue();
    }

    static class AnealManager
    implements IMeasure,
    IRandomSolution {
        static final double startTemperature = 200.0;
        static final double fieldDisturbance = 2.0;
        static final int numInternalLaunches = 3;
        static final int acceptableDayDifference = 90;
        static final int acceptableRacesDifference = 10;
        private ArrayList history = new ArrayList();

        AnealManager() {
        }

        public final Object[] generateFieldOfSolutions(int number) {
            Object[] field = new Data[number];
            Random rnd2 = new Random();
            int lim = BigRace.series_highest;
            double[] disturbance = new double[6];
            for (int distur = 0; distur < 6; ++distur) {
                disturbance[distur] = 2.0 * Math.pow(10.0, Math.floor(0.43429 * Math.log(WHRating.series_GOLD[distur])));
            }
            for (int i = 0; i < number; ++i) {
                int[] datavalues = new int[6];
                for (int vals = 0; vals < 6; ++vals) {
                    datavalues[vals] = WHRating.series_GOLD[vals] <= 1 ? (lim > vals ? (int)((double)WHRating.series_GOLD[vals] + disturbance[vals] * (2.0 - 2.0 * rnd2.nextDouble())) : WHRating.series_GOLD[vals]) : (lim > vals ? (int)((double)WHRating.series_GOLD[vals] + disturbance[vals] * (1.0 - 2.0 * rnd2.nextDouble())) : WHRating.series_GOLD[vals]);
                    double count = 2.0;
                    while (vals > 0 && datavalues[vals] <= datavalues[vals - 1]) {
                        datavalues[vals] = (int)((double)datavalues[vals] + disturbance[vals] * count * (1.0 - rnd2.nextDouble()));
                        count += 1.0;
                    }
                }
                field[i] = new Data(datavalues[0], datavalues[1], datavalues[2], datavalues[3], datavalues[4], datavalues[5]);
                for (int itern = 0; itern < 6; ++itern) {
                    if (!((double)((Data)field[i]).values[itern] < 0.1 * Math.pow(10.0, itern))) continue;
                    int n = itern;
                    ((Data)field[i]).values[n] = (int)((double)((Data)field[i]).values[n] + 2.0 * Math.pow(10.0, itern));
                }
            }
            return field;
        }

        public final Object generateInitialSolution() {
            if (this.history.isEmpty()) {
                SingleMeasure m = new SingleMeasure();
                m.data = new Data(WHRating.series_GOLD[0], WHRating.series_GOLD[1], WHRating.series_GOLD[2], WHRating.series_GOLD[3], WHRating.series_GOLD[4], WHRating.series_GOLD[5]);
                m.result = this.makeMeasure(m.data);
                this.history.add(m);
            }
            return ((SingleMeasure)this.history.get((int)(this.history.size() - 1))).data;
        }

        public final MeasureResult makeMeasure(Object _data) {
            Data data = (Data)_data;
            for (int i = 0; i < 6; ++i) {
                WHRating.series_GOLD[i] = data.values[i];
            }
            WHRating.renew();
            DebugRating.renewScreen();
            DebugRating.GameResults[] daysstat = new DebugRating.GameResults[3];
            for (int iter = 0; iter < 3; ++iter) {
                DebugRating.makeDebugStatistics();
                daysstat[iter] = DebugRating.getGameResults();
            }
            return new MeasureResult(this.computeWeight(daysstat, 3));
        }

        private final int computeWeight(DebugRating.GameResults[] res, int size) {
            int meandays = 0;
            int meanraces = 0;
            int countmeanraces = 0;
            for (int i = 0; i < size; ++i) {
                meandays += res[i].daysleft > 0 ? res[i].daysleft : DebugRating.daysforgame;
                if (res[i].daysleft < 0) continue;
                int itermediate = 0;
                int count = 0;
                for (int j = 1; j < BigRace.series_highest; ++j) {
                    ++count;
                    itermediate += res[i].sp_bc[j];
                }
                ++countmeanraces;
                meanraces += itermediate / count;
            }
            meandays /= size;
            meanraces = countmeanraces != 0 ? meanraces / countmeanraces : 0;
            int racesdispercion = 0;
            for (int i = 0; i < size; ++i) {
                for (int j = 1; j < BigRace.series_highest; ++j) {
                    int diffrence;
                    if (res[i].daysleft < 0 || (diffrence = Math.abs(res[i].sp_bc[j] - meanraces)) <= racesdispercion) continue;
                    racesdispercion = diffrence;
                }
            }
            double W1 = (double)(DebugRating.daysforgame - meandays) * Math.exp(-((double)meandays) / 90.0);
            double W2 = Math.exp(-((double)racesdispercion) / 10.0);
            return (int)(W1 * W2);
        }

        private final int maxComputedWeight() {
            return DebugRating.daysforgame;
        }

        static void makeStatistics() {
            Thread runner = new Thread(){

                public void run() {
                    AnealManager.prmakeStatistics();
                }
            };
            runner.start();
        }

        protected static void prmakeStatistics() {
            int i;
            ProgressBar PROGRESS = new ProgressBar(0, 10);
            AnealManager man = new AnealManager();
            for (int count_measures = 0; count_measures < 10; ++count_measures) {
                PROGRESS.update(count_measures);
                Anneal stat = new Anneal(man, man);
                double t0 = (double)man.maxComputedWeight() / 2.0 * (1.0 - (double)count_measures / 9.0 * 0.9);
                double dt = t0 / 300.0;
                stat.start(t0, dt);
                StringBuffer History = new StringBuffer("History Buff\n");
                int max_weight = 0;
                int index_max_weight = 0;
                for (int i2 = 0; i2 < stat.history.size(); ++i2) {
                    SingleMeasure m = (SingleMeasure)stat.history.get(i2);
                    History.append("Measure N " + i2 + ".\tWeight = \t" + m.result.weight + ".\t");
                    Data data = (Data)m.data;
                    History.append("Data\t\tlvl0 : " + data.values[0] + "\tlvl1 : " + data.values[1] + "\tlvl2 : " + data.values[2] + "\tlvl3 : " + data.values[3] + "\tlvl4 : " + data.values[4] + "\tlvl5 : " + data.values[5]);
                    History.append("\n");
                    if (m.result.weight < max_weight) continue;
                    max_weight = m.result.weight;
                    index_max_weight = i2;
                }
                man.history.add(stat.history.get(index_max_weight));
            }
            int max_weight = 0;
            int index_max_weight = 0;
            StringBuffer totalHistory = new StringBuffer("TOTAL History Buff\n");
            for (i = 0; i < man.history.size(); ++i) {
                SingleMeasure m = (SingleMeasure)man.history.get(i);
                totalHistory.append("Measure N " + i + ".\tWeight = \t" + m.result.weight + ".\t");
                Data data = (Data)m.data;
                totalHistory.append("Data\t\tlvl0 : " + data.values[0] + "\tlvl1 : " + data.values[1] + "\tlvl2 : " + data.values[2] + "\tlvl3 : " + data.values[3] + "\tlvl4 : " + data.values[4] + "\tlvl5 : " + data.values[5]);
                totalHistory.append("\n");
                if (m.result.weight < max_weight) continue;
                max_weight = m.result.weight;
                index_max_weight = i;
            }
            new Message(totalHistory.toString());
            for (i = 0; i < 6; ++i) {
                WHRating.series_GOLD[i] = ((Data)((SingleMeasure)man.history.get((int)index_max_weight)).data).values[i];
            }
            WHRating.renew();
            DebugRating.renewScreen();
            PROGRESS.close();
        }
    }

    static class Data {
        public int[] values = new int[6];

        Data() {
        }

        Data(int i1, int i2, int i3, int i4, int i5, int i6) {
            this.values[0] = i1;
            this.values[1] = i2;
            this.values[2] = i3;
            this.values[3] = i4;
            this.values[4] = i5;
            this.values[5] = i6;
        }
    }

    static class Manager {
        Controls formulaEdit = null;
        GraficWrapper graficBonus = GraficWrapper.newGraphec(24, 100, 1000, 600);
        GraficWrapper graficRating = GraficWrapper.newGraphec(24, 100, 1000, 600);

        Manager() {
        }
    }

    static class Controls {
        TrackSliders _a_coef;
        TrackSliders _b_coef;
        TrackSliders _c_coef;
        TrackSliders _L_coef;

        Controls(SliderBox valuea, SliderBox valueb, SliderBox valuec, SliderBox valueL) {
            this._a_coef = new TrackSliders(valuea);
            valuea.assignChangeListener(this._a_coef);
            this._b_coef = new TrackSliders(valueb);
            valueb.assignChangeListener(this._b_coef);
            this._c_coef = new TrackSliders(valuec);
            valuec.assignChangeListener(this._c_coef);
            this._L_coef = new TrackSliders(valueL);
            valuea.assignChangeListener(this._L_coef);
        }
    }

    static class TrackSliders
    implements ValueChanged {
        SliderBox slider;

        TrackSliders(SliderBox slider) {
            this.slider = slider;
        }

        public void recieveChange() {
            statistica.renewValues();
            statistica.buildBonusGraphic();
            statistica.buildRatingGraphic();
            DebugRating.renewScreen();
        }
    }
}

