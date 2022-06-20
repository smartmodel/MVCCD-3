package window.editor.diagrammer.menus;

import java.io.Serializable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import window.editor.diagrammer.elements.shapes.UMLPackage;
import window.editor.diagrammer.menus.actions.UMLPackageDeleteAction;

public class UMLPackageMenu extends JPopupMenu implements Serializable {
  public UMLPackageMenu(UMLPackage shape) {
    super();
    JMenuItem delete = new JMenuItem(
        new UMLPackageDeleteAction("Supprimer graphiquement", null, shape));

    this.add(delete);
  }
}
