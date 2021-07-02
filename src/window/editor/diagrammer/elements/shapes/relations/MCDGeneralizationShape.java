package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import mcd.MCDGeneralization;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDGeneralizationShape extends RelationShape {

  private MCDGeneralization generalization;

  public MCDGeneralizationShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination, false);
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    this.drawArrow(graphics2D);
  }

  @Override
  public void setInformations() {
    // TODO -> Implémenter cette méthode lorsque le bug du input non cliquable dans la fenêtre d'édition de la généralization sera résolu
  }

  @Override
  public void setDestinationRole(String role) {
    // TODO -> Implémenter cette méthode lorsque le bug du input non cliquable dans la fenêtre d'édition de la généralization sera résolu
  }

  @Override
  public void setSourceRole(String role) {
    // TODO -> Implémenter cette méthode lorsque le bug du input non cliquable dans la fenêtre d'édition de la généralization sera résolu
  }

  @Override
  public void setRelationName(String name) {
    // TODO -> Implémenter cette méthode lorsque le bug du input non cliquable dans la fenêtre d'édition de la généralization sera résolu
  }

  @Override
  public void setSourceCardinalite(String cardinalite) {
    // TODO -> Implémenter cette méthode lorsque le bug du input non cliquable dans la fenêtre d'édition de la généralization sera résolu
  }

  @Override
  public void setDestinationCardinalite(String cardinalite) {
    // TODO -> Implémenter cette méthode lorsque le bug du input non cliquable dans la fenêtre d'édition de la généralization sera résolu
  }

  public void drawArrow(Graphics2D graphics2D) {
    // TODO -> Faire en sorte que la flêche ait une bordune noire et un fond blanc

    final RelationPointAncrageShape previousPoint = this.pointsAncrage.get(this.pointsAncrage.get(this.getPointsAncrage().size() - 1).getIndex() - 1);
    final RelationPointAncrageShape lastPoint = this.pointsAncrage.get(this.getPointsAncrage().size() - 1);
    final int differenceX = lastPoint.x - previousPoint.x;
    final int differenceY = lastPoint.y - previousPoint.y;
    final double squareRoot = Math.sqrt(differenceX * differenceX + differenceY * differenceY);

    final int ARROW_WIDTH = 10;
    final int ARROW_HEIGHT = 13;

    double xm = squareRoot - ARROW_HEIGHT;
    double xn = xm;
    double ym = ARROW_WIDTH;
    double yn = -ARROW_WIDTH, x;

    final double sin = differenceY / squareRoot;
    final double cos = differenceX / squareRoot;
    final int NUMBER_OF_POINTS = 3;

    x = xm * cos - ym * sin + previousPoint.x;
    ym = xm * sin + ym * cos + previousPoint.y;
    xm = x;
    x = xn * cos - yn * sin + previousPoint.x;
    yn = xn * sin + yn * cos + previousPoint.y;
    xn = x;

    int[] xPoints = {lastPoint.x, (int) xm, (int) xn};
    int[] yPoints = {lastPoint.y, (int) ym, (int) yn};

    graphics2D.fillPolygon(xPoints, yPoints, NUMBER_OF_POINTS);
  }

  public MCDGeneralization getGeneralization() {
    return this.generalization;
  }

  public void setGeneralization(MCDGeneralization generalization) {
    this.generalization = generalization;
  }
}
