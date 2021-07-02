package window.editor.diagrammer.menus;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.menus.actions.MCDEntityShapeDeleteAction;
import window.editor.diagrammer.menus.actions.MCDEntityShapeEditAction;

public class EntityShapeMenu extends JPopupMenu {

  public EntityShapeMenu(MCDEntityShape shape) {
    super();

    JMenuItem edit = new JMenuItem(new MCDEntityShapeEditAction("Ouvrir l'assistant de modélisation", null, shape));
    this.add(edit);

    JMenuItem delete = new JMenuItem(new MCDEntityShapeDeleteAction("Supprimer l'entité", null, shape));
    this.add(delete);

  }
}
