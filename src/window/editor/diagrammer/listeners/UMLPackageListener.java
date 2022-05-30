package window.editor.diagrammer.listeners;

import window.editor.diagrammer.elements.shapes.UMLPackage;
import window.editor.diagrammer.menus.UMLPackageMenu;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class UMLPackageListener extends MouseAdapter implements Serializable {

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            this.showMenu(e);
        }
    }

    private void showMenu(MouseEvent event) {
        final UMLPackage shape = (UMLPackage) event.getSource();
        final Point converted = SwingUtilities.convertPoint(shape, event.getPoint(), DiagrammerService.getDrawPanel());
        final UMLPackageMenu menu = new UMLPackageMenu(shape);

        menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
    }
}
