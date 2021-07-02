package window.editor.diagrammer.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public final class ResizableBorder implements Border {

  private final int DISTANCE = 8;
  int[] locations = {SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST, SwingConstants.EAST, //SwingConstants.NORTH_WEST,
      //SwingConstants.NORTH_EAST, //SwingConstants.SOUTH_WEST,
      //SwingConstants.SOUTH_EAST
  };
  int[] cursors = {Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR, Cursor.E_RESIZE_CURSOR, //Cursor.NW_RESIZE_CURSOR, //Cursor.NE_RESIZE_CURSOR,
      // Cursor.SW_RESIZE_CURSOR,// Cursor.SE_RESIZE_CURSOR
  };
  private boolean isVisible = false;

  @Override
  public void paintBorder(Component component, Graphics g, int x, int y, int w, int h) {
    if (this.isVisible) {
      g.setColor(Color.black);
      for (int location : this.locations) {
        Rectangle rect = this.getRectangle(x, y, w, h, location);
        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
        g.setColor(Color.BLACK);
        g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
      }
    }
  }

  @Override
  public Insets getBorderInsets(Component component) {
    return new Insets(this.DISTANCE, this.DISTANCE, this.DISTANCE, this.DISTANCE);
  }

  @Override
  public boolean isBorderOpaque() {
    return false;
  }

  private Rectangle getRectangle(int x, int y, int width, int height, int location) {
    switch (location) {
      case SwingConstants.NORTH:
        return new Rectangle(x + width / 2 - this.DISTANCE / 2, y, this.DISTANCE, this.DISTANCE);
      case SwingConstants.SOUTH:
        return new Rectangle(x + width / 2 - this.DISTANCE / 2, y + height - this.DISTANCE, this.DISTANCE, this.DISTANCE);
      case SwingConstants.WEST:
        return new Rectangle(x, y + height / 2 - this.DISTANCE / 2, this.DISTANCE, this.DISTANCE);
      case SwingConstants.EAST:
        return new Rectangle(x + width - this.DISTANCE, y + height / 2 - this.DISTANCE / 2, this.DISTANCE, this.DISTANCE);
      case SwingConstants.NORTH_WEST:
        return new Rectangle(x, y, this.DISTANCE, this.DISTANCE);
      case SwingConstants.NORTH_EAST:
        return new Rectangle(x + width - this.DISTANCE, y, this.DISTANCE, this.DISTANCE);
      case SwingConstants.SOUTH_WEST:
        return new Rectangle(x, y + height - this.DISTANCE, this.DISTANCE, this.DISTANCE);
      case SwingConstants.SOUTH_EAST:
        return new Rectangle(x + width - this.DISTANCE, y + height - this.DISTANCE, this.DISTANCE, this.DISTANCE);
      default:
        return new Rectangle();
    }
  }

  public int getCursor(MouseEvent me) {
    Component component = me.getComponent();
    final int width = component.getWidth();
    final int height = component.getHeight();
    for (int i = 0; i < this.locations.length; i++) {
      Rectangle rect = this.getRectangle(0, 0, width, height, this.locations[i]);
      if (rect.contains(me.getPoint())) {
        return this.cursors[i];
      }
    }
    return Cursor.MOVE_CURSOR;
  }

  public boolean isVisible() {
    return this.isVisible;
  }

  public void setVisible(boolean visible) {
    this.isVisible = visible;
  }
}