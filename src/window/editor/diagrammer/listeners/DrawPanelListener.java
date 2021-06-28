package window.editor.diagrammer.listeners;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.util.ListIterator;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.menus.PointAncrageMenu;
import window.editor.diagrammer.menus.RelationShapeMenu;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.DiagrammerConstants;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;

public class DrawPanelListener extends MouseAdapter implements KeyListener {

  private boolean ctrlKeyPressed = false;
  private boolean mouseWheelPressed = false;
  private boolean spaceBarPressed = false;
  private Point origin;
  private RelationPointAncrageShape pointAncrageClicked = null;
  private RelationShape relationClicked = null;

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);

    // Déselectionne toutes les formes et reset la création de relation
    DiagrammerService.drawPanel.deselectAllShapes();
    RelationCreator.resetSourceAndDestination();

    this.relationClicked = this.setAssociationClicked(e);
    this.pointAncrageClicked = this.getPointAncrageClicked(e);

    // Aucune association n'est cliquée
    if (this.relationClicked == null) {
      DiagrammerService.drawPanel.deselectAllShapes();
    } else {
      // Sélectionne l'association et déselectionne toutes les autres shapes
      this.relationClicked.setSelected(true);
      DiagrammerService.drawPanel.deselectAllOtherShape(this.relationClicked);

      // Sélectionne le potentiel point ancrage cliqué
      this.pointAncrageClicked = this.getPointAncrageClicked(e);

    }

    // Gestion de l'action à exécuter lors d'un clic gauche
    if (SwingUtilities.isLeftMouseButton(e) && PalettePanel.activeButton != null) {
      this.executeButtonAction(e);
      PalettePanel.setActiveButton(null);
    }

    // Gestion du clic droit
    if (SwingUtilities.isRightMouseButton(e)) {
      if (this.pointAncrageClicked != null) {
        this.showPointAncrageMenu(e);
      } else if (this.relationClicked != null) {
        this.showRelationMenu(e);
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.origin = new Point(e.getPoint());
    this.mouseWheelPressed = SwingUtilities.isMiddleMouseButton(e);
    this.pointAncrageClicked = this.getPointAncrageClicked(e);
    this.updateCursor();

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    if (pointAncrageClicked != null && relationClicked != null) {
      deletePointsAncrageIfNecessary();
    }
    if (mouseWheelPressed && SwingUtilities.isMiddleMouseButton(e)) {
      mouseWheelPressed = false;
    }
    pointAncrageClicked = null;
    updateCursor();
    DiagrammerService.drawPanel.endScroll();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    super.mouseEntered(e);
    // TODO -> Voir pour enlever ce bout de code
    if (DiagrammerService.getDrawPanel().contains(e.getPoint())) {
      DiagrammerService.getDrawPanel().grabFocus();
    }
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    super.mouseWheelMoved(e);
    if (this.isZoomAllowed()) {
      int actualZoom = DiagrammerService.getDrawPanel().getGridSize();
      DiagrammerService.getDrawPanel().zoom(actualZoom - e.getWheelRotation());
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    super.mouseDragged(e);
    int differenceX = e.getPoint().x - this.origin.x;
    int differenceY = e.getPoint().y - this.origin.y;
    if (this.isScrollAllowed()) {
      DiagrammerService.drawPanel.scroll(differenceX, differenceY);
    }
    if (this.pointAncrageClicked != null) {
      // Déplacement d'un point d'ancrage
      this.dragPointAncrageSelected(e);
    } else if (this.relationClicked != null && this.pointAncrageClicked == null) {
      // Déplacement de l'association
      this.dragAssociation(differenceX, differenceY);
    }
    this.origin = e.getPoint();

  }

  @Override
  public void mouseMoved(MouseEvent e) {
    super.mouseMoved(e);
    // Change le curseur lors du survol de l'association cliquée
    if (this.relationClicked != null) {
      for (Line2D segment : this.relationClicked.getSegments()) {
        if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, e.getPoint()) <= DiagrammerConstants.DIAGRAMMER_RELATION_CLICK_AREA) {
          DiagrammerService.drawPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        } else {
          DiagrammerService.drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
      }
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
  public void keyReleased(KeyEvent e) {
    this.ctrlKeyPressed = e.isControlDown();
    if (this.spaceBarPressed && (e.getKeyCode() == KeyEvent.VK_SPACE)) {
      this.spaceBarPressed = false;
    }
    this.updateCursor();
    DiagrammerService.drawPanel.endScroll();
  }

  private void updateCursor() {
    if (this.isScrollAllowed()) {
      // Scroll autorisé -> cursor en forme de main
      DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
    } else {
      // Scroll non autorisé -> cursor basique en forme de flèche
      DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }

  private boolean isZoomAllowed() {
    return this.ctrlKeyPressed;
  }

  private boolean isScrollAllowed() {
    return this.spaceBarPressed || this.mouseWheelPressed;
  }

  private void createEntityShape(MouseEvent event) {
    Point mouseClick = event.getPoint();
    MCDEntityShape shape = new MCDEntityShape();
    shape.setLocation(GridUtils.alignToGrid(mouseClick.x, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(mouseClick.y, DiagrammerService.getDrawPanel().getGridSize()));
    DiagrammerService.getDrawPanel().addElement(shape);
    DiagrammerService.getDrawPanel().repaint();
  }

  private void executeButtonAction(MouseEvent event) {
    if (PalettePanel.activeButton.getText().equals(DiagrammerConstants.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT)) {
      this.createEntityShape(event);
    } else if (PalettePanel.activeButton.getText().equals(DiagrammerConstants.DIAGRAMMER_PALETTE_ASSOCIATION_BUTTON_TEXT)) {
      //this.createAssociation();
    }
  }

  private RelationPointAncrageShape getPointAncrageClicked(MouseEvent event) {
    for (IShape shape : DiagrammerService.getDrawPanel().getElements()) {
      if (shape instanceof RelationShape) {
        for (RelationPointAncrageShape pointAncrage : ((RelationShape) shape).getPointsAncrage()) {
          if (pointAncrage.contains(event.getPoint())) {
            return pointAncrage;
          }
        }
      }
    }
    return null;
  }

  private RelationShape setAssociationClicked(MouseEvent event) {
    for (IShape shape : DiagrammerService.getDrawPanel().getElements()) {
      if (shape instanceof RelationShape) {
        RelationShape relation = (RelationShape) shape;
        for (int i = 0; i < relation.getPointsAncrage().size() - 1; i++) {
          Line2D segment = new Line2D.Double();
          segment.setLine(relation.getPointsAncrage().get(i).getX(), relation.getPointsAncrage().get(i).getY(), relation.getPointsAncrage().get(i + 1).getX(), relation.getPointsAncrage().get(i + 1).getY());
          if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, event.getPoint()) <= DiagrammerConstants.DIAGRAMMER_RELATION_CLICK_AREA) {
            return relation;
          }
        }
      }
    }
    return null;
  }

  private void dragPointAncrageSelected(MouseEvent e) {
    Point newPoint = new Point(GridUtils.alignToGrid(e.getX(), DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(e.getY(), DiagrammerService.getDrawPanel().getGridSize()));
    ClassShape nearestClassShape = relationClicked.getNearestClassShape(pointAncrageClicked);
    if (pointAncrageClicked.getIndex() == 0 || pointAncrageClicked.getIndex() == relationClicked.getPointsAncrage().getLast().getIndex()) {
      this.dragFirstOrLastPointAncrage(newPoint, nearestClassShape);
      // Met à jour les points aux index 1 ou n-1 si nécessaire
    } else {
      this.dragPointAncrage(newPoint);
    }
    if (relationClicked.getPointsAncrage().size() > 2) {
      this.dragPointAtIndex1orNMinus1(newPoint);
    }
    DiagrammerService.drawPanel.repaint();
  }

  private void deletePointsAncrageIfNecessary() {
    boolean updateNecessary = false;
    if (relationClicked.getPointsAncrage().size() > 2) {
      ListIterator<RelationPointAncrageShape> iterator = relationClicked.getPointsAncrage().listIterator();
      RelationPointAncrageShape leftNeighbour = iterator.next();
      RelationPointAncrageShape pointToCheck = iterator.next();
      while (iterator.hasNext()) {
        RelationPointAncrageShape rightNeighbour = iterator.next();
        if (GeometryUtils.getDistanceBetweenLineAndPoint(leftNeighbour, rightNeighbour, pointToCheck) < DiagrammerConstants.DIAGRAMMER_RELATION_CLICK_AREA) {
          updateNecessary = true;
          iterator.previous();
          iterator.previous();
          iterator.remove();
          pointToCheck = iterator.next();
        } else {
          leftNeighbour = pointToCheck;
          pointToCheck = rightNeighbour;
        }
      }
    }
    if (updateNecessary) {
      relationClicked.reindexAllPointsAncrage();
    }
    DiagrammerService.drawPanel.repaint();
  }

  private void dragFirstOrLastPointAncrage(Point newPoint, ClassShape nearestClassShape) {
    // Si le point sélectionné est le premier ou le dernier de la relation (sur les ClassShape), on ne le déplace que s'il est sur les bords d'une ClassShape
    if (pointAncrageClicked.getIndex() == 0 || pointAncrageClicked.getIndex() == relationClicked.getPointsAncrage().getLast().getIndex()) {
      if (GeometryUtils.pointIsAroundShape(newPoint, nearestClassShape)) {
        pointAncrageClicked.drag(newPoint.x, newPoint.y);
      }
    }
  }

  private void dragPointAncrage(Point newPoint) {
    pointAncrageClicked.drag(newPoint.x, newPoint.y);
  }

  private void dragPointAtIndex1orNMinus1(Point newPoint) {
    // Si le point sélectionné est le deuxième point de l'association ou l'avant dernier
    if (pointAncrageClicked.getIndex() == 1 || pointAncrageClicked.getIndex() == relationClicked.getPointsAncrage().getLast().getIndex() - 1) {
      ClassShape leftShape = (ClassShape) GeometryUtils.getShapeOnTheLeft(relationClicked.getSource(), relationClicked.getDestination());
      ClassShape rightShape = (ClassShape) GeometryUtils.getShapeOnTheRight(relationClicked.getSource(), relationClicked.getDestination());

      RelationPointAncrageShape previousPoint;
      RelationPointAncrageShape nextPoint;

      if (relationClicked.getPointsAncrage().size() < 3) {
        previousPoint = GeometryUtils.getNearestPointAncrage(leftShape, relationClicked);
        nextPoint = GeometryUtils.getNearestPointAncrage(rightShape, relationClicked);
      } else {
        previousPoint = relationClicked.getPointsAncrage().get(pointAncrageClicked.getIndex() - 1);
        nextPoint = relationClicked.getPointsAncrage().get(pointAncrageClicked.getIndex() + 1);
      }

      // Si la forme à droite est plus haute que celle de gauche
      if (rightShape.getBounds().getMinY() < leftShape.getBounds().getMinY()) {
        Point newPreviousPoint = newPoint;
        Point newNextPoint = newPoint;
        // Si le point à l'index 1 se situe au dessus de la ClassShape de gauche
        if (newPoint.y < leftShape.getBounds().getMinY()) {
          // Si le point précédent est celui croché sur une ClassShape, on le met à jour
          if (previousPoint == relationClicked.getPointsAncrage().getFirst() || previousPoint == relationClicked.getPointsAncrage().getLast()) {
            newPreviousPoint = new Point(newPoint.x, (int) leftShape.getBounds().getMinY());
            newNextPoint = new Point((int) rightShape.getBounds().getMinX(), newPoint.y);
          }
        }
        // Si le point à l'index 1 se situe en dessous de la ClassShape de droite
        if (newPoint.y > rightShape.getBounds().getMaxY()) {
          // Si le point suivant est celui croché sur la ClassShape, on le met à jour
          if (nextPoint == relationClicked.getPointsAncrage().getLast() || nextPoint == relationClicked.getPointsAncrage().getFirst()) {
            newPreviousPoint = new Point((int) leftShape.getBounds().getMaxX(), newPoint.y);
            newNextPoint = new Point(newPoint.x, (int) rightShape.getBounds().getMaxY());
          }
        }
        // Si le point à l'index 1 à un point commun en Y entre les deux ClassShape
        if ((newPoint.y >= leftShape.getBounds().getMinY() && newPoint.y <= leftShape.getBounds().getMaxY()) && (newPoint.y >= rightShape.getBounds().getMinY() && newPoint.y <= rightShape.getBounds().getMaxY())) {
          newNextPoint = new Point((int) rightShape.getBounds().getMinX(), newPoint.y);
          newPreviousPoint = new Point((int) leftShape.getBounds().getMaxX(), newPoint.y);
        }
        // Si les nouveaux points se situent autour des ClassShape
        if (GeometryUtils.pointIsAroundShape(newPreviousPoint, leftShape) && GeometryUtils.pointIsAroundShape(newNextPoint, rightShape)) {
          previousPoint.drag(newPreviousPoint.x, newPreviousPoint.y);
          nextPoint.drag(newNextPoint.x, newNextPoint.y);
        }
      }
      // Si la forme à gauche est plus haute que celle de droite
      if (leftShape.getBounds().getMinY() < rightShape.getBounds().getMinY()) {
        Point newPreviousPoint = newPoint;
        Point newNextPoint = newPoint;
        // Si le point à l'index 1 se situe au dessus de la ClassShape de droite
        if (newPoint.y < rightShape.getBounds().getMinY()) {
          // Si le point suivant est celui croché sur une ClassShape, on le met à jour
          if (nextPoint == relationClicked.getPointsAncrage().getLast() || nextPoint == relationClicked.getPointsAncrage().getFirst()) {
            newPreviousPoint = new Point(previousPoint.x, newPoint.y);
            newNextPoint = new Point(newPoint.x, (int) rightShape.getBounds().getMinY());
          }
        }
        // Si le point à l'index 1 se situe en dessous de la ClassShape de gauche
        else if (newPoint.y > leftShape.getBounds().getMaxY()) {
          // Si le point précédent est celui croché sur la ClassShape, on le met à jour
          if (previousPoint == relationClicked.getPointsAncrage().getFirst() || previousPoint == relationClicked.getPointsAncrage().getLast()) {
            newPreviousPoint = new Point(newPoint.x, (int) leftShape.getBounds().getMaxY());
            newNextPoint = new Point((int) rightShape.getBounds().getMinX(), newPoint.y);
          }
        }
        // Si le point à l'index 1 à un point commun en Y entre les deux ClassShape
        if ((newPoint.y >= leftShape.getBounds().getMinY() && newPoint.y <= leftShape.getBounds().getMaxY()) && (newPoint.y >= rightShape.getBounds().getMinY() && newPoint.y <= rightShape.getBounds().getMaxY())) {
          newNextPoint = new Point((int) rightShape.getBounds().getMinX(), newPoint.y);
          newPreviousPoint = new Point((int) leftShape.getBounds().getMaxX(), newPoint.y);
        }
        // Si le point à l'index 1 à un point commun en X entre les deux ClassShape
        if ((newPoint.x >= leftShape.getBounds().getMinX() && newPoint.x <= leftShape.getBounds().getMaxX()) && (newPoint.x >= rightShape.getBounds().getMinX() && newPoint.x <= rightShape.getBounds().getMaxX())) {
          newNextPoint = new Point(newPoint.x, (int) rightShape.getBounds().getMinY());
          newPreviousPoint = new Point(newPoint.x, (int) leftShape.getBounds().getMaxY());
        }
        // Si les nouveaux points se situent autour des ClassShape
        if (GeometryUtils.pointIsAroundShape(newPreviousPoint, leftShape) && GeometryUtils.pointIsAroundShape(newNextPoint, rightShape)) {
          // Si le prochain point est le dernier, celui croché sur la ClassShape, on met à jour le point sur l'entité
          previousPoint.drag(newPreviousPoint.x, newPreviousPoint.y);
          nextPoint.drag(newNextPoint.x, newNextPoint.y);
        }
      }
    }
  }

  private void dragAssociation(int differenceX, int differenceY) {
    if (this.relationClicked.getPointsAncrage().size() == 2) {
      Point firstPoint = this.relationClicked.getPointsAncrage().getFirst();
      Point lastPoint = this.relationClicked.getPointsAncrage().getLast();

      // Crée un segment fictif
      Line2D segment = new Line2D.Double();
      segment.setLine(firstPoint.x, firstPoint.y, lastPoint.x, lastPoint.y);

      Point newFirstPoint = new Point(firstPoint.x + differenceX, firstPoint.y + differenceY);
      Point newSecondPoint = new Point(lastPoint.x + differenceX, lastPoint.y + differenceY);

      // Si le segment est horizontal, on déplace l'association
      if (GeometryUtils.isHorizontal(segment)) {
        if (GeometryUtils.pointIsAroundShape(newFirstPoint, this.relationClicked.getSource()) && GeometryUtils.pointIsAroundShape(newSecondPoint, this.relationClicked.getDestination())) {
          this.relationClicked.setLocationDifference(0, differenceY);
        }
        // Si le segment est vertical, on déplace l'association
      } else if (GeometryUtils.isVertical(segment)) {
        if (GeometryUtils.pointIsAroundShape(newFirstPoint, this.relationClicked.getSource()) && GeometryUtils.pointIsAroundShape(newSecondPoint, this.relationClicked.getDestination())) {
          this.relationClicked.setLocationDifference(differenceX, 0);
        }
      }
      DiagrammerService.drawPanel.repaint();
    }
  }

  private void showPointAncrageMenu(MouseEvent event) {
    Point converted = SwingUtilities.convertPoint(this.relationClicked, event.getPoint(), DiagrammerService.drawPanel);
    PointAncrageMenu menu = new PointAncrageMenu(this.pointAncrageClicked, this.relationClicked);
    menu.show(DiagrammerService.drawPanel, converted.x, converted.y);
  }

  private void showRelationMenu(MouseEvent event) {
    Point converted = SwingUtilities.convertPoint(this.relationClicked, event.getPoint(), DiagrammerService.drawPanel);
    RelationShapeMenu menu = new RelationShapeMenu(this.relationClicked, converted.x, converted.y);
    menu.show(DiagrammerService.drawPanel, converted.x, converted.y);
  }

}
