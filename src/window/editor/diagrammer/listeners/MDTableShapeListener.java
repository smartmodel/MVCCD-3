package window.editor.diagrammer.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.SwingUtilities;
import window.editor.diagrammer.elements.shapes.MDTableShape;
import window.editor.diagrammer.menus.MDTableShapeMenu;
import window.editor.diagrammer.services.DiagrammerService;

public class MDTableShapeListener extends MouseAdapter implements Serializable {

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            this.showMenu(e);
        }
    }

    private void showMenu(MouseEvent e) {
        final MDTableShape shape = (MDTableShape) e.getSource();
        final Point converted = SwingUtilities.convertPoint(shape, e.getPoint(), DiagrammerService.getDrawPanel());
        final MDTableShapeMenu menu = new MDTableShapeMenu(shape);

        menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
    }
}
