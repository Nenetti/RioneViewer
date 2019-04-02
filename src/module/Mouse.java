package module;


import config.Config;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;



public class Mouse extends MouseAdapter {

    private int mouseX;
    private int mouseY;


    public void mousePressed (MouseEvent e) {

    }

    public void mouseDragged (MouseEvent e) {
        double multiply = Math.abs(((Config.MaxScale - Config.Scale) / Config.MaxScale / 2)) + 1;
        Config.MapX += (e.getX() - this.mouseX) * multiply;
        Config.MapY += (e.getY() - this.mouseY) * multiply;
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    public void mouseMoved (MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();

    }

    public void mouseWheelMoved (MouseWheelEvent e) {
        Config.addScale(e.getWheelRotation() * Config.REROTATE_UNIT);

    }
}
