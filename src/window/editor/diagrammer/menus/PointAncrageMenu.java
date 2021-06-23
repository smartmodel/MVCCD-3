package window.editor.diagrammer.menus;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.menus.actions.PointAncrageDeleteAction;

public class PointAncrageMenu extends JPopupMenu {

  RelationPointAncrageShape shape;
  RelationShape relation;

  public PointAncrageMenu(RelationPointAncrageShape shape, RelationShape relation) {
    this.shape = shape;
    this.relation = relation;
    JMenuItem delete = new JMenuItem(
        new PointAncrageDeleteAction("Supprimer le point d'ancrage", null, shape, relation));
    this.add(delete);
  }
}
