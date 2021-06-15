package window.editor.diagrammer.interfaces;

import java.awt.*;

public interface IShape {
    default void setLocationDifference(int differenceX, int differenceY){
        this.setLocation(this.getBounds().x + differenceX, this.getBounds().y + differenceY);
    }


    void setLocation(Point location);


    void setSize(Dimension dimension);

    Rectangle getBounds();

    void zoom(int fromFactor, int toFactor);

    void drag(int differenceX, int differenceY);

    default void setLocation(int x, int y) {
        this.setLocation(new Point(x, y));
    }

    default void setSize(int width, int height) {
        this.setSize(new Dimension(width, height));
    }
}
