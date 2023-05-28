package window.editor.diagrammer.utils;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class TextPositionUtils {

  public static Point centerTextInRectangle(Rectangle rectangle, String text, Graphics2D graphics) {
    int fontHeight = graphics.getFontMetrics().getAscent();
    int x = (rectangle.width / 2) - (graphics.getFontMetrics().stringWidth(text) / 2);
    int y = (rectangle.height / 2) - (graphics.getFontMetrics().getAscent() / 2);
    return new Point(x, y);
  }
}