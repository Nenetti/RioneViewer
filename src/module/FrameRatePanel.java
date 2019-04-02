package module;


import config.Config;

import javax.swing.*;
import java.awt.*;



public class FrameRatePanel extends JPanel {

    private Graphics2D graphics;

    public void paintComponent (Graphics g) {
        graphics = (Graphics2D) g;
        String text = String.format("%d FPS", Config.FrameRate);
        graphics.setColor(Color.GREEN);
        drawString(20, 20, text);
    }

    private void drawString (int x, int y, String text) {
        Rectangle rectangle = graphics.getFontMetrics().getStringBounds(text, graphics).getBounds();
        x += rectangle.width / 2;
        y += rectangle.height / 2;
        graphics.drawString(text, x, y);
    }
}
