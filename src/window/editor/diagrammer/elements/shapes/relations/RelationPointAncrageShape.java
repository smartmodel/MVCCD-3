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

public class RelationPointAncrageShape extends Point implements IShape, Serializable {

  private static final long serialVersionUID = 1000;
  public int DIAMETER = 10;
  private int index;
  private boolean isSelected = false;
  private int id;

  public RelationPointAncrageShape(Point p, int index) {
    super(p);

    this.generateId();

    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationPointAncrageShape(int x, int y) {
    super(x, y);
    this.generateId();
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationPointAncrageShape(int x, int y, int index) {
    super(x, y);
    this.generateId();
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationPointAncrageShape(int id, Point p, int index) {
    super(p);
    this.id = id;
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationPointAncrageShape(int id, Point p) {
    super(p);
    this.id = id;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationPointAncrageShape(int id, int x, int y, int index) {
    super(x, y);
    this.id = id;
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    this.translate(differenceX, differenceY);
  }

  @Override
  public void repaint() {

  }

  private void generateId() {
    // Génère un id utile à la persistance
    if (MVCCDManager.instance().getProject() != null) {
      this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
    }
  }

  @Override
  public void setSize(Dimension dimension) {
    this.DIAMETER = dimension.height;
  }

  @Override
  public Rectangle getBounds() {
    return new Rectangle(this.x - this.DIAMETER / 2, this.y - this.DIAMETER / 2, this.DIAMETER, this.DIAMETER);
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {
    int newXPosition = this.getBounds().x * toFactor / fromFactor;
    int newYPosition = this.getBounds().y * toFactor / fromFactor;
    int newWidth = this.getBounds().width * toFactor / fromFactor;
    int newHeight = this.getBounds().height * toFactor / fromFactor;
    // Set la nouvelle position, la nouvelle taille de l'élément et met à jour la nouvelle taille minimale de l'élément
    this.setSize(GridUtils.alignToGrid(newWidth, toFactor), GridUtils.alignToGrid(newHeight, toFactor));
    this.setLocation(GridUtils.alignToGrid(newXPosition, toFactor), GridUtils.alignToGrid(newYPosition, toFactor));
  }

  @Override
  public void drag(int x, int y) {
    this.move(x, y);
    DiagrammerService.getDrawPanel().repaint();
  }

  @Override
  public boolean isFocused() {
    return this.isSelected;
  }

  @Override
  public void setFocused(boolean isSelected) {
    this.isSelected = isSelected;
  }

  public boolean contains(Point point) {
    Rectangle bounds = this.getBounds();
    return bounds.contains(point);
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

  public int getId() {
    return id;
  }

  @Override
  public Point getCenter() {
    return new Point(x, y);
  }
}
