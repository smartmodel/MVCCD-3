package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;

public class MCDEntityShapeDeleteActions extends CommonDeleteActions implements Serializable {

  private static final long serialVersionUID = 1000;
  private MCDEntityShape shape;

  public MCDEntityShapeDeleteActions(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Supprimer objet")) {
      this.deleteObject();
    }
  }

  private void deleteObject() {
    super.deleteGraphically(shape);
    shape.getEntity().delete();
  }
}
