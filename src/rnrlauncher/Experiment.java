package rnrlauncher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Experiment {
  public static void main(String[] args) {
    JFrame test = new JFrame("Gradient Test");
    final ImageIcon gradient = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_yellow.gif");
    JPanel base = new JPanel() {
        public void paint(Graphics g) {
          Rectangle area = g.getClipBounds();
          Graphics2D canvas = (Graphics2D)g;
          canvas.drawImage(gradient.getImage(), area.x, area.y, area.width, area.height, null);
          paintChildren(g);
        }
      };
    JLabel label = new JLabel("Test");
    label.setOpaque(false);
    base.add(label);
    base.setBackground(Color.BLUE);
    test.add(base);
    test.setDefaultCloseOperation(3);
    test.pack();
    test.setVisible(true);
  }
}
