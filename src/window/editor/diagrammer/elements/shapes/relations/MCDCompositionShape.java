package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDCompositionShape extends RelationShape {

  public MCDCompositionShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination, false);
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    this.drawDiamond(graphics2D);
  }

  @Override
  public void setInformations() {
    // TODO -> Implémenter cette méthode lorsque la composition sera prise en charge
  }

  @Override
  public void setDestinationRole(String role) {
    // TODO -> Implémenter cette méthode lorsque la composition sera prise en charge
  }

  @Override
  public void setSourceRole(String role) {
    // TODO -> Implémenter cette méthode lorsque la composition sera prise en charge
  }

  @Override
  public void setRelationName(String name) {
    // TODO -> Implémenter cette méthode lorsque la composition sera prise en charge
  }

  @Override
  public void setSourceCardinalite(String cardinalite) {
    // TODO -> Implémenter cette méthode lorsque la composition sera prise en charge
  }

  @Override
  public void setDestinationCardinalite(String cardinalite) {
    // TODO -> Implémenter cette méthode lorsque la composition sera prise en charge
  }

  public void drawDiamond(Graphics2D graphics2D) {
    final RelationPointAncrageShape previousPoint = this.pointsAncrage.get(this.getLastPoint().getIndex() - 1);
    final RelationPointAncrageShape lastPoint = this.getLastPoint();

    final int NUMBER_OF_POINTS = 4;
    final int DIAMOND_WIDTH = 8;
    final int DIAMOND_HEIGHT = 13;

    final int dx = lastPoint.x - previousPoint.x, dy = lastPoint.y - previousPoint.y;
    final double D = Math.sqrt(dx * dx + dy * dy);

    double xm = D - DIAMOND_HEIGHT;
    double xn = xm;
    double ym = DIAMOND_WIDTH;
    double yn = -DIAMOND_WIDTH, x;

    final double sin = dy / D;
    final double cos = dx / D;

    x = xm * cos - ym * sin + previousPoint.x;
    ym = xm * sin + ym * cos + previousPoint.y;
    xm = x;
    x = xn * cos - yn * sin + previousPoint.x;
    yn = xn * sin + yn * cos + previousPoint.y;
    xn = x;

    final double xq = (DIAMOND_HEIGHT * 2 / D) * previousPoint.x + ((D - DIAMOND_HEIGHT * 2) / D) * lastPoint.x;
    final double yq = (DIAMOND_HEIGHT * 2 / D) * previousPoint.y + ((D - DIAMOND_HEIGHT * 2) / D) * lastPoint.y;

    final int[] xpoints = {lastPoint.x, (int) xm, (int) xq, (int) xn};
    final int[] ypoints = {lastPoint.y, (int) ym, (int) yq, (int) yn};

    graphics2D.fillPolygon(xpoints, ypoints, NUMBER_OF_POINTS);
  }
}
