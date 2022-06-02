package window.editor.diagrammer.elements.interfaces;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface IShape {

  void setLocationDifference(int differenceX, int differenceY);
  void setLocation(Point location);
  void setSize(Dimension dimension);
  int getId();
  Point getCenter();
  boolean contains(Point point);
  Rectangle getBounds();
  void zoom(int fromFactor, int toFactor);
  void drag(int differenceX, int differenceY);
  default void setLocation(int x, int y) {
    this.setLocation(new Point(x, y));
  }
  boolean isFocused();
  void setFocused(boolean isSelected);
  default void setSize(int width, int height) {
    this.setSize(new Dimension(width, height));
  }
}