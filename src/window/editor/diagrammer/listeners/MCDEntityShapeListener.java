package window.editor.diagrammer.listeners;

import window.editor.diagrammer.DrawPanel;
import window.editor.diagrammer.elements.MCDEntityShape;
import window.editor.diagrammer.utils.GridUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MCDEntityShapeListener extends MouseAdapter {

    Point startPoint;
    private int cursor;
    private Point startPos = null;
    MCDEntityShape component;
    DrawPanel drawPanel;

    public MCDEntityShapeListener(MCDEntityShape component, DrawPanel drawPanel) {
        this.component = component;
        this.drawPanel =  drawPanel;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (SwingUtilities.isLeftMouseButton(mouseEvent)){
            if (startPos != null) {

                int deltaX = mouseEvent.getX() - startPos.x;
                int deltaY = mouseEvent.getY() - startPos.y;

                Rectangle bounds = this.component.getBounds();
                bounds.translate(GridUtils.alignToGrid(deltaX, this.drawPanel.getGridSize()), GridUtils.alignToGrid(deltaY, this.drawPanel.getGridSize()));
                this.component.setBounds(bounds);
                this.component.setCursor(Cursor.getPredefinedCursor(cursor));
            }
            this.drawPanel.getHandler().updatePanelAndScrollbars();
        }
    }


    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        JPanel source = (JPanel) mouseEvent.getSource();

        startPoint = source.getMousePosition();
        this.startPos = mouseEvent.getPoint();

        // Prend le focus
        source.requestFocus();
        source.repaint();
    }

}
