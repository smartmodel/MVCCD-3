package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;

public class MCDEntityShapeDeleteAction extends DeleteActions implements Serializable {

  private static final long serialVersionUID = 1000;
  private MCDEntityShape shape;

  public MCDEntityShapeDeleteAction(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Supprimer graphiquement")) {
      super.deleteGraphically(shape);
    } else if (e.getActionCommand().equals("Supprimer objet")) {
      this.deleteObject();
    }
  }

  private void deleteObject() {
    super.deleteGraphically(shape);
    super.deleteObject(shape);
  }
}
