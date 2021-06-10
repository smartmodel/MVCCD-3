package window.editor.diagrammer.listeners;

import window.editor.diagrammer.DrawPanel;
import window.editor.diagrammer.interfaces.IShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DrawPanelListener extends MouseAdapter implements KeyListener {

    private DrawPanel drawPanel;

    private boolean ctrlKeyPressed = false;
    private boolean mouseWheelPressed = false;
    private boolean spaceBarPressed = false;

    private Point origin;

    public DrawPanelListener(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);

        if (this.isZoomAllowed()){
            int actualZoom = DrawPanel.getGridSize();
            this.drawPanel.zoom(actualZoom - e.getWheelRotation());
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
        this.ctrlKeyPressed = e.isControlDown();
        this.spaceBarPressed = (e.getKeyCode() == KeyEvent.VK_SPACE);
        this.updateCursor();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        int differenceX = e.getPoint().x - this.origin.x;
        int differenceY = e.getPoint().y - this.origin.y;

        if (this.isScrollAllowed()){
            this.drawPanel.getHandler().scroll(differenceX, differenceY);
        }

        this.origin = e.getPoint();

    }


    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if(this.mouseWheelPressed && SwingUtilities.isMiddleMouseButton(e)){
            this.mouseWheelPressed = false;
        }
        this.updateCursor();

        this.drawPanel.getHandler().endScroll();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.origin = new Point(e.getPoint());
        this.mouseWheelPressed = SwingUtilities.isMiddleMouseButton(e);

        this.updateCursor();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.ctrlKeyPressed = e.isControlDown();

        if (this.spaceBarPressed && (e.getKeyCode() == KeyEvent.VK_SPACE)){
            this.spaceBarPressed = false;
        }

        this.updateCursor();

        this.drawPanel.getHandler().endScroll();
    }

    private void updateCursor(){
        if(this.isScrollAllowed()){
            // Scroll autorisé -> cursor en forme de main
            this.drawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            // Scroll non autorisé -> cursor basique en forme de flèche
            this.drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    private boolean isZoomAllowed(){
        return this.ctrlKeyPressed;
    }
    private boolean isScrollAllowed(){
        return this.spaceBarPressed || this.mouseWheelPressed;
    }
}
