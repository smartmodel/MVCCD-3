package window.editor.diagrammer.elements.interfaces;

import project.ProjectElement;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public interface IShape {

  default void setLocationDifference(int differenceX, int differenceY) {
    this.setLocation(this.getBounds().x + differenceX, this.getBounds().y + differenceY);
  }
  void repaint();
  void setLocation(Point location);
  void setSize(Dimension dimension);
  Rectangle getBounds();
  void zoom(int fromFactor, int toFactor);
  void drag(int differenceX, int differenceY);
  default void setLocation(int x, int y) {
    this.setLocation(new Point(x, y));
  }
  boolean isSelected();
  void setSelected(boolean isSelected);
  default void setSize(int width, int height) {
    this.setSize(new Dimension(width, height));
  }
}
