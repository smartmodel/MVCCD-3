package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class MCDEntityShapeDeleteActions extends CommonDeleteActions implements Serializable {

  private static final long serialVersionUID = 1000;
  private MCDEntityShape shape;

  public MCDEntityShapeDeleteActions(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Supprimer l'objet et sa représentation graphique")) {
      this.deleteObject();
    }
  }

  private void deleteObject() {
    super.deleteGraphically(shape);
    shape.getEntity().delete();
  }
}
