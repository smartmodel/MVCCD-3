package window.editor.diagrammer.menus;

import java.io.Serializable;
import javax.swing.JMenuItem;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.menus.actions.MCDEntityShapeDeleteActions;
import window.editor.diagrammer.menus.actions.MCDEntityShapeEditAction;

public class EntityShapeMenu extends CommonMenu implements Serializable {

  private static final long serialVersionUID = 1000;

  public EntityShapeMenu(MCDEntityShape shape) {
    super(shape);
    JMenuItem deleteObject = new JMenuItem(
        new MCDEntityShapeDeleteActions("Supprimer objet", null, shape));

    JMenuItem edit = new JMenuItem(
        new MCDEntityShapeEditAction("Ouvrir l'assistant de modélisation", null, shape));

    this.add(deleteObject);
    this.add(edit);
  }
}
