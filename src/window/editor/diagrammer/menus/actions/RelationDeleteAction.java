package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

public class RelationDeleteAction extends AbstractAction implements Serializable {

  private final RelationShape shape;
  private static final long serialVersionUID = 1000;

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
