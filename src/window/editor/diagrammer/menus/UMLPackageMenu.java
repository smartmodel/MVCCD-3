package window.editor.diagrammer.menus;

import window.editor.diagrammer.elements.shapes.UMLPackage;
import window.editor.diagrammer.menus.actions.UMLPackageDeleteAction;

import javax.swing.*;
import java.io.Serializable;

public class UMLPackageMenu extends JPopupMenu implements Serializable {
    public UMLPackageMenu(UMLPackage shape) {
        super();

        JMenuItem delete = new JMenuItem(new UMLPackageDeleteAction("Supprimer le Package TAPIs", null, shape));
        this.add(delete);

    }
}
