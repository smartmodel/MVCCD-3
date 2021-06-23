package window.editor.diagrammer.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import window.editor.diagrammer.elements.shapes.LabelShape;

public class LabelShapeListener extends MouseAdapter {

  Point origin;

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    this.origin = e.getPoint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    LabelShape shape = (LabelShape) e.getSource();
    int differenceX = e.getPoint().x - origin.x;
    int differenceY = e.getPoint().y - origin.y;
    shape.setDistanceInXFromPointAncrage(shape.getDistanceInXFromPointAncrage() + differenceX);
    shape.setDistanceInYFromPointAncrage(shape.getDistanceInYFromPointAncrage() + differenceY);
    shape.setFirstDisplay(false);
    shape.repaint();

  }

}
