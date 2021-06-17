package window.editor.diagrammer.listeners;

import java.awt.geom.Line2D;
import window.editor.diagrammer.elements.ClassShape;
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

            this.relationClicked = this.setAssociationClicked(e);
            this.pointAncrageClicked = this.getPointAncrageClicked(e);

            if (this.relationClicked != null){
                this.relationClicked.setSelected(true);
            }
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

        if (this.pointAncrageClicked != null){
            this.deletePointsAncrageIfNecessary();
        }

        if(this.mouseWheelPressed && SwingUtilities.isMiddleMouseButton(e)){
            this.mouseWheelPressed = false;
        }

        this.pointAncrageClicked = null;

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
                        System.out.println("Index du point d'ancrage cliqué : " + pointAncrage.getIndex());
                        return pointAncrage;
                    }
                }
            }
        }
        return null;
    }

    private RelationShape setAssociationClicked(MouseEvent event){
        for (IShape shape : DiagrammerService.getDrawPanel().getElements()){
            if (shape instanceof RelationShape) {
                RelationShape relation = (RelationShape) shape;
                for (int i = 0; i < relation.getPointsAncrage().size() - 1; i++) {
                    Line2D segment = new Line2D.Double();
                    segment.setLine(relation.getPointsAncrage().get(i).getX(),relation.getPointsAncrage().get(i).getY(),relation.getPointsAncrage().get(i + 1).getX(),relation.getPointsAncrage().get(i + 1).getY());
                    if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, event.getPoint()) <= DiagrammerConstants.DIAGRAMMER_RELATION_CLICK_AREA) {
                        return relation;
                    }
                }
            }
        }
        return null;
    }

    private Line2D getNearestSegment(MouseEvent event){
        for (IShape shape : DiagrammerService.getDrawPanel().getElements()){
            if (shape instanceof RelationShape) {
                RelationShape relation = (RelationShape) shape;
                for (int i = 0; i < relation.getPointsAncrage().size(); i++) {
                    Line2D segment = new Line2D.Double();
                    segment.setLine(relation.getPointsAncrage().get(i).getX(), relation.getPointsAncrage().get(i).getY(), relation.getPointsAncrage().get(i + 1).getX(), relation.getPointsAncrage().get(i + 1).getY());
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
        RelationPointAncrageShape newPointAncrage = new RelationPointAncrageShape(nearestPointOnSegment, newIndex);
        this.relationClicked.addPointAncrage(newPointAncrage, newIndex);
        this.relationClicked.reindexAllPointsAncrage();
        DiagrammerService.drawPanel.repaint();
    }

    private void dragPointAncrageSelected(MouseEvent e){
        int newX = GridUtils.alignToGrid(e.getX(), DiagrammerService.getDrawPanel().getGridSize());
        int newY = GridUtils.alignToGrid(e.getY(), DiagrammerService.getDrawPanel().getGridSize());
        int currentIndex = this.pointAncrageClicked.getIndex();

        RelationPointAncrageShape firstPoint = this.relationClicked.getPointsAncrage().getFirst();
        RelationPointAncrageShape lastPoint = this.relationClicked.getPointsAncrage().getLast();

        ClassShape rightShape = (ClassShape) GeometryUtils.getShapeOnTheRight(this.relationClicked.getSource(), this.relationClicked.getDestination());
        ClassShape leftShape = (ClassShape) GeometryUtils.getShapeOnTheLeft(this.relationClicked.getSource(), this.relationClicked.getDestination());

        ClassShape nearestShape = this.relationClicked.getNearestClassShape(this.pointAncrageClicked);

        RelationPointAncrageShape nearestShapePoint;
        if (nearestShape == this.relationClicked.getSource()){
            nearestShapePoint = firstPoint;
        } else{
            nearestShapePoint = lastPoint;
        }

        // Si le premier ou le dernier point d'ancrage est cliqué
        if (this.pointAncrageClicked.equals(firstPoint) || this.pointAncrageClicked.equals(lastPoint)){
            if (GeometryUtils.pointIsAroundBounds(new Point(newX, newY), nearestShape)) {
                this.pointAncrageClicked.drag(newX, newY);
                if (this.relationClicked.getPointsAncrage().size() > 2){
                    // Si le premier point est cliqué
                    RelationPointAncrageShape pointToAdapt = this.pointAncrageClicked.equals(firstPoint) ? this.relationClicked.getPointsAncrage().get(1) : this.relationClicked.getPointsAncrage().get(lastPoint.getIndex() - 1);
                    // On met à jour le pointà l'index 1 ou le point à l'index n-1 pour l'aligner avec le point cliqué
                    if (this.pointAncrageClicked.y > pointToAdapt.y || this.pointAncrageClicked.y < pointToAdapt.y){
                        pointToAdapt.drag(pointToAdapt.x, newY);
                    }

                    if (this.pointAncrageClicked.x > pointToAdapt.x || this.pointAncrageClicked.x < pointToAdapt.x){
                        pointToAdapt.drag(newX, pointToAdapt.y);
                    }
                }
            }
        } else{
                this.pointAncrageClicked.drag(newX, newY);
        }

        // S'il n'y a que 3 points d'ancrage dans l'association
        if (this.relationClicked.getPointsAncrage().size() == 3){

            if (newX >= leftShape.getBounds().getMinX() && newX <= leftShape.getBounds().getMaxX() &&
                newY >= rightShape.getBounds().getMinY() && newY <= rightShape.getBounds().getMaxY() ){

                firstPoint.drag(newX, (int) leftShape.getBounds().getMaxY());
                lastPoint.drag((int) rightShape.getBounds().getMinX(), newY);

            } else if (newX >= rightShape.getBounds().getMinX() && newX <= rightShape.getBounds().getMaxX() &&
                newY >= leftShape.getBounds().getMinY() && newY <= leftShape.getBounds().getMaxY() ){

                firstPoint.drag((int) leftShape.getBounds().getMaxX(), newY);
                lastPoint.drag(newX, (int) rightShape.getBounds().getMinY());
            }
        }

        if (this.relationClicked.getPointsAncrage().size() > 2){
            // Le deuxième point d'ancrage de l'association doit aligner le point de l'entité sur sa coordonnée Y
            if (currentIndex == 1 || currentIndex == lastPoint.getIndex()-1 && currentIndex < lastPoint.getIndex()){
                if (newY >= nearestShape.getBounds().getMinY() && newY <= nearestShape.getBounds().getMaxY()){
                    nearestShapePoint.drag(nearestShapePoint.x, newY);
                    if (newY < nearestShape.getBounds().getMinY()){
                        nearestShapePoint.drag(nearestShapePoint.x, (int) nearestShape.getBounds().getMinY());
                    } else if (newY > nearestShape.getBounds().getMaxY()){
                        nearestShapePoint.drag(nearestShapePoint.x, (int) nearestShape.getBounds().getMaxY());
                    }
                }

                //  Le deuxième point d'ancrage de l'association doit aligner le point de l'entité sur sa coordonnée X
                if (newX >= nearestShape.getBounds().getMinX() && newX <= nearestShape.getBounds().getMaxX()){
                    nearestShapePoint.drag(newX, nearestShapePoint.y);
                   if (newX < nearestShape.getBounds().getMinX()){
                        nearestShapePoint.drag((int) nearestShape.getBounds().getMinX(), nearestShapePoint.y);
                    } else if (newX > nearestShape.getBounds().getMaxX()){
                        nearestShapePoint.drag((int) nearestShape.getBounds().getMaxX(), nearestShapePoint.y);
                    }
                }
            }
        }

        DiagrammerService.drawPanel.repaint();
    }

    private void deletePointsAncrageIfNecessary(){
        for (int i = 1; i < this.relationClicked.getPointsAncrage().size() - 1; i++) {
            RelationPointAncrageShape actualPoint = this.relationClicked.getPointsAncrage().get(i);
            RelationPointAncrageShape previousPoint = this.relationClicked.getPointsAncrage().get(i - 1);
            RelationPointAncrageShape nextPoint = this.relationClicked.getPointsAncrage().get(i + 1);

            if (actualPoint.y == previousPoint.y && actualPoint.y == nextPoint.y){
                this.relationClicked.getPointsAncrage().remove(actualPoint);
            }

            if (actualPoint.x == previousPoint.x && actualPoint.x == nextPoint.x){
                this.relationClicked.getPointsAncrage().remove(actualPoint);
            }
        }

        this.relationClicked.reindexAllPointsAncrage();
        DiagrammerService.drawPanel.repaint();
    }
}
