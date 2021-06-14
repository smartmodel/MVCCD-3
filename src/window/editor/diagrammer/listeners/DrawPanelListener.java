package window.editor.diagrammer.listeners;

import main.MVCCDManager;
import window.editor.diagrammer.elements.MCDEntityShape;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.panels.DrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.utils.GridUtils;


public class DrawPanelListener extends MouseAdapter implements KeyListener {

    private boolean ctrlKeyPressed = false;
    private boolean mouseWheelPressed = false;
    private boolean spaceBarPressed = false;

    private Point origin;


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);

        if (this.isZoomAllowed()){
            int actualZoom = DrawPanel.getGridSize();
            DiagrammerService.getDrawPanel().zoom(actualZoom - e.getWheelRotation());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (SwingUtilities.isLeftMouseButton(e)){
            if (PalettePanel.activeButton != null){
                this.executeButtonAction(e);
                PalettePanel.setActiveButton(null);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (DiagrammerService.getDrawPanel().contains(e.getPoint())){
            DiagrammerService.getDrawPanel().grabFocus();
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
            DiagrammerService.getDrawPanel().getHandler().scroll(differenceX, differenceY);
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

        DiagrammerService.getDrawPanel().getHandler().endScroll();
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

        DiagrammerService.getDrawPanel().getHandler().endScroll();
    }

    private void updateCursor(){
        if(this.isScrollAllowed()){
            // Scroll autorisé -> cursor en forme de main
            DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            // Scroll non autorisé -> cursor basique en forme de flèche
            DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    private boolean isZoomAllowed(){
        return this.ctrlKeyPressed;
    }
    private boolean isScrollAllowed(){
        return this.spaceBarPressed || this.mouseWheelPressed;
    }

    private void createEntityShape(MouseEvent event){
        Point mouseClick = event.getPoint();
        MCDEntityShape shape = new MCDEntityShape();
        shape.setLocation(GridUtils.alignToGrid(mouseClick.x, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(mouseClick.y, DiagrammerService.getDrawPanel().getGridSize()));
        DiagrammerService.getDrawPanel().addElement(shape);
        DiagrammerService.getDrawPanel().repaint();
    }
    private void executeButtonAction(MouseEvent event){
        if (PalettePanel.activeButton.getText().equals(DiagrammerConstants.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT)){
            this.createEntityShape(event);
        }
    }
}
