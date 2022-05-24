package window.editor.diagrammer.listeners;

import window.editor.diagrammer.elements.shapes.classes.MDTableShape;
import window.editor.diagrammer.menus.MDTableShapeMenu;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class MDTableShapeListener  extends MouseAdapter implements Serializable {

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            this.showMenu(e);
        }
    }

    private void showMenu(MouseEvent event) {
        final MDTableShape shape = (MDTableShape) event.getSource();
        final Point converted = SwingUtilities.convertPoint(shape, event.getPoint(), DiagrammerService.getDrawPanel());
        final MDTableShapeMenu menu = new MDTableShapeMenu(shape);

        menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
    }
}
