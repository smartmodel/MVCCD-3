package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import main.MVCCDManager;
import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.ShapeUtils;

public class RelationAnchorPointShape extends Point implements IShape, Serializable {

  private static final long serialVersionUID = 1000;

  private int index;
  private boolean isSelected = false;
  private int id;

  public RelationAnchorPointShape(Point p, int index) {
    super(p);

    this.generateId();

    this.index = index;
    this.setSize(Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE);
  }

  public RelationAnchorPointShape(int x, int y) {
    super(x, y);
    this.generateId();
    this.setSize(Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE);
  }

  public RelationAnchorPointShape(int x, int y, int index) {
    super(x, y);
    this.generateId();
    this.index = index;
    this.setSize(Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE);
  }

  public RelationAnchorPointShape(int id, Point p, int index) {
    super(p);
    this.id = id;
    this.index = index;
    this.setSize(Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE);
  }

  public RelationAnchorPointShape(int id, Point p) {
    super(p);
    this.id = id;
    this.setSize(Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE);
  }

  public RelationAnchorPointShape(int id, int x, int y, int index) {
    super(x, y);
    this.id = id;
    this.index = index;
    this.setSize(Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE);
  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    this.translate(differenceX, differenceY);
  }

  @Override
  public void setSize(Dimension dimension) {

  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public Point getCenter() {
    return new Point(this.x, this.y);
  }

  @Override
  public boolean contains(Point point) {
    Rectangle bounds = this.getBounds();
    return bounds.contains(point);
  }

  @Override
  public Rectangle getBounds() {
    return new Rectangle(this.x - Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE / 2, this.y - Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE / 2, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE, Preferences.DIAGRAMMER_DEFAULT_ANCHOR_POINT_SIZE);
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {
    int newX = this.x * toFactor / fromFactor;
    int newY = this.y * toFactor / fromFactor;

    this.x = GridUtils.alignToGrid(newX, DiagrammerService.getDrawPanel().getGridSize());
    this.y = GridUtils.alignToGrid(newY, DiagrammerService.getDrawPanel().getGridSize());

  }

  @Override
  public void drag(int differenceX, int differenceY) {
    int newX = GridUtils.alignToGrid(differenceX, DiagrammerService.getDrawPanel().getGridSize());
    int newY = GridUtils.alignToGrid(differenceY, DiagrammerService.getDrawPanel().getGridSize());
    this.move(newX, newY);
  }

  @Override
  public boolean isFocused() {
    return this.isSelected;
  }

  @Override
  public void setFocused(boolean isSelected) {
    this.isSelected = isSelected;
  }

  @Override
  public MDElement getRelatedRepositoryElement() {
    return null;
  }

  public void zoom(int fromFactor, int toFactor, RelationShape relation) {
    int newX = this.x * toFactor / fromFactor;
    int newY = this.y * toFactor / fromFactor;

    int x = GridUtils.alignToGrid(newX, DiagrammerService.getDrawPanel().getGridSize());
    int y = GridUtils.alignToGrid(newY, DiagrammerService.getDrawPanel().getGridSize());

    boolean zoomAllowed = true;

    if (this == relation.getFirstPoint()) {
      if (!ShapeUtils.pointIsAroundShape(new Point(x, y), relation.getSource())) {
        zoomAllowed = false;
      }
    } else if (this == relation.getDestination()) {
      if (!ShapeUtils.pointIsAroundShape(new Point(x, y), (SquaredShape) relation.getDestination())) {
        zoomAllowed = false;
      }
    }

    this.x = x;
    this.y = y;
  }

  private void generateId() {
    // Génère un id utile à la persistance
    if (MVCCDManager.instance().getProject() != null) {
      this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
    }
  }

  public int getIndex() {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_RELATION_ANCHOR_POINT_XML_TAG;
  }

  @Override
  public String toString() {
    return "RelationPointAncrageShape{" + "index=" + this.index + ", x=" + this.x + ", y=" + this.y + '}';
  }
}