package window.editor.diagrammer.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ResizableBorder implements Border {

    private int dist = 8;

    int[] locations = {
            SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
            SwingConstants.EAST, SwingConstants.NORTH_WEST,
            SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
            SwingConstants.SOUTH_EAST
    };

    int[] cursors = {
            Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
            Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
            Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
    };

    @Override
    public Insets getBorderInsets(Component component) {
        return new Insets(dist, dist, dist, dist);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component component, Graphics g, int x, int y,int w, int h) {

        g.setColor(Color.black);
        //g.drawRect(0, 0, w, h-2);

        if (component.hasFocus()) {
            for (int location : locations) {
                Rectangle rect = getRectangle(x, y, w, h, location);
                if (rect != null) {
                    g.setColor(Color.WHITE);
                    g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                    g.setColor(Color.BLACK);
                    g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                }
            }
        }
    }

    private Rectangle getRectangle(int x, int y, int width, int height, int location) {

        switch (location) {

            case SwingConstants.NORTH:
                return new Rectangle(x + width / 2 - dist / 2, y, dist, dist);
            case SwingConstants.SOUTH:
                return new Rectangle(x + width / 2 - dist / 2, y + height - dist, dist, dist);
            case SwingConstants.WEST:
                return new Rectangle(x, y + height / 2 - dist / 2, dist, dist);
            case SwingConstants.EAST:
                return new Rectangle(x + width - dist, y + height / 2 - dist / 2, dist, dist);
            case SwingConstants.NORTH_WEST:
                return new Rectangle(x, y, dist, dist);
            case SwingConstants.NORTH_EAST:
                return new Rectangle(x + width - dist, y, dist, dist);
            case SwingConstants.SOUTH_WEST:
                return new Rectangle(x, y + height - dist, dist, dist);
            case SwingConstants.SOUTH_EAST:
                return new Rectangle(x + width - dist, y + height - dist, dist, dist);
            default:
                return new Rectangle();
        }
    }

    public int getCursor(MouseEvent me) {

        Component component = me.getComponent();
        int width = component.getWidth();
        int height = component.getHeight();

        for (int i = 0; i < locations.length; i++) {

            Rectangle rect = getRectangle(0, 0, width, height, locations[i]);

            if (rect != null && rect.contains(me.getPoint())) {
                return cursors[i];
            }
        }

        return Cursor.MOVE_CURSOR;
    }
}