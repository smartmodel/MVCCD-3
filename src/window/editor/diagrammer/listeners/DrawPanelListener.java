package window.editor.diagrammer.listeners;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Line2D.Float;
import window.editor.diagrammer.elements.MCDEntityShape;
import window.editor.diagrammer.elements.RelationPointAncrageShape;
import window.editor.diagrammer.elements.RelationShape;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.palette.PalettePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.GridUtils;


public class DrawPanelListener extends MouseAdapter implements KeyListener {

    private boolean ctrlKeyPressed = false;
    private boolean mouseWheelPressed = false;
    private boolean spaceBarPressed = false;

    private Point origin;

    private RelationPointAncrageShape pointAncrageClicked = null;
    private RelationShape relationClicked = null;


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);

        if (this.isZoomAllowed()){
            int actualZoom = DiagrammerService.getDrawPanel().getGridSize();
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
            this.relationClicked = this.getAssociationClicked(e);
        }

        if (SwingUtilities.isRightMouseButton(e)){
            if (this.relationClicked != null){
                this.addPointAncrage(e);
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
            DiagrammerService.drawPanel.scroll(differenceX, differenceY);
        }


        if (this.pointAncrageClicked != null){
            this.dragPointAncrageSelected(e);
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

        DiagrammerService.drawPanel.endScroll();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.origin = new Point(e.getPoint());
        this.mouseWheelPressed = SwingUtilities.isMiddleMouseButton(e);

        this.pointAncrageClicked = this.getPointAncrageClicked(e);

        this.updateCursor();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.ctrlKeyPressed = e.isControlDown();

        if (this.spaceBarPressed && (e.getKeyCode() == KeyEvent.VK_SPACE)){
            this.spaceBarPressed = false;
        }

        this.updateCursor();

        DiagrammerService.drawPanel.endScroll();
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

    private RelationPointAncrageShape getPointAncrageClicked(MouseEvent event){
        for (IShape shape : DiagrammerService.getDrawPanel().getElements()){
            if (shape instanceof RelationShape){
                for (RelationPointAncrageShape pointAncrage : ((RelationShape) shape).getPointsAncrage()){
                    if (pointAncrage.contains(event.getPoint())) {
                        System.out.println("Point ancrage trouvé");
                        return pointAncrage;
                    }
                }
            }
        }
        return null;
    }

    private RelationShape getAssociationClicked(MouseEvent event){
        for (IShape shape : DiagrammerService.getDrawPanel().getElements()){
            if (shape instanceof RelationShape) {
                for (int i = 0; i < ((RelationShape) shape).getPointsAncrage().size(); i++) {

                    Line2D segment = new Line2D.Double();
                    segment.setLine(((RelationShape) shape).getPointsAncrage().get(i).getX(),((RelationShape) shape).getPointsAncrage().get(i).getY(),((RelationShape) shape).getPointsAncrage().get(i + 1).getX(),((RelationShape) shape).getPointsAncrage().get(i + 1).getY());
                    if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, event.getPoint()) <= DiagrammerConstants.DIAGRAMMER_RELATION_CLICK_AREA) {
                        return (RelationShape) shape;
                    }
                }
            }
        }
        return null;
    }

    private Line2D getNearestSegment(MouseEvent event){
        for (IShape shape : DiagrammerService.getDrawPanel().getElements()){
            if (shape instanceof RelationShape) {
                for (int i = 0; i < ((RelationShape) shape).getPointsAncrage().size(); i++) {

                    Line2D segment = new Line2D.Double();
                    segment.setLine(((RelationShape) shape).getPointsAncrage().get(i).getX(),((RelationShape) shape).getPointsAncrage().get(i).getY(),((RelationShape) shape).getPointsAncrage().get(i + 1).getX(),((RelationShape) shape).getPointsAncrage().get(i + 1).getY());
                    if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, event.getPoint()) <= DiagrammerConstants.DIAGRAMMER_RELATION_CLICK_AREA) {
                        return segment;
                    }
                }
            }
        }
        return null;
    }

    private void addPointAncrage(MouseEvent e){
        Line2D nearestSegment = this.getNearestSegment(e);
        int newIndex = this.relationClicked.getPointAncrageIndex(this.relationClicked.convertPoint2DToPointAncrage(nearestSegment.getP2()));
        System.out.println(newIndex);
        Point nearestPointOnSegment = GeometryUtils.getNearestPointOnLine(
            nearestSegment.getX1(),
            nearestSegment.getY1(),
            nearestSegment.getX2(),
            nearestSegment.getY2(),
            e.getX(),
            e.getY(),
            true,
            null
        );
        RelationPointAncrageShape newPointAncrage = new RelationPointAncrageShape(nearestPointOnSegment);
        this.relationClicked.addPointAncrage(newPointAncrage, newIndex);
        DiagrammerService.drawPanel.repaint();
    }

    private void dragPointAncrageSelected(MouseEvent e){
        int x = GridUtils.alignToGrid(e.getX(), DiagrammerService.getDrawPanel().getGridSize());
        int y = GridUtils.alignToGrid(e.getY(), DiagrammerService.getDrawPanel().getGridSize());
        this.pointAncrageClicked.drag(x, y);
    }
}
