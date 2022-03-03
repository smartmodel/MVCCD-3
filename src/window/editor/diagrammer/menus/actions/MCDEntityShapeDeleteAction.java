package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;

public class MCDEntityShapeDeleteAction extends AbstractAction implements Serializable {

  private MCDEntityShape shape;
  private static final long serialVersionUID = 1000;

  public MCDEntityShapeDeleteAction(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    delete();
  }

  private void delete() {
    DiagrammerService.getDrawPanel().deleteShape(shape);
  }
}
