package window.editor.diagrammer.menus.actions;

import main.MVCCDManager;
import window.editor.diagrammer.elements.shapes.MDTableShape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class MDTableShapeDeleteActions extends CommonDeleteActions implements Serializable {

  private MDTableShape shape;

  public MDTableShapeDeleteActions(String name, Icon icon, MDTableShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Supprimer l'objet et sa représentation graphique")) {
      this.deleteObject();
    } else if (e.getActionCommand().equals("Supprimer l'objet et ses représentations graphiques")) {
      int result = JOptionPane.showConfirmDialog(MVCCDManager.instance().getMvccdWindow(),
              "Souhaitez-vous réellement supprimer l'objet ainsi que sa représentation graphique dans tous les diagrammes où celui-ci est présent ?");

      if (result == 0) {
        super.deleteObjectAndClones(shape);
      }
    }
  }

  private void deleteObject() {
    super.deleteGraphically(shape);
    super.deleteObject(shape);
  }

}
