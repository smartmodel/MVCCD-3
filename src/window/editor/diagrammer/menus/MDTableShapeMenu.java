package window.editor.diagrammer.menus;

import window.editor.diagrammer.elements.shapes.MDTableShape;
import window.editor.diagrammer.menus.actions.MDTableShapeDeleteAction;

import javax.swing.*;
import java.io.Serializable;

public class MDTableShapeMenu extends JPopupMenu implements Serializable {

    public MDTableShapeMenu(MDTableShape shape) {
        super();

        JMenuItem delete = new JMenuItem(new MDTableShapeDeleteAction("Supprimer la Table", null, shape));
        this.add(delete);

    }
}