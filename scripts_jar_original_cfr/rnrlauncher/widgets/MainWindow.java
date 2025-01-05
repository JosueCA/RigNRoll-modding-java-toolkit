/*
 * Decompiled with CFR 0.151.
 */
package rnrlauncher.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import rnrlauncher.data.LocalizedText;
import rnrlauncher.widgets.DataTable;

public final class MainWindow {
    private JFrame mainWindow;
    public static final Color BACKGROUND_COLOR = new Color(107, 96, 100, 255);
    private static final Color FOOTER_COLOR = new Color(230, 230, 230, 255);
    private static final Color HEADER_COLOR = new Color(200, 200, 200, 255);
    private static final int FONT_SIZE = 12;
    private static final int HEADER_SIZE = 16;
    private static final int FOOTER_SIZE = 30;
    private static final int GAP = 5;
    private static final int FOOTER_PANEL_GAP = 10;
    private static final int TOP_PANELS_IN_ROW = 2;
    private final int[] buttonsCount = new int[]{1, 1, 2, 2, 3};
    private boolean continueExecutionStatus = false;
    private static final int BUTTONS_GAP = 200;
    private AbstractButton buttonRun = null;
    private AbstractButton buttonCancel = null;
    private final Object windowClosedMonitor = new Object();

    public static int getBordersWidth() {
        return 15;
    }

