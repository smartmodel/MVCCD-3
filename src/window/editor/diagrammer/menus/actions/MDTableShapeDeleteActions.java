package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import main.MVCCDManager;
import window.editor.diagrammer.elements.shapes.MDTableShape;

public class MDTableShapeDeleteActions extends CommonDeleteActions implements Serializable {

  private MDTableShape shape;

  public MDTableShapeDeleteActions(String name, Icon icon, MDTableShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Supprimer objet")) {
      this.deleteObject();
    } else if (e.getActionCommand().equals("Supprimer objet et clones")) {
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
