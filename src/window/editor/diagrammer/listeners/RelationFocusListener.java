package window.editor.diagrammer.listeners;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

public class RelationFocusListener extends FocusAdapter {

  @Override
  public void focusGained(FocusEvent e) {
    super.focusGained(e);
    RelationShape relation = (RelationShape) e.getSource();
  }

  @Override
  public void focusLost(FocusEvent e) {
    super.focusLost(e);
    RelationShape relation = (RelationShape) e.getSource();
  }
}
