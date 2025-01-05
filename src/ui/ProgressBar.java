/*
 * Decompiled with CFR 0.151.
 */
package ui;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ProgressBar
extends JFrame {
    static final long serialVersionUID = 1L;
    protected JProgressBar bar = null;
    protected Object monitor = new Object();
    private Thread runner = null;
    protected int value = 0;

    public ProgressBar(int min, int max) throws HeadlessException {
        this.constructFrame(min, max);
    }

    public ProgressBar(int min, int max, GraphicsConfiguration arg0) {
        super(arg0);
        this.constructFrame(min, max);
    }

    public ProgressBar(int min, int max, String arg0) throws HeadlessException {
        super(arg0);
        this.constructFrame(min, max);
    }

    public ProgressBar(int min, int max, String arg0, GraphicsConfiguration arg1) {
        super(arg0, arg1);
        this.constructFrame(min, max);
    }

    private void constructFrame(int min, int max) {
        UIManager.put("ProgressBar.foreground", new Color(8, 32, 128));
        this.setVisible(false);
        this.setDefaultCloseOperation(3);
        this.setBounds(400, 400, 600, 50);
        this.setResizable(false);
        JPanel mainpanel = (JPanel)this.getContentPane();
        this.bar = new JProgressBar(min, max);
        this.bar.setMinimum(min);
        this.bar.setMaximum(max);
        this.bar.setValue(min);
        this.bar.setStringPainted(true);
        mainpanel.add(this.bar);
        this.doLayout();
        this.setVisible(true);
        this.constructThreadListener();
    }

    private void constructThreadListener() {
        this.runner = new Thread(){

            public void run() {
                while (true) {
                    Runnable runme = new Runnable(){

                        /*
                         * WARNING - Removed try catching itself - possible behaviour change.
                         */
                        public void run() {
                            Object object = ProgressBar.this.monitor;
                            synchronized (object) {
                                ProgressBar.this.bar.setValue(ProgressBar.this.value);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(runme);
                    try {
                        Thread.sleep(100L);
                    }
                    catch (Exception exception) {
                    }
                }
            }
        };
        this.runner.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void update(int value) {
        Object object = this.monitor;
        synchronized (object) {
            this.value = value;
        }
    }

    public void close() {
        this.runner = null;
        this.dispose();
    }
}

