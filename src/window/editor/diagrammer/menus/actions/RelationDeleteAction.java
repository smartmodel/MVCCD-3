package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RelationDeleteAction extends AbstractAction {

  private final RelationShape shape;

  public RelationDeleteAction(String name, Icon icon, RelationShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.delete();
  }

  private void delete() {
    this.shape.deleteLabels();
    DiagrammerService.getDrawPanel().deleteShape(this.shape);
  }
}
