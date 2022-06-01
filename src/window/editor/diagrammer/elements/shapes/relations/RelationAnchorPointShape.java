package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import main.MVCCDManager;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;

public class RelationAnchorPointShape extends Point implements IShape, Serializable {

  private static final long serialVersionUID = 1000;
  public int DIAMETER = 10;
  private int index;
  private boolean isSelected = false;
  private int id;

  public RelationAnchorPointShape(Point p, int index) {
    super(p);

    this.generateId();

    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationAnchorPointShape(int x, int y) {
    super(x, y);
    this.generateId();
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationAnchorPointShape(int x, int y, int index) {
    super(x, y);
    this.generateId();
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationAnchorPointShape(int id, Point p, int index) {
    super(p);
    this.id = id;
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationAnchorPointShape(int id, Point p) {
    super(p);
    this.id = id;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationAnchorPointShape(int id, int x, int y, int index) {
    super(x, y);
    this.id = id;
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    System.out.println("d" + differenceX);
    this.translate(differenceX, differenceY);
  }

  @Override
  public void setSize(Dimension dimension) {
    this.DIAMETER = dimension.height;
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
    return new Rectangle(this.x - this.DIAMETER / 2, this.y - this.DIAMETER / 2, this.DIAMETER, this.DIAMETER);
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
    int newX = GridUtils.alignToGrid(this.getX() + differenceX, DiagrammerService.getDrawPanel().getGridSize());
    int newY = GridUtils.alignToGrid(this.getY() + differenceY, DiagrammerService.getDrawPanel().getGridSize());
    this.move(newX, newY);
    System.out.println("Point " + this.index + " dragged !");
  }

  @Override
  public boolean isFocused() {
    return this.isSelected;
  }

  @Override
  public void setFocused(boolean isSelected) {
    this.isSelected = isSelected;
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