package window.editor.diagrammer.menus;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.menus.actions.DeleteActions;
import window.editor.diagrammer.menus.actions.ModifyColorActions;

public abstract class CommonMenu extends JPopupMenu {

  private final JMenuItem deleteGraphically;

  private final JMenuItem color;
  private final JMenuItem colorAll;


  public CommonMenu(SquaredShape shape) {
    this.color = new JMenuItem(
        new ModifyColorActions("Modifier couleur de fond de cet objet", null, shape));
    this.colorAll = new JMenuItem(
        new ModifyColorActions("Modifier couleur de fond de tous les objets de ce type", null,
            shape));
    this.deleteGraphically = new JMenuItem(
        new DeleteActions("Supprimer graphiquement", null, shape));

    this.add(color);
    this.add(colorAll);
    this.add(deleteGraphically);
  }
}
