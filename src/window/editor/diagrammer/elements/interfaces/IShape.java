/***
 * Cette classe peut être utilisée en l'état actuel. Elle représente toutes les formes présentes dans ArcDataModeler.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.elements.interfaces;

import java.awt.*;

public interface IShape {

    default void setLocationDifference(int differenceX, int differenceY) {
        this.setLocation(this.getBounds().x + differenceX, this.getBounds().y + differenceY);
    }

    void repaint();

    void setLocation(Point location);

    void setSize(Dimension dimension);

    int getId();

    Point getCenter();

    boolean contains(Point point);

    Rectangle getBounds();

    void zoom(int fromFactor, int toFactor);

    void drag(int differenceX, int differenceY);

    default void setLocation(int x, int y) {
        this.setLocation(new Point(x, y));
    }

    boolean isFocused();

    void setFocused(boolean isSelected);

    default void setSize(int width, int height) {
        this.setSize(new Dimension(width, height));
    }
}
