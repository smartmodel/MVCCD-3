package window.editor.diagrammer.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;
import window.editor.diagrammer.interfaces.IShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;

public class RelationPointAncrageShape extends Point implements IShape {

  public final int DIAMETER = 10;

  public RelationPointAncrageShape(Point p) {
    super(p);
  }

  public RelationPointAncrageShape(int x, int y) {
    super(x, y);
  }

  @Override
  public void setLocationDifference(int differenceX, int differenceY) {
    this.setLocation(this.getBounds().x + differenceX, this.getBounds().y + differenceY);
  }

  @Override
  public void setSize(Dimension dimension) {
    this.setSize(dimension.width, dimension.height);
  }

  public Rectangle getBounds() {
    return new Rectangle(x - DIAMETER / 2, y - DIAMETER / 2, DIAMETER, DIAMETER);
  }

  @Override
  public void zoom(int fromFactor, int toFactor) {

  }

  @Override
  public void drag(int x, int y) {
    this.move(x, y);
    //this.translate(x, y);
    DiagrammerService.drawPanel.repaint();
  }

  @Override
  public void setSize(int width, int height) {
    IShape.super.setSize(width, height);
  }

  public boolean contains(Point point){
    Rectangle bounds = this.getBounds();
    return bounds.contains(point);
  }

}
