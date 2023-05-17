/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère les mouvements de la zone de dessin du diagrammeur ainsi que les autres événements
 * Par exemple : drag d'une association, clic droit, etc.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */


package window.editor.diagrammer.listeners;

import main.MVCCDManager;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.NoteShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.menus.AnchorPointMenu;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DrawPanelListener extends MouseAdapter implements KeyListener, Serializable {

  private static final long serialVersionUID = 1000;
  private final Cursor CURSOR_ENTITY_ICON;
  private boolean ctrlKeyPressed = false;
  private boolean mouseWheelPressed = false;
  private boolean spaceBarPressed = false;
  private Point origin;
  private RelationAnchorPointShape anchorPointClicked = null;
  private RelationShape focusedRelation = null;
  private List<RelationAnchorPointShape> anchorPointsToMove = new LinkedList<>();
  ;

  public DrawPanelListener() {
    this.CURSOR_ENTITY_ICON = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("ressources/icons-diagrammer/palette/icon_entity.png").getImage(), new Point(0, 0), "cursorEntityIcon");
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);

    // Déselectionne toutes les formes et reset la création de relation
    DiagrammerService.getDrawPanel().deselectAllShapes();

    this.checkForClickedRelation(e);

    // Aucune association n'est cliquée
    if (this.focusedRelation != null) {
      DiagrammerService.getDrawPanel().deselectAllShapes();
    } else {
      // Sélectionne l'association et vérifie si un point d'ancrage est cliqué
      this.anchorPointClicked = this.getAnchorPointClicked(e);

    }

    // Gestion de l'action à exécuter lors d'un clic gauche
    if (SwingUtilities.isLeftMouseButton(e) && PalettePanel.activeButton != null) {
      this.executeButtonAction(e);
      PalettePanel.setActiveButton(null);
    }

    // Gestion du clic droit
    if (SwingUtilities.isRightMouseButton(e)) {
      if (this.anchorPointClicked != null) {
        this.showAnchorPointMenu(e);
      } else if (this.focusedRelation != null) {
        this.showRelationMenu(e);
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.origin = e.getPoint();
    this.mouseWheelPressed = SwingUtilities.isMiddleMouseButton(e);
    this.anchorPointClicked = this.getAnchorPointClicked(e);
    this.updateCursor();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);

    if (this.anchorPointClicked != null && this.focusedRelation != null) {
      this.deleteAnchorPointsIfNecessary();
    }

    if (this.mouseWheelPressed && SwingUtilities.isMiddleMouseButton(e)) {
      this.mouseWheelPressed = false;
    }

    this.anchorPointClicked = null;
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

    if (this.anchorPointClicked != null && this.focusedRelation != null) {
      this.dragAnchorPointSelected(e);
    } else if (this.focusedRelation != null) {
      this.dragAssociation(differenceX, differenceY);
    }

    this.origin = e.getPoint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    super.mouseMoved(e);

    this.checkForHoveredRelation(e);
    // Change le curseur lors du survol de l'association cliquée
    if (this.focusedRelation != null) {
      for (Line2D segment : this.focusedRelation.getSegments()) {
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

  public void selectAnchorPointsToMove(RelationAnchorPointShape anchorPoint) {
    this.anchorPointsToMove.clear();
    if (anchorPoint == this.focusedRelation.getFirstPoint()) {
      // Premier point
      this.anchorPointsToMove.add(this.focusedRelation.getAnchorPoints().get(anchorPoint.getIndex() + 1));
    } else if (anchorPoint == this.focusedRelation.getLastPoint()) {
      // Dernier point
      this.anchorPointsToMove.add(this.focusedRelation.getAnchorPoints().get(anchorPoint.getIndex() - 1));
    } else {
      // Tout autre point entre l'indice 1 et n-1
      this.anchorPointsToMove.add(this.focusedRelation.getAnchorPoints().get(anchorPoint.getIndex() - 1));
      this.anchorPointsToMove.add(this.focusedRelation.getAnchorPoints().get(anchorPoint.getIndex() + 1));
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

  private void createNoteShape(MouseEvent event) {
    Point mouseClick = event.getPoint();
    NoteShape shape = new NoteShape();

    shape.setLocation(GridUtils.alignToGrid(mouseClick.x, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(mouseClick.y, DiagrammerService.getDrawPanel().getGridSize()));

    MVCCDManager.instance().getCurrentDiagram().addShape(shape);
    DiagrammerService.getDrawPanel().addShape(shape);
    DiagrammerService.getDrawPanel().repaint();
  }

  private void createEntityShape(MouseEvent event) {
    Point mouseClick = event.getPoint();
    MCDEntityShape shape = new MCDEntityShape();

    shape.setLocation(GridUtils.alignToGrid(mouseClick.x, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(mouseClick.y, DiagrammerService.getDrawPanel().getGridSize()));

    MVCCDManager.instance().getCurrentDiagram().addShape(shape);
    DiagrammerService.getDrawPanel().addShape(shape);
    DiagrammerService.getDrawPanel().repaint();

  }

  private void executeButtonAction(MouseEvent event) {
    switch (PalettePanel.activeButton.getText()) {
      case Preferences.DIAGRAMMER_PALETTE_ENTITE_BUTTON_TEXT:
        this.createEntityShape(event);
        break;
      case Preferences.DIAGRAMMER_PALETTE_NOTE_BUTTON_TEXT:
        this.createNoteShape(event);
        break;
    }
  }

  private RelationAnchorPointShape getAnchorPointClicked(MouseEvent event) {
    for (RelationShape relation : DiagrammerService.getDrawPanel().getRelationShapes()) {
      for (RelationAnchorPointShape anchorPoint : relation.getAnchorPoints()) {
        if (anchorPoint.contains(event.getPoint())) {
          this.selectAnchorPointsToMove(anchorPoint);
          return anchorPoint;
        }
      }
    }
    return null;
  }

  private void checkForClickedRelation(MouseEvent event) {
    for (RelationShape relation : DiagrammerService.getDrawPanel().getRelationShapes()) {
      for (int i = 0; i < relation.getAnchorPoints().size() - 1; i++) {
        final Line2D segment = new Line2D.Double();
        segment.setLine(relation.getAnchorPoints().get(i).getX(), relation.getAnchorPoints().get(i).getY(), relation.getAnchorPoints().get(i + 1).getX(), relation.getAnchorPoints().get(i + 1).getY());
        if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, event.getPoint()) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
          this.focusedRelation = relation;
          relation.setFocused(true);
        }
      }
    }
  }

  private void dragAnchorPointSelected(MouseEvent e) {
    Point newPoint = new Point(GridUtils.alignToGrid(e.getX(), DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(e.getY(), DiagrammerService.getDrawPanel().getGridSize()));
    this.dragAnchorPoint(newPoint);
    DiagrammerService.getDrawPanel().repaint();
  }

  private void deleteAnchorPointsIfNecessary() {
    boolean updateNecessary = false;
    if (this.focusedRelation.getAnchorPoints().size() > 2) {
      final ListIterator<RelationAnchorPointShape> iterator = this.focusedRelation.getAnchorPoints().listIterator();
      RelationAnchorPointShape leftNeighbour = iterator.next();
      RelationAnchorPointShape pointToCheck = iterator.next();
      while (iterator.hasNext()) {
        RelationAnchorPointShape rightNeighbour = iterator.next();
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
      this.focusedRelation.reindexAllAnchorPoint();
    }
    DiagrammerService.getDrawPanel().repaint();
  }

  /***
   * Cette méthode gère le déplacement du premier ou dernier point d'ancrage d'une relation
   * @param newPoint Nouveau point aux coordonnées x et y
   */
  private void dragFirstAnchorPoint(Point newPoint) {

    boolean dragAllowed = GeometryUtils.pointIsAroundShape(newPoint, this.focusedRelation.getSource());

    if (dragAllowed) {
      this.anchorPointClicked.drag(newPoint.x, newPoint.y);
    }
  }

  private void dragLastAnchorPoint(Point newPoint) {
    boolean dragAllowed = false;

    if (this.focusedRelation.getDestination() instanceof RelationShape) {
      dragAllowed = GeometryUtils.pointIsOnRelation(newPoint, (RelationShape) this.focusedRelation.getDestination());
    } else if (this.focusedRelation.getDestination() instanceof SquaredShape) {
      dragAllowed = GeometryUtils.pointIsAroundShape(newPoint, (ClassShape) this.focusedRelation.getDestination());
    }
    if (dragAllowed) {
      this.anchorPointClicked.drag(newPoint.x, newPoint.y);
    }
  }

  /***
   * Cette méthode gère les déplacements des points d'ancrage de la relation cliquée.
   * @param newPoint Nouveau point aux coordonnées x et y
   */
  private void dragAnchorPoint(Point newPoint) {
    for (RelationAnchorPointShape anchorPointToMove : this.anchorPointsToMove) {

      Point newPosition = new Point(anchorPointToMove.x, anchorPointToMove.y);

      if (this.anchorPointClicked.x == anchorPointToMove.x) {
        newPosition.x = newPoint.x;
      }

      if (this.anchorPointClicked.y == anchorPointToMove.y) {
        newPosition.y = newPoint.y;
      }

      boolean dragAllowed = this.checkIfAnchorPointCanBeDragged(anchorPointToMove, newPosition);

      if (dragAllowed) {
        anchorPointToMove.drag(newPosition.x, newPosition.y);
      }
    }

    // Maintenant, on déplace le point d'ancrage cliqué
    this.dragClickedAnchorPoint(newPoint);

    List<RelationAnchorPointShape> linkAnchorPointsToMove = new LinkedList<>();
    for (RelationShape relationShape : DiagrammerService.getDrawPanel().getRelationShapes()) {
      if (relationShape != this.focusedRelation) {
        for (RelationAnchorPointShape anchorPoint : relationShape.getAnchorPoints()) {
          if (this.focusedRelation.contains(anchorPoint.x, anchorPoint.y)) {

          }
        }
      }
    }
  }

  private void dragClickedAnchorPoint(Point newPoint) {
    if (this.anchorPointClicked == this.focusedRelation.getFirstPoint()) {
      this.dragFirstAnchorPoint(newPoint);
    } else if (this.anchorPointClicked == this.focusedRelation.getLastPoint()) {
      this.dragLastAnchorPoint(newPoint);
    } else {
      this.anchorPointClicked.drag(newPoint.x, newPoint.y);
    }
  }

  private boolean checkIfAnchorPointCanBeDragged(RelationAnchorPointShape anchorPoint, Point newPosition) {
    boolean dragAllowed;

    if (anchorPoint == this.focusedRelation.getFirstPoint()) {
      // Premier point d'ancrage
      dragAllowed = GeometryUtils.pointIsAroundShape(newPosition, this.focusedRelation.getSource());
    } else if (anchorPoint == this.focusedRelation.getLastPoint()) {
      // Dernier point d'ancrage
      if (this.focusedRelation.getDestination() instanceof SquaredShape) {
        // La destination est une SquaredShape
        dragAllowed = GeometryUtils.pointIsAroundShape(newPosition, (ClassShape) this.focusedRelation.getDestination());
      } else {
        // La destination est une RelationShape
        dragAllowed = GeometryUtils.pointIsOnRelation(newPosition, (RelationShape) this.focusedRelation.getDestination());
      }
    } else {
      // Tout point qui n'est pas le premier ni le dernier peut être déplacé
      dragAllowed = true;
    }
    return dragAllowed;
  }

  private void dragAssociation(int differenceX, int differenceY) {
    if (this.focusedRelation.getAnchorPoints().size() == 2) {
      final Point firstPoint = this.focusedRelation.getFirstPoint();
      final Point lastPoint = this.focusedRelation.getLastPoint();

      // Crée un segment fictif
      final Line2D segment = new Line2D.Double();
      segment.setLine(firstPoint.x, firstPoint.y, lastPoint.x, lastPoint.y);

      final Point newFirstPoint = new Point(firstPoint.x + differenceX, firstPoint.y + differenceY);
      final Point newSecondPoint = new Point(lastPoint.x + differenceX, lastPoint.y + differenceY);

      // Si le segment est horizontal, on déplace l'association
      if (GeometryUtils.isHorizontal(segment)) {
        if (GeometryUtils.pointIsAroundShape(newFirstPoint, this.focusedRelation.getSource()) && GeometryUtils.pointIsAroundShape(newSecondPoint, (ClassShape) this.focusedRelation.getDestination())) {
          this.focusedRelation.setLocationDifference(0, differenceY);
        }
        // Si le segment est vertical, on déplace l'association
      } else if (GeometryUtils.isVertical(segment)) {
        if (GeometryUtils.pointIsAroundShape(newFirstPoint, this.focusedRelation.getSource()) && GeometryUtils.pointIsAroundShape(newSecondPoint, (ClassShape) this.focusedRelation.getDestination())) {
          this.focusedRelation.setLocationDifference(differenceX, 0);
        }
      }
      DiagrammerService.getDrawPanel().repaint();
    }
  }

  private void showAnchorPointMenu(MouseEvent event) {
    Point converted = SwingUtilities.convertPoint(this.focusedRelation, event.getPoint(), DiagrammerService.getDrawPanel());
    AnchorPointMenu menu = new AnchorPointMenu(this.anchorPointClicked, this.focusedRelation);
    menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
  }

  private void showRelationMenu(MouseEvent event) {
    Point converted = SwingUtilities.convertPoint(this.focusedRelation, event.getPoint(), DiagrammerService.getDrawPanel());
    RelationShapeMenu menu = new RelationShapeMenu(this.focusedRelation, converted.x, converted.y);
    menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
  }

  private void checkForHoveredRelation(MouseEvent e) {
    // Vérifie si une association est survolée
    for (RelationShape relationShape : DiagrammerService.getDrawPanel().getRelationShapes()) {
      boolean oneSegmentIsHovered = false;
      for (Line2D segment : relationShape.getSegments()) {
        if (GeometryUtils.getDistanceBetweenLineAndPoint(segment, e.getPoint()) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
          oneSegmentIsHovered = true;
        }
      }
      if (oneSegmentIsHovered) {
        this.focusedRelation = relationShape;
      }
      relationShape.setFocused(oneSegmentIsHovered);
      DiagrammerService.getDrawPanel().setCursor(new Cursor(oneSegmentIsHovered ? Cursor.MOVE_CURSOR : Cursor.DEFAULT_CURSOR));
    }
  }
}