package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class MCDEntityShapeDeleteAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;
  private MCDEntityShape shape;

  public MCDEntityShapeDeleteAction(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.delete();
  }

  private void delete() {
    DiagrammerService.getDrawPanel().deleteShape(this.shape);
  }
}
