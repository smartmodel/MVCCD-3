package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDCompositionShape extends RelationShape {

  public MCDCompositionShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination, false);
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
    this.drawDiamond(graphics2D);
  }

  @Override
  public void setInformations() {
  }

  public void drawDiamond(Graphics2D graphics2D) {
    RelationPointAncrageShape previousPoint = this.pointsAncrage.get(this.pointsAncrage.get(this.getPointsAncrage().size() - 1).getIndex() - 1);
    RelationPointAncrageShape lastPoint = this.pointsAncrage.get(this.getPointsAncrage().size() - 1);

    final int DIAMOND_WIDTH = 8;
    final int DIAMOND_HEIGHT = 13;

    int dx = lastPoint.x - previousPoint.x, dy = lastPoint.y - previousPoint.y;
    double D = Math.sqrt(dx * dx + dy * dy);
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
    graphics2D.fillPolygon(xpoints, ypoints, 4);
  }
}
