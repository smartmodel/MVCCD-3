package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import mcd.MCDGeneralization;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDGeneralizationShape extends RelationShape {

  private final int ARROW_WIDTH = 10;
  private final int ARROW_HEIGHT = 13;
  private MCDGeneralization generalization;

  public MCDGeneralizationShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination);
  }

  @Override
  public void setDestinationRole(String role) {
  }

  @Override
  public void setSourceRole(String role) {
  }

  @Override
  public void setRelationName(String name) {
  }

  @Override
  public void setSourceCardinalite(String cardinalite) {
  }

  @Override
  public void setDestinationCardinalite(String cardinalite) {
  }

  @Override
  public void draw(Graphics2D graphics2D) {
    this.drawSegments(graphics2D);
    this.drawArrow(graphics2D);
    if (this.generalization != null) {
      this.setName(this.generalization.getName());
      this.setSourceRole(this.generalization.getName());
    }
  }

  @Override
  public void setInformations() {
  }

  public void drawArrow(Graphics2D graphics2D) {
    RelationPointAncrageShape previousPoint = this.pointsAncrage.get(this.pointsAncrage.getLast().getIndex() - 1);
    RelationPointAncrageShape lastPoint = this.pointsAncrage.getLast();
    int differenceX = lastPoint.x - previousPoint.x;
    int differenceY = lastPoint.y - previousPoint.y;
    double squareRoot = Math.sqrt(differenceX * differenceX + differenceY * differenceY);
    double xm = squareRoot - ARROW_HEIGHT;
    double xn = xm;
    double ym = ARROW_WIDTH;
    double yn = -ARROW_WIDTH, x;
    double sin = differenceY / squareRoot;
    double cos = differenceX / squareRoot;
    x = xm * cos - ym * sin + previousPoint.x;
    ym = xm * sin + ym * cos + previousPoint.y;
    xm = x;
    x = xn * cos - yn * sin + previousPoint.x;
    yn = xn * sin + yn * cos + previousPoint.y;
    xn = x;
    int[] xPoints = {lastPoint.x, (int) xm, (int) xn};
    int[] yPoints = {lastPoint.y, (int) ym, (int) yn};
    graphics2D.fillPolygon(xPoints, yPoints, 3);
  }

  public MCDGeneralization getGeneralization() {
    return generalization;
  }

  public void setGeneralization(MCDGeneralization generalization) {
    this.generalization = generalization;
  }
}
