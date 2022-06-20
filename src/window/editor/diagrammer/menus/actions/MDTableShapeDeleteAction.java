package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.MDTableShape;

public class MDTableShapeDeleteAction extends DeleteActions implements Serializable {

  private MDTableShape shape;

  public MDTableShapeDeleteAction(String name, Icon icon, MDTableShape shape) {
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
