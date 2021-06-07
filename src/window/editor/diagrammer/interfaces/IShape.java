package window.editor.diagrammer.interfaces;

import javax.swing.*;
import java.awt.*;

public interface IShape {
    void setLocation(Point location);
    Point getLocation();
    void setSize(Dimension dimension);
    Dimension getSize();
    JComponent getComponent();
    Rectangle getBounds();
    void zoom(int fromFactor, int toFactor);
    void setBounds(Rectangle bounds);
    default void setLocation(int x, int y){
        this.setLocation(new Point(x, y));
    }
    default void setSize(int width, int height){
        this.setSize(new Dimension(width, height));
    }
}
