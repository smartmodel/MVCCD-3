package window.editor.diagrammer.listeners;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import window.editor.diagrammer.elements.shapes.relations.LabelShape;
import window.editor.diagrammer.services.DiagrammerService;

public class LabelShapeListener extends MouseAdapter {

  Point origin;

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    this.origin = e.getPoint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    final LabelShape shape = (LabelShape) e.getSource();
    shape.setFirstDisplay(false);

    final int differenceX = e.getPoint().x - this.origin.x;
    final int differenceY = e.getPoint().y - this.origin.y;

    shape.setDistanceInXFromPointAncrage(shape.getDistanceInXFromPointAncrage() + differenceX);
    shape.setDistanceInYFromPointAncrage(shape.getDistanceInYFromPointAncrage() + differenceY);

    shape.repaint();
  }

}
