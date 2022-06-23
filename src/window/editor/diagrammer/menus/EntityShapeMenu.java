package window.editor.diagrammer.menus;

import java.io.Serializable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.menus.actions.MCDEntityShapeDeleteAction;
import window.editor.diagrammer.menus.actions.MCDEntityShapeEditAction;

public class EntityShapeMenu extends JPopupMenu implements Serializable {

  private static final long serialVersionUID = 1000;

  MCDEntityShape shape;

  public EntityShapeMenu(MCDEntityShape shape) {
    super();

    JMenuItem edit = new JMenuItem(
        new MCDEntityShapeEditAction("Ouvrir l'assistant de modélisation", null, shape));
    JMenuItem delete = new JMenuItem(
        new MCDEntityShapeDeleteAction("Supprimer graphiquement", null, shape));
    JMenuItem deleteObject = new JMenuItem(
        new MCDEntityShapeDeleteAction("Supprimer objet", null, shape));

    this.add(edit);
    this.add(delete);
    this.add(deleteObject);
  }
}
