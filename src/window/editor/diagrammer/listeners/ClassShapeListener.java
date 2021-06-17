package window.editor.diagrammer.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.elements.ClassShape;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.GridUtils;

public class ClassShapeListener extends MouseAdapter {

  private Point origin;

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    this.origin = e.getPoint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    super.mouseDragged(e);
    ClassShape shape = (ClassShape) e.getSource();

    int differenceX = GridUtils.alignToGrid(e.getPoint().x - origin.x, DiagrammerService.getDrawPanel().getGridSize());
    int differenceY = GridUtils.alignToGrid(e.getPoint().y - origin.y, DiagrammerService.getDrawPanel().getGridSize());

    shape.updateRelations(differenceX, differenceY);
  }

}
