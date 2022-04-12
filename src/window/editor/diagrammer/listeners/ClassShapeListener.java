package window.editor.diagrammer.listeners;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;

public class ClassShapeListener extends MouseAdapter implements Serializable {

  private static final long serialVersionUID = 1000;
  private final ClassShape shape;

  public ClassShapeListener(ClassShape shape) {
    this.shape = shape;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    super.mouseExited(e);
    ClassShape source = (ClassShape) e.getSource();
    if (!source.isFocused()){
      source.setFocused(false);
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    super.mouseMoved(e);

  }

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    ClassShape shape = (ClassShape) e.getSource();
    int cursor = shape.getCursor().getType();

    if (cursor != Cursor.MOVE_CURSOR) {
      shape.updateRelations();
    }
  }


}
