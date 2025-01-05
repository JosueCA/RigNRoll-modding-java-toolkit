/*
 * Decompiled with CFR 0.151.
 */
package scenarioAnalysis;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnalysisUI {
    private static final int NUM_BUTTONS = 10;
    IUpdateChoose listener = null;
    JFrame frame = new JFrame("Analysis UI");
    JButton[] buttons = null;

    public AnalysisUI() {
        this.createFrame();
    }

    private void createFrame() {
        this.frame.setVisible(false);
        this.frame.setDefaultCloseOperation(1);
        this.frame.setBounds(50, 150, 1500, 1000);
        this.frame.setResizable(false);
        JPanel mainpanel = (JPanel)this.frame.getContentPane();
        mainpanel.setLayout(new GridLayout(10, 1));
        this.buttons = new JButton[10];
        for (int i = 0; i < 10; ++i) {
            JButton button = new JButton("not initialised");
            button.addActionListener(new ButtonPressEvent(i));
            button.setVisible(false);
            mainpanel.add(button);
            this.buttons[i] = button;
        }
        this.frame.doLayout();
        this.frame.setVisible(false);
    }

    void recieve(String[] options, IUpdateChoose listener) throws ExceptionBadData {
        int i;
        this.listener = listener;
        if (options.length > 10) {
            throw new ExceptionBadData(options.length);
        }
        for (i = 0; i < 10; ++i) {
            this.buttons[i].setVisible(false);
        }
        for (i = 0; i < options.length; ++i) {
            this.buttons[i].setVisible(true);
            this.buttons[i].setText(options[i]);
        }
    }

    private void choose(int value) {
        if (null != this.listener) {
            this.listener.choose(value);
        }
    }

    void show() {
        this.frame.setVisible(true);
    }

    void close() {
        this.frame.dispose();
    }

    static interface IUpdateChoose {
        public void choose(int var1);
    }

    class ButtonPressEvent
    implements ActionListener {
        int m_nom_event = 0;

        ButtonPressEvent(int nom_event) {
            this.m_nom_event = nom_event;
        }

        public void actionPerformed(ActionEvent e) {
            AnalysisUI.this.choose(this.m_nom_event);
        }
    }

    static class ExceptionBadData
    extends Exception {
        static final long serialVersionUID = 0L;
        String message;
        JFrame frame;

        ExceptionBadData(int value) {
            super("Number of passed ways to go is more than 10 and equals " + value);
            this.message = "Number of passed ways to go is more than 10 and equals " + value;
        }

        void showEvent(final WarnMessageClosed listener) {
            this.frame = new JFrame("WARNING");
            this.frame.setDefaultCloseOperation(1);
            this.frame.setBounds(500, 300, 300, 150);
            this.frame.setResizable(false);
            JPanel mainpanel = (JPanel)this.frame.getContentPane();
            mainpanel.setLayout(new GridLayout(1, 1));
            JButton button = new JButton(this.message);
            button.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) {
                    ExceptionBadData.this.frame.dispose();
                    listener.closed();
                }
            });
            mainpanel.add(button);
            this.frame.doLayout();
            this.frame.setVisible(true);
        }
    }

    public static interface WarnMessageClosed {
        public void closed();
    }
}