    public MainWindow(final ImageIcon logoIcon, ImageIcon windowIcon, DataTable dataToDisplay, String fontName, LocalizedText text, int status) {
        assert (null != dataToDisplay);
        assert (null != fontName);
        assert (null != text);
        assert (null != logoIcon);
        assert (null != windowIcon);
        assert (0 <= status && this.buttonsCount.length > status);
        this.mainWindow = new JFrame();
        BorderLayout mainWindowLayout = new BorderLayout(5, 5);
        JPanel godFatherPanel = new JPanel(mainWindowLayout);
        JPanel textFooterBase = new JPanel(new BorderLayout());
        JPanel imagePanel = new JPanel(new BorderLayout()){

            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D canvas = (Graphics2D)g;
                canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                canvas.drawImage(logoIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        JPanel contentPanel = new JPanel(new BorderLayout(5, 0));
        godFatherPanel.setBorder(new LineBorder(BACKGROUND_COLOR, 5));
        textFooterBase.setBorder(new LineBorder(FOOTER_COLOR, 5));
        godFatherPanel.setBackground(BACKGROUND_COLOR);
        ((Component)imagePanel).setBackground(BACKGROUND_COLOR);
        Font textFont = new Font(fontName, 1, 12);
        Container buttonsPanel = this.createButtonsPanel(text, status);
        Container footerPanel = MainWindow.createFooter(text, textFooterBase, textFont, buttonsPanel);
        Container headerPanel = MainWindow.createHeader(text, textFont);
        contentPanel.add((Component)headerPanel, "North");
        contentPanel.add((Component)dataToDisplay.getTable(), "Center");
        contentPanel.add((Component)footerPanel, "South");
        ((Component)contentPanel).setBackground(BACKGROUND_COLOR);
        godFatherPanel.add((Component)imagePanel, "West");
        godFatherPanel.add((Component)contentPanel, "Center");
        this.mainWindow.setIconImage(windowIcon.getImage());
        this.mainWindow.getContentPane().setBackground(BACKGROUND_COLOR);
        this.mainWindow.setLayout(new BorderLayout());
        this.mainWindow.add((Component)godFatherPanel, "Center");
        this.mainWindow.setResizable(false);
        this.mainWindow.pack();
        this.mainWindow.addWindowListener(new WindowAdapter(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void windowClosing(WindowEvent e) {
                Object object = MainWindow.this.windowClosedMonitor;
                synchronized (object) {
                    MainWindow.this.windowClosedMonitor.notify();
                }
            }
        });
        double ratio = (double)((Component)contentPanel).getHeight() / (double)logoIcon.getIconHeight();
        ((Component)imagePanel).setPreferredSize(new Dimension((int)(ratio * (double)logoIcon.getIconWidth()), ((Component)contentPanel).getHeight()));
        this.mainWindow.pack();
    }

    private Container createButtonsPanel(LocalizedText text, int status) {
        switch (status) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                this.buttonRun = new JButton(text.getRunAnywayButtonText());
                break;
            }
            case 4: {
                this.buttonRun = new JButton(text.getRunButtonText());
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        this.buttonCancel = new JButton(text.getCancelButtonText());
        this.buttonCancel.setFocusable(true);
        this.buttonCancel.setBackground(FOOTER_COLOR);
        this.buttonCancel.addActionListener(new ActionListener(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void actionPerformed(ActionEvent e) {
                Object object = MainWindow.this.windowClosedMonitor;
                synchronized (object) {
                    MainWindow.this.continueExecutionStatus = false;
                    MainWindow.this.windowClosedMonitor.notify();
                }
                MainWindow.this.mainWindow.setVisible(false);
                MainWindow.this.mainWindow.dispose();
            }
        });
        JPanel buttonsContainer = new JPanel();
        if (null != this.buttonRun) {
            this.buttonRun.setBackground(FOOTER_COLOR);
            this.buttonRun.addActionListener(new ActionListener(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void actionPerformed(ActionEvent e) {
                    Object object = MainWindow.this.windowClosedMonitor;
                    synchronized (object) {
                        MainWindow.this.continueExecutionStatus = true;
                        MainWindow.this.windowClosedMonitor.notify();
                    }
                    MainWindow.this.mainWindow.setVisible(false);
                    MainWindow.this.mainWindow.dispose();
                }
            });
            buttonsContainer.add(this.buttonRun);
        }
        buttonsContainer.add(this.buttonCancel);
        buttonsContainer.setLayout(new GridLayout(1, this.buttonsCount[status], 5, 5));
        ((Component)buttonsContainer).setBackground(BACKGROUND_COLOR);
        JPanel buttonsPanel = new JPanel();
        ((Component)buttonsPanel).setBackground(BACKGROUND_COLOR);
        buttonsPanel.add(Box.createHorizontalStrut(200));
        buttonsPanel.add(buttonsContainer);
        buttonsPanel.add(Box.createHorizontalStrut(200));
        return buttonsPanel;
    }

    private static Container createFooter(LocalizedText text, JComponent textFooterBase, Font textFont, Container buttonsPanel) {
        JTextArea textFooter = new JTextArea(text.getFooterText());
        textFooter.setFont(textFont);
        textFooter.setBackground(FOOTER_COLOR);
        textFooter.setSelectionColor(FOOTER_COLOR);
        textFooter.setSelectedTextColor(Color.BLACK);
        textFooter.setPreferredSize(new Dimension(0, 30));
        textFooter.setEditable(false);
        textFooter.setLineWrap(true);
        textFooter.setWrapStyleWord(true);
        textFooterBase.add((Component)textFooter, "Center");
        BorderLayout footerLayout = new BorderLayout();
        footerLayout.setVgap(10);
        footerLayout.setHgap(5);
        JPanel footerPanel = new JPanel(footerLayout);
        ((Component)footerPanel).setBackground(BACKGROUND_COLOR);
        footerPanel.add((Component)textFooterBase, "Center");
        footerPanel.add((Component)buttonsPanel, "South");
        return footerPanel;
    }

    private static Container createHeader(LocalizedText text, Font textFont) {
        JLabel textHeader = new JLabel(text.getHeaderText());
        textHeader.setBackground(HEADER_COLOR);
        textHeader.setPreferredSize(new Dimension(0, 16));
        textHeader.setVerticalAlignment(0);
        textHeader.setHorizontalAlignment(0);
        textHeader.setFont(textFont);
        JPanel textHeaderBackground = new JPanel(new BorderLayout());
        textHeaderBackground.add((Component)textHeader, "Center");
        ((Component)textHeaderBackground).setBackground(HEADER_COLOR);
        return textHeaderBackground;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean show() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.mainWindow.setLocation((screenSize.width - this.mainWindow.getWidth()) / 2, (screenSize.height - this.mainWindow.getHeight()) / 2);
        if (null == this.buttonRun) {
            this.buttonCancel.requestFocusInWindow();
        } else {
            this.buttonRun.requestFocusInWindow();
        }
        this.mainWindow.setDefaultCloseOperation(2);
        this.mainWindow.setVisible(true);
        Object object = this.windowClosedMonitor;
        synchronized (object) {
            try {
                this.windowClosedMonitor.wait();
            }
            catch (InterruptedException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
            }
        }
        return this.continueExecutionStatus;
    }
}

