package window.editor.diagrammer.listeners;

import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.menus.EntityShapeMenu;
import window.editor.diagrammer.palette.PaletteButtonType;
import window.editor.diagrammer.palette.PalettePanel;
import window.editor.diagrammer.services.DiagrammerService;
import window.editor.diagrammer.utils.RelationCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class MCDEntityShapeListener extends MouseAdapter implements Serializable {

    private static final long serialVersionUID = 1000;
    private final MCDEntityShape shape;

    public MCDEntityShapeListener(MCDEntityShape shape) {
        this.shape = shape;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            this.showMenu(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (PalettePanel.activeButton != null) {
            if (PalettePanel.activeButton.getType() == PaletteButtonType.LINK_CREATION) {
                RelationCreator.setSource(this.shape);
            }
        }

    }

    private void showMenu(MouseEvent event) {
        Point converted = SwingUtilities.convertPoint(this.shape, event.getPoint(), DiagrammerService.getDrawPanel());
        EntityShapeMenu menu = new EntityShapeMenu(this.shape);

        menu.show(DiagrammerService.getDrawPanel(), converted.x, converted.y);
    }
}
