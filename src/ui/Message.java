/*
 * Decompiled with CFR 0.151.
 */
package ui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Message
extends JFrame {
    static final long serialVersionUID = 1L;
    private static int lastX = 10;
    private static int lastY = 10;
    private static final int maxX = 1000;
    private static final int maxY = 600;

    public Message(String name) throws HeadlessException {
        this.constructFrame(name);
    }

    public Message(String name, GraphicsConfiguration arg0) {
        super(arg0);
        this.constructFrame(name);
    }

    public Message(String name, String arg0) throws HeadlessException {
        super(arg0);
        this.constructFrame(name);
    }

    public Message(String name, String arg0, GraphicsConfiguration arg1) {
        super(arg0, arg1);
        this.constructFrame(name);
    }

    static void Display(String message) {
        new Message(message, "message");
    }

    private int gX() {
        if ((lastX += 150) > 1000) {
            lastX = 10;
        }
        return lastX;
    }

    private int gY() {
        if ((lastY += 150) > 600) {
            lastY = 10;
        }
        return lastY;
    }

    private void constructFrame(String name) {
        this.setVisible(false);
        this.setDefaultCloseOperation(3);
        this.setBounds(this.gX(), this.gY(), 400, 200);
        this.setResizable(false);
        JPanel mainpanel = (JPanel)this.getContentPane();
        mainpanel.setLayout(new BoxLayout(mainpanel, 1));
        mainpanel.setAlignmentX(0.5f);
        JTextArea text = new JTextArea(name);
        text.setBorder(BorderFactory.createLoweredBevelBorder());
        text.setAlignmentX(0.5f);
        JPanel pn1 = new JPanel();
        pn1.add(text);
        JScrollPane scroll = new JScrollPane(pn1);
        JButton OK = new JButton("LADNO");
        MouseAdapter list = new MouseAdapter(){

            public void mouseClicked(MouseEvent e) {
                Message.this.dispose();
            }
        };
        OK.addMouseListener(list);
        OK.setAlignmentX(0.5f);
        mainpanel.add(scroll);
        mainpanel.add(OK);
        this.doLayout();
        this.setVisible(true);
    }
}

