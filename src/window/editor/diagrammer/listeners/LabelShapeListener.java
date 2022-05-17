package window.editor.diagrammer.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;

public class LabelShapeListener extends MouseAdapter implements Serializable {

  private static final long serialVersionUID = 1000;
  private Point origin;

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    this.origin = e.getPoint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    final LabelShape shape = (LabelShape) e.getSource();

    final int differenceX = e.getPoint().x - this.origin.x;
    final int differenceY = e.getPoint().y - this.origin.y;

    shape.setDistanceInXFromPointAncrage(shape.getDistanceInXFromPointAncrage() + differenceX);
    shape.setDistanceInYFromPointAncrage(shape.getDistanceInYFromPointAncrage() + differenceY);

    shape.repaint();
  }

}