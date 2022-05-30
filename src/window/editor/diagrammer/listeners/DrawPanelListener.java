package window.editor.diagrammer.listeners;

import main.MVCCDManager;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.menus.PointAncrageMenu;
import window.editor.diagrammer.menus.RelationShapeMenu;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GeometryUtils;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ListIterator;

public class DrawPanelListener extends MouseAdapter implements KeyListener, Serializable {

  private static final long serialVersionUID = 1000;
  private final Cursor CURSOR_ENTITY_ICON;
  private boolean ctrlKeyPressed = false;
  private boolean mouseWheelPressed = false;
  private boolean spaceBarPressed = false;
  private Point origin;
  private RelationPointAncrageShape pointAncrageClicked = null;
  private RelationShape relationClicked = null;

  public DrawPanelListener() {
    this.CURSOR_ENTITY_ICON = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("ressources/icons-diagrammer/palette/icon_entity.png").getImage(), new Point(0, 0), "cursorEntityIcon");
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);

    // Déselectionne toutes les formes et reset la création de relation
    DiagrammerService.getDrawPanel().deselectAllShapes();

    if (RelationCreator.isCreating) {
      RelationCreator.resetSourceAndDestination();
      RelationCreator.setIsCreating(false);
    }

    this.relationClicked = this.setAssociationClicked(e);

    // Aucune association n'est cliquée
    if (this.relationClickedIsNull()) {
      DiagrammerService.getDrawPanel().deselectAllShapes();
    } else {
      // Sélectionne l'association et vérifie si un point d'ancrage est cliqué
      this.relationClicked.setSelected(true);
      this.pointAncrageClicked = this.getPointAncrageClicked(e);
    }

    // Gestion de l'action à exécuter lors d'un clic gauche
    if (SwingUtilities.isLeftMouseButton(e) && PalettePanel.activeButton != null) {
      this.executeButtonAction(e);
      PalettePanel.setActiveButton(null);
    }

    // Gestion du clic droit
    if (SwingUtilities.isRightMouseButton(e)) {
      if (!this.pointAncrageClickedIsNull()) {
        this.showPointAncrageMenu(e);
      } else if (!this.relationClickedIsNull()) {
        this.showRelationMenu(e);
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.origin = e.getPoint();
    this.mouseWheelPressed = SwingUtilities.isMiddleMouseButton(e);
    this.pointAncrageClicked = this.getPointAncrageClicked(e);
    this.updateCursor();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    if (!this.pointAncrageClickedIsNull() && !this.relationClickedIsNull()) {
      //this.deletePointsAncrageIfNecessary();
    }
    if (this.mouseWheelPressed && SwingUtilities.isMiddleMouseButton(e)) {
      this.mouseWheelPressed = false;
    }
    this.pointAncrageClicked = null;
    this.updateCursor();

    DiagrammerService.getDrawPanel().endScroll();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    super.mouseEntered(e);
    DiagrammerService.getDrawPanel().grabFocus();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    super.mouseWheelMoved(e);
    if (this.isZoomAllowed()) {
      final int actualZoom = DiagrammerService.getDrawPanel().getGridSize();
      DiagrammerService.getDrawPanel().zoom(actualZoom - e.getWheelRotation());
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    super.mouseDragged(e);

    final int differenceX = e.getPoint().x - this.origin.x;
    final int differenceY = e.getPoint().y - this.origin.y;

    if (this.isScrollAllowed()) {
      DiagrammerService.getDrawPanel().scroll(differenceX, differenceY);
    }

    if (!this.pointAncrageClickedIsNull() && !this.relationClickedIsNull()) {
      this.dragPointAncrageSelected(e);
    } else if (!this.relationClickedIsNull() && this.pointAncrageClickedIsNull()) {
      this.dragAssociation(differenceX, differenceY);
    }

    this.origin = e.getPoint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    super.mouseMoved(e);

    // Change le curseur lors du survol de l'association cliquée
    if (!this.relationClickedIsNull()) {
      for (Line2D segment : this.relationClicked.getSegments()) {
        if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, e.getPoint()) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
          DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.MOVE_CURSOR));
        } else {
          DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
      }
    }

    if (RelationCreator.isCreating) {
      DiagrammerService.getDrawPanel().repaint();
    }

    if (PalettePanel.activeButton != null && PalettePanel.activeButton.getText().equals(Preferences.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT)) {
      DiagrammerService.getDrawPanel().setCursor(this.CURSOR_ENTITY_ICON);
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
    DiagrammerService.getDrawPanel().endScroll();
  }

  private void updateCursor() {
    if (this.isScrollAllowed()) {
      DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
    } else {
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
    final Point mouseClick = event.getPoint();
    final MCDEntityShape shape = new MCDEntityShape();

    shape.setLocation(GridUtils.alignToGrid(mouseClick.x, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(mouseClick.y, DiagrammerService.getDrawPanel().getGridSize()));

    MVCCDManager.instance().getCurrentDiagram().addShape(shape);
    DiagrammerService.getDrawPanel().addShape(shape);
    DiagrammerService.getDrawPanel().repaint();

  }

  private void executeButtonAction(MouseEvent event) {
    if (PalettePanel.activeButton.getText().equals(Preferences.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT)) {
      this.createEntityShape(event);
    }
  }

  private RelationPointAncrageShape getPointAncrageClicked(MouseEvent event) {
    for (RelationShape relation : DiagrammerService.getDrawPanel().getRelationShapes()) {
      for (RelationPointAncrageShape pointAncrage : relation.getPointsAncrage()) {
        if (pointAncrage.contains(event.getPoint())) {
          return pointAncrage;
        }
      }
    }
    return null;
  }

  private RelationShape setAssociationClicked(MouseEvent event) {
    for (RelationShape relation : DiagrammerService.getDrawPanel().getRelationShapes()) {
      for (int i = 0; i < relation.getPointsAncrage().size() - 1; i++) {
        final Line2D segment = new Line2D.Double();
        segment.setLine(relation.getPointsAncrage().get(i).getX(), relation.getPointsAncrage().get(i).getY(), relation.getPointsAncrage().get(i + 1).getX(), relation.getPointsAncrage().get(i + 1).getY());
        if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, event.getPoint()) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
          return relation;
        }
      }
    }
    return null;
  }

  private void dragPointAncrageSelected(MouseEvent e) {
    final Point newPoint = new Point(GridUtils.alignToGrid(e.getX(), DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(e.getY(), DiagrammerService.getDrawPanel().getGridSize()));
    if (this.relationClicked.isFirstOrLastPoint(this.pointAncrageClicked)) {
      SquaredShape nearestClassShape = (SquaredShape) this.relationClicked.getNearestClassShape(this.pointAncrageClicked);
      this.dragFirstOrLastPointAncrage(newPoint, nearestClassShape);
    } else {
      this.dragPointAncrage(newPoint);
    }
    // TODO -> Enlever le !relationClicked.isReflexive() lorsque le comportement des points d'ancrage d'une association réflexive aura été implémenté.
    if (this.relationClicked.getPointsAncrage().size() == 3 && !this.relationClicked.isReflexive()) {
      // Met à jour les points aux index 1 ou n-1 si nécessaire
      this.dragPointAtIndex1orNMinus1(newPoint);
    }
    DiagrammerService.getDrawPanel().repaint();
  }

  private void deletePointsAncrageIfNecessary() {
    boolean updateNecessary = false;
    if (this.relationClicked.getPointsAncrage().size() > 2) {
      final ListIterator<RelationPointAncrageShape> iterator = this.relationClicked.getPointsAncrage().listIterator();
      RelationPointAncrageShape leftNeighbour = iterator.next();
      RelationPointAncrageShape pointToCheck = iterator.next();
      while (iterator.hasNext()) {
        RelationPointAncrageShape rightNeighbour = iterator.next();
        if (GeometryUtils.getDistanceBetweenLineAndPoint(leftNeighbour, rightNeighbour, pointToCheck) < Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
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
      this.relationClicked.reindexAllPointsAncrage();
    }
    DiagrammerService.getDrawPanel().repaint();
  }

  private void dragFirstOrLastPointAncrage(Point newPoint, SquaredShape nearestClassShape) {
    // Si le point sélectionné est le premier ou le dernier de la relation (sur les ClassShape), on ne le déplace que s'il est sur les bords d'une ClassShape
    if (GeometryUtils.pointIsAroundShape(newPoint, nearestClassShape)) {
      this.pointAncrageClicked.drag(newPoint.x, newPoint.y);
    } else {
      // Si la nouvelle coordonnée X est hors du contour de la ClassShape, on met quand même à jour le point d'ancrage sans modifier sa position X
      if (GeometryUtils.xCoordinateIsOutsideShape(newPoint.x, nearestClassShape)) {
        if (GeometryUtils.pointIsAroundShape(new Point(this.pointAncrageClicked.x, newPoint.y), nearestClassShape)) {
          this.pointAncrageClicked.drag(this.pointAncrageClicked.x, newPoint.y);
        }
      }
      // Si la nouvelle coordonnée Y est hors du contour de la ClassShape, on met quand même à jour le point d'ancrage sans modifier sa position Y
      if (GeometryUtils.yCoordinateIsOutsideShape(newPoint.y, nearestClassShape)) {
        if (GeometryUtils.pointIsAroundShape(new Point(newPoint.x, this.pointAncrageClicked.y), nearestClassShape)) {
          this.pointAncrageClicked.drag(newPoint.x, this.pointAncrageClicked.y);
        }
      }
    }
  }

  private void dragPointAncrage(Point newPoint) {
    this.pointAncrageClicked.drag(newPoint.x, newPoint.y);
  }

  private void dragPointAtIndex1orNMinus1(Point newPoint) {
    // Si le point sélectionné est le deuxième point de l'association ou l'avant dernier
    if (this.pointAncrageClicked.getIndex() == 1 || this.pointAncrageClicked.getIndex() == this.relationClicked.getLastPoint().getIndex() - 1) {
      final SquaredShape leftShape = (SquaredShape) GeometryUtils.getShapeOnTheLeft(this.relationClicked.getSource(), this.relationClicked.getDestination());
      final SquaredShape rightShape = (SquaredShape) GeometryUtils.getShapeOnTheRight(this.relationClicked.getSource(), this.relationClicked.getDestination());

      final RelationPointAncrageShape previousPoint = GeometryUtils.getNearestPointAncrage(leftShape, this.relationClicked);
      final RelationPointAncrageShape nextPoint = GeometryUtils.getNearestPointAncrage(rightShape, this.relationClicked);

      if (rightShape != null && leftShape != null) {
        // Si la forme à droite est plus haute que celle de gauche
        if (GeometryUtils.isHigher(rightShape, leftShape)) {

          Point newPreviousPoint = newPoint;
          Point newNextPoint = newPoint;
          // Si le point à l'index 1 se situe au dessus de la ClassShape de gauche
          if (GeometryUtils.pointIsAboveShape(newPoint, leftShape)) {
            // Si le point précédent est celui croché sur une ClassShape, on le met à jour
            if (this.relationClicked.isFirstOrLastPoint(previousPoint)) {
              newPreviousPoint = new Point(newPoint.x, (int) leftShape.getBounds().getMinY());
              newNextPoint = new Point((int) rightShape.getBounds().getMinX(), newPoint.y);
            }
          }
          // Si le point à l'index 1 se situe en dessous de la ClassShape de droite
          if (GeometryUtils.pointIsUnderShape(newPoint, rightShape)) {
            // Si le point suivant est celui croché sur la ClassShape, on le met à jour
            if (this.relationClicked.isFirstOrLastPoint(nextPoint)) {
              newPreviousPoint = new Point((int) leftShape.getBounds().getMaxX(), newPoint.y);
              newNextPoint = new Point(newPoint.x, (int) rightShape.getBounds().getMaxY());
            }
          }
          // Si le point à l'index 1 à un point commun en Y entre les deux ClassShape
          if (GeometryUtils.pointHasCommonYWithShapes(newPoint, leftShape, rightShape)) {
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
        if (GeometryUtils.isHigher(leftShape, rightShape)) {
          Point newPreviousPoint = newPoint;
          Point newNextPoint = newPoint;
          // Si le point à l'index 1 se situe au dessus de la ClassShape de droite
          if (GeometryUtils.pointIsAboveShape(newPoint, rightShape)) {
            // Si le point suivant est celui croché sur une ClassShape, on le met à jour
            if (this.relationClicked.isFirstOrLastPoint(nextPoint)) {
              newPreviousPoint = new Point((int) leftShape.getBounds().getMaxX(), newPoint.y);
              newNextPoint = new Point(newPoint.x, (int) rightShape.getBounds().getMinY());
            }
          }
          // Si le point à l'index 1 se situe en dessous de la ClassShape de gauche
          else if (GeometryUtils.pointIsUnderShape(newPoint, leftShape)) {
            // Si le point précédent est celui croché sur la ClassShape, on le met à jour
            if (this.relationClicked.isFirstOrLastPoint(previousPoint)) {
              newPreviousPoint = new Point(newPoint.x, (int) leftShape.getBounds().getMaxY());
              newNextPoint = new Point((int) rightShape.getBounds().getMinX(), newPoint.y);
            }
          }
          // Si le point à l'index 1 à un point commun en Y entre les deux ClassShape
          if (GeometryUtils.pointHasCommonYWithShapes(newPoint, leftShape, rightShape)) {
            newNextPoint = new Point((int) rightShape.getBounds().getMinX(), newPoint.y);
            newPreviousPoint = new Point((int) leftShape.getBounds().getMaxX(), newPoint.y);
          }
          // Si le point à l'index 1 à un point commun en X entre les deux ClassShape
          if (GeometryUtils.pointHasCommonXWithShapes(newPoint, leftShape, rightShape)) {
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
  }

  private void dragAssociation(int differenceX, int differenceY) {
    if (this.relationClicked.getPointsAncrage().size() == 2) {
      final Point firstPoint = this.relationClicked.getFirstPoint();
      final Point lastPoint = this.relationClicked.getLastPoint();

      // Crée un segment fictif
      final Line2D segment = new Line2D.Double();
      segment.setLine(firstPoint.x, firstPoint.y, lastPoint.x, lastPoint.y);

      final Point newFirstPoint = new Point(firstPoint.x + differenceX, firstPoint.y + differenceY);
      final Point newSecondPoint = new Point(lastPoint.x + differenceX, lastPoint.y + differenceY);

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
      DiagrammerService.getDrawPanel().repaint();
    }
  }

  private void showPointAncrageMenu(MouseEvent event) {
    final Point converted = SwingUtilities.convertPoint(this.relationClicked, event.getPoint(), DiagrammerService.getDrawPanel());
    final PointAncrageMenu menu = new PointAncrageMenu(this.pointAncrageClicked, this.relationClicked);
    menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
  }

  private void showRelationMenu(MouseEvent event) {
    final Point converted = SwingUtilities.convertPoint(this.relationClicked, event.getPoint(), DiagrammerService.getDrawPanel());
    final RelationShapeMenu menu = new RelationShapeMenu(this.relationClicked, converted.x, converted.y);
    menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
  }

  private boolean relationClickedIsNull() {
    return this.relationClicked == null;
  }

  private boolean pointAncrageClickedIsNull() {
    return this.pointAncrageClicked == null;
  }

}
