package window.editor.diagrammer.elements;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class EntityShapeMenu extends JPopupMenu {

  public EntityShapeMenu(MCDEntityShape shape, int x, int y) {
    super();

    JMenuItem edit = new JMenuItem(new MCDEntityShapeEditAction("Ouvrir l'assistant de modélisation", null, shape));
    this.add(edit);

    JMenuItem delete = new JMenuItem(new MCDEntityShapeDeleteAction("Supprimer l'entité", null, shape));
    this.add(delete);

  }
}
