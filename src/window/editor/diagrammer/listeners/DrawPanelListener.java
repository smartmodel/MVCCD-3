package window.editor.diagrammer.listeners;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
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
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.RelationCreator;
import window.editor.diagrammer.utils.ShapeUtils;

public class DrawPanelListener extends MouseAdapter implements Serializable, KeyListener {

  private static final long serialVersionUID = 1000;
  private final Cursor CURSOR_ENTITY_ICON;
  private Point origin;
  private int tempNewX = 0;
  private int tempNewAlignedX = 0;
  private int tempNewY = 0;
  private int tempNewAlignedY = 0;
  private RelationAnchorPointShape anchorPointClicked = null;
  private RelationShape focusedRelation = null;
  private List<RelationAnchorPointShape> anchorPointsToMove = new LinkedList<>();
  private boolean spaceBarPressed = false;
  private boolean mouseWheelPressed = false;

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
    this.updateCursor(e);

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
    this.updateCursor(e);
    this.resetTemporaryLocations();
    //DiagrammerService.getDrawPanel().endScroll();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    super.mouseEntered(e);
    DiagrammerService.getDrawPanel().grabFocus();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    super.mouseWheelMoved(e);
    if (this.isZoomAllowed(e)) {
      int actualZoom = DiagrammerService.getDrawPanel().getGridSize();
      DiagrammerService.getDrawPanel().zoom(actualZoom - e.getWheelRotation());
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    super.mouseDragged(e);

    // Si un point d'ancrage est sélectionné
    if (this.anchorPointClicked != null && this.focusedRelation != null) {
      this.dragAnchorPointSelected(e);
    } else {
      if (this.spaceBarPressed) {
        this.dragDiagram(e);
        DiagrammerService.getDrawPanel().setManualScrolling(false);
      }
    }

  }

  @Override
  public void mouseMoved(MouseEvent e) {
    super.mouseMoved(e);

    this.checkForHoveredRelation(e);
    // Change le curseur lors du survol de l'association cliquée
    if (this.focusedRelation != null) {
      for (Line2D segment : this.focusedRelation.getSegments()) {
        if (ShapeUtils.getDistanceBetweenLineAndPoint(segment, e.getPoint()) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
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

  private void dragDiagram(MouseEvent e) {
    DiagrammerService.getDrawPanel().setManualScrolling(true);

    int differenceX = e.getPoint().x - this.origin.x;
    int differenceY = e.getPoint().y - this.origin.y;

    this.origin = e.getPoint();
    this.tempNewX += differenceX;
    this.tempNewY += differenceY;

    boolean xNeedsRealignment = this.tempNewX == GridUtils.alignToGrid(this.tempNewX + differenceX, DiagrammerService.getDrawPanel().getGridSize());
    boolean yNeedsRealignement = this.tempNewY == GridUtils.alignToGrid(this.tempNewY + differenceY, DiagrammerService.getDrawPanel().getGridSize());

    if (xNeedsRealignment && yNeedsRealignement) {
      DiagrammerService.getDrawPanel().drag(this.tempNewX, this.tempNewY);
      this.tempNewX = 0;
      this.tempNewY = 0;
    }

    if (xNeedsRealignment && !yNeedsRealignement) {
      DiagrammerService.getDrawPanel().drag(this.tempNewX, 0);
      this.tempNewX = 0;
    }

    if (!xNeedsRealignment && yNeedsRealignement) {
      DiagrammerService.getDrawPanel().drag(0, this.tempNewY);
      this.tempNewY = 0;
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

  private void updateCursor(MouseEvent event) {
    if (this.isScrollAllowed(event)) {
      DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
    } else {
      DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }

  private boolean isZoomAllowed(MouseEvent event) {
    return event.isControlDown();
  }

  private boolean isScrollAllowed(MouseEvent event) {
    return event.isShiftDown();
  }

  private void createNoteShape(MouseEvent event) {
    Point mouseClick = event.getPoint();
    NoteShape shape = new NoteShape();

    shape.setLocation(GridUtils.alignToGrid(mouseClick.x, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(mouseClick.y, DiagrammerService.getDrawPanel().getGridSize()));

    MVCCDManager.instance().getCurrentDiagram().addShape(shape);
    DiagrammerService.getDrawPanel().addShape(shape);
  }

  private void createEntityShape(MouseEvent event) {
    Point mouseClick = event.getPoint();
    MCDEntityShape shape = new MCDEntityShape();

    shape.setLocation(GridUtils.alignToGrid(mouseClick.x, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(mouseClick.y, DiagrammerService.getDrawPanel().getGridSize()));

    MVCCDManager.instance().getCurrentDiagram().addShape(shape);
    DiagrammerService.getDrawPanel().addShape(shape);

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
        if (ShapeUtils.getDistanceBetweenLineAndPoint(segment, event.getPoint()) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
          this.focusedRelation = relation;
          relation.setFocused(true);
        }
      }
    }
  }

  private void dragAnchorPointSelected(MouseEvent e) {

    Point newPoint = new Point(GridUtils.alignToGrid(e.getX(), DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(e.getY(), DiagrammerService.getDrawPanel().getGridSize()));
    boolean dragAllowed = this.checkIfAnchorPointCanBeDragged(this.anchorPointClicked, newPoint);

    if (dragAllowed) {
      this.anchorPointClicked.move(newPoint.x, newPoint.y);
    }

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
        if (ShapeUtils.getDistanceBetweenLineAndPoint(leftNeighbour, rightNeighbour, pointToCheck) < Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
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
   */
  private void dragFirstAnchorPoint(int differenceX, int differenceY) {

    Point newPoint = new Point(GridUtils.alignToGrid(this.anchorPointClicked.x + differenceX, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(this.anchorPointClicked.y + differenceY, DiagrammerService.getDrawPanel().getGridSize()));
    boolean dragAllowed = ShapeUtils.pointIsAroundShape(newPoint, this.focusedRelation.getSource());

    if (dragAllowed) {
      this.anchorPointClicked.drag(newPoint.x, newPoint.y);
    }
  }

  private void dragLastAnchorPoint(int differenceX, int differenceY) {
    boolean dragAllowed = false;

    Point newPoint = new Point(GridUtils.alignToGrid(this.anchorPointClicked.x + differenceX, DiagrammerService.getDrawPanel().getGridSize()), GridUtils.alignToGrid(this.anchorPointClicked.y + differenceY, DiagrammerService.getDrawPanel().getGridSize()));

    if (this.focusedRelation.getDestination() instanceof RelationShape) {
      dragAllowed = ShapeUtils.pointIsOnRelation(newPoint, (RelationShape) this.focusedRelation.getDestination());
    } else if (this.focusedRelation.getDestination() instanceof SquaredShape) {
      dragAllowed = ShapeUtils.pointIsAroundShape(newPoint, (ClassShape) this.focusedRelation.getDestination());
    }
    if (dragAllowed) {
      this.anchorPointClicked.drag(differenceX, differenceY);
    }
  }

  private boolean checkIfAnchorPointCanBeDragged(RelationAnchorPointShape anchorPoint, Point newPosition) {
    boolean dragAllowed;

    if (anchorPoint == this.focusedRelation.getFirstPoint()) {
      // Premier point d'ancrage
      dragAllowed = ShapeUtils.pointIsAroundShape(newPosition, this.focusedRelation.getSource());
    } else if (anchorPoint == this.focusedRelation.getLastPoint()) {
      // Dernier point d'ancrage
      if (this.focusedRelation.getDestination() instanceof SquaredShape) {
        // La destination est une SquaredShape
        dragAllowed = ShapeUtils.pointIsAroundShape(newPosition, (ClassShape) this.focusedRelation.getDestination());
      } else {
        // La destination est une RelationShape
        dragAllowed = ShapeUtils.pointIsOnRelation(newPosition, (RelationShape) this.focusedRelation.getDestination());
      }
    } else {
      // Tout point qui n'est pas le premier ni le dernier peut être déplacé
      dragAllowed = true;
    }
    return dragAllowed;
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

  private void resetTemporaryLocations() {
    this.tempNewX = 0;
    this.tempNewAlignedX = 0;
  }

  private void checkForHoveredRelation(MouseEvent e) {
    // Vérifie si une association est survolée
    for (RelationShape relationShape : DiagrammerService.getDrawPanel().getRelationShapes()) {
      boolean oneSegmentIsHovered = false;
      for (Line2D segment : relationShape.getSegments()) {
        if (ShapeUtils.getDistanceBetweenLineAndPoint(segment, e.getPoint()) <= Preferences.DIAGRAMMER_RELATION_CLICK_AREA) {
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

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyChar() == ' ') {
      this.spaceBarPressed = true;
      DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyChar() == ' ') {
      this.spaceBarPressed = false;
      DiagrammerService.getDrawPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }
}