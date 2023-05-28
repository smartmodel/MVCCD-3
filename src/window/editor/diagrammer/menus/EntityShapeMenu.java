package window.editor.diagrammer.menus;

import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.menus.actions.MCDEntityShapeDeleteActions;
import window.editor.diagrammer.menus.actions.MCDEntityShapeEditAction;

import javax.swing.*;
import java.io.Serializable;

public class EntityShapeMenu extends CommonMenu implements Serializable {

  private static final long serialVersionUID = 1000;

  public EntityShapeMenu(MCDEntityShape shape) {
    super(shape);
    JMenuItem deleteObject = new JMenuItem(
            new MCDEntityShapeDeleteActions("Supprimer l'objet et sa représentation graphique", null, shape));

    JMenuItem edit = new JMenuItem(
        new MCDEntityShapeEditAction("Ouvrir l'assistant de modélisation", null, shape));

    this.add(deleteObject);
    this.add(edit);
  }
}
