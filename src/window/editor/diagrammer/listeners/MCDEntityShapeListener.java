package window.editor.diagrammer.listeners;

import window.editor.diagrammer.DrawPanel;
import window.editor.diagrammer.elements.MCDEntityShape;
import window.editor.diagrammer.utils.GridUtils;
import window.editor.diagrammer.utils.ResizableBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MCDEntityShapeListener extends MouseAdapter {

    private int cursor;
    private Point startPoint = null;
    MCDEntityShape component;
    DrawPanel drawPanel;

    public MCDEntityShapeListener(MCDEntityShape component, DrawPanel drawPanel) {
        this.component = component;
        this.drawPanel =  drawPanel;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
            this.handleResize(mouseEvent.getPoint());
    }


    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        ResizableBorder resizableBorder = (ResizableBorder) this.component.getBorder();
        cursor = resizableBorder.getCursor(mouseEvent);
        this.startPoint = mouseEvent.getPoint();

        this.component.requestFocus();
        this.component.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.component.hasFocus()) {
            ResizableBorder resizableBorder = (ResizableBorder) this.component.getBorder();
            this.component.setCursor(Cursor.getPredefinedCursor(resizableBorder.getCursor(e)));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        this.startPoint = null;
    }

    private void handleResize(Point mouseClick){

        if (startPoint != null) {

            int x = this.component.getX();
            int y = this.component.getY();
            int width = this.component.getWidth();
            int height = this.component.getHeight();

            int newX;
            int newY;
            int newWidth;
            int newHeight;


            int differenceX = GridUtils.alignToGrid(mouseClick.x - startPoint.x, this.drawPanel.getGridSize());
            int differenceY = GridUtils.alignToGrid(mouseClick.y - startPoint.y, this.drawPanel.getGridSize());
            System.out.println();

            switch (cursor) {
                case Cursor.N_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x, this.drawPanel.getGridSize());
                    newY = GridUtils.alignToGrid(y + differenceY, this.drawPanel.getGridSize());
                    newWidth = GridUtils.alignToGrid(width, this.drawPanel.getGridSize());
                    newHeight = GridUtils.alignToGrid(height - differenceY, this.drawPanel.getGridSize());

                    this.component.resize(newX, newY, newWidth, newHeight);
                    break;
                }
                case Cursor.S_RESIZE_CURSOR: {
                    newX = x;
                    newY = y;
                    newWidth = width;
                    newHeight = GridUtils.alignToGrid(height + differenceY, this.drawPanel.getGridSize());

                    this.component.resize(newX, newY, newWidth, newHeight);

                    this.startPoint = new Point(mouseClick.x, newHeight);
                    break;
                }
                case Cursor.W_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x + differenceX, this.drawPanel.getGridSize());
                    newY = GridUtils.alignToGrid((y), this.drawPanel.getGridSize());
                    newWidth = GridUtils.alignToGrid(width - differenceX, this.drawPanel.getGridSize());
                    newHeight = GridUtils.alignToGrid(height, this.drawPanel.getGridSize());

                    this.component.resize(newX, newY, newWidth, newHeight);

                    break;
                }
                case Cursor.E_RESIZE_CURSOR: {
                    newX = x;
                    newY = y;
                    newWidth = GridUtils.alignToGrid(width + differenceX, this.drawPanel.getGridSize());
                    newHeight = height;

                    this.component.resize(newX, newY, newWidth, newHeight);

                    this.startPoint = new Point(newWidth, mouseClick.y);

                    break;
                }
                case Cursor.NW_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x + differenceX, this.drawPanel.getGridSize());
                    newY = GridUtils.alignToGrid(y + differenceY, this.drawPanel.getGridSize());
                    newWidth = GridUtils.alignToGrid(width - differenceX, this.drawPanel.getGridSize());
                    newHeight = GridUtils.alignToGrid(height - differenceY, this.drawPanel.getGridSize());

                    this.component.resize(newX, newY, newWidth, newHeight);



                    break;
                }
                case Cursor.NE_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x, this.drawPanel.getGridSize());
                    newY = GridUtils.alignToGrid(y + differenceY, this.drawPanel.getGridSize());
                    newWidth = GridUtils.alignToGrid(width + differenceX, this.drawPanel.getGridSize());
                    newHeight = GridUtils.alignToGrid(height - differenceY, this.drawPanel.getGridSize());

                    this.component.resize(newX, newY, newWidth, newHeight);


                    this.startPoint = new Point(newWidth, 0);

                    break;
                }
                case Cursor.SW_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x + differenceX, this.drawPanel.getGridSize());
                    newY = GridUtils.alignToGrid(y, this.drawPanel.getGridSize());
                    newWidth = GridUtils.alignToGrid(width - differenceX, this.drawPanel.getGridSize());
                    newHeight = GridUtils.alignToGrid(height + differenceY, this.drawPanel.getGridSize());

                    this.component.resize(newX, newY, newWidth, newHeight);

                    this.startPoint = new Point(0, newHeight);

                    break;
                }
                case Cursor.SE_RESIZE_CURSOR: {
                    newX = GridUtils.alignToGrid(x, this.drawPanel.getGridSize());
                    newY = GridUtils.alignToGrid(y, this.drawPanel.getGridSize());
                    newWidth = GridUtils.alignToGrid(width + differenceX, this.drawPanel.getGridSize());
                    newHeight = GridUtils.alignToGrid(height + differenceY, this.drawPanel.getGridSize());

                    this.component.resize(newX, newY, newWidth, newHeight);

                    this.startPoint = new Point(newWidth, newHeight);

                    break;
                }
                case Cursor.MOVE_CURSOR: {


                    Rectangle bounds = this.component.getBounds();
                    bounds.translate(differenceX, differenceY);

                    this.component.setBounds(bounds);
                    this.component.repaint();

                    this.drawPanel.getHandler().updatePanelAndScrollbars();
                    break;
                }
            }
            this.component.setCursor(Cursor.getPredefinedCursor(cursor));
        }
    }

}
