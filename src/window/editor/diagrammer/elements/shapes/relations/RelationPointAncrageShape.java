package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;

public class RelationPointAncrageShape extends Point implements IShape {

  public int DIAMETER = 10;
  private int index;
  private boolean isSelected = false;

  public RelationPointAncrageShape(Point p, int index) {
    super(p);
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  public RelationPointAncrageShape(int x, int y) {
    super(x, y);
    this.setSize(this.DIAMETER, this.DIAMETER);

  }

  public RelationPointAncrageShape(int x, int y, int index) {
    super(x, y);
    this.index = index;
    this.setSize(this.DIAMETER, this.DIAMETER);
  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    this.translate(differenceX, differenceY);
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
  public boolean isSelected() {
    return this.isSelected;
  }

  @Override
  public void setSelected(boolean isSelected) {
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
}