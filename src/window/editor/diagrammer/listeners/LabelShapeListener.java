/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère les mouvements des labels ainsi que les autres événements
 * Par exemple : drag
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */


package window.editor.diagrammer.listeners;

import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class LabelShapeListener extends MouseAdapter implements Serializable {

  private static final long serialVersionUID = 1000;
  private Point origin;

  @Override
  public void mousePressed(MouseEvent e) {
    super.mousePressed(e);
    this.origin = e.getPoint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    LabelShape shape = (LabelShape) e.getSource();

    int differenceX = e.getPoint().x - this.origin.x;
    int differenceY = e.getPoint().y - this.origin.y;

    shape.setDistanceInXFromPointAncrage(shape.getDistanceInXFromPointAncrage() + differenceX);
    shape.setDistanceInYFromPointAncrage(shape.getDistanceInYFromPointAncrage() + differenceY);

    shape.setBounds(shape.getX() + differenceX, shape.getY() + differenceY, shape.getWidth(), shape.getHeight());
  }

}