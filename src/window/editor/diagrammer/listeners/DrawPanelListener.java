package window.editor.diagrammer.listeners;

import window.editor.diagrammer.DrawPanel;

import java.awt.*;
import java.awt.event.*;


public class DrawPanelListener extends MouseAdapter implements KeyListener {

    private DrawPanel drawPanel;
    private boolean zoomAllowed = false;
    private boolean dragAllowed = false;

    public DrawPanelListener(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);

        if (this.zoomAllowed){
            int actualZoom = this.drawPanel.getGridSize();
            this.drawPanel.setGridAndZoom(actualZoom - e.getWheelRotation());
            this.drawPanel.getHandler().zoomElements(actualZoom, actualZoom - e.getWheelRotation());
            this.drawPanel.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (this.drawPanel.contains(e.getPoint())){
            this.drawPanel.grabFocus();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.zoomAllowed = e.isControlDown();
        this.dragAllowed = (e.getKeyCode() == KeyEvent.VK_SPACE);
        if (this.dragAllowed){
            this.drawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.zoomAllowed = e.isControlDown();
        this.drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
