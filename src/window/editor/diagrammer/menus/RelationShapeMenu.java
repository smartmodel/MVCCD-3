package window.editor.diagrammer.menus;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;
import window.editor.diagrammer.menus.actions.MCDAssociationEditAction;

public class RelationShapeMenu extends JPopupMenu {

  public RelationShapeMenu(MCDAssociationShape shape, int x, int y) {
    super();

    JMenuItem edit = new JMenuItem(new MCDAssociationEditAction("Ouvrir l'assistant de modélisation", null, shape));
    this.add(edit);

  }
}
