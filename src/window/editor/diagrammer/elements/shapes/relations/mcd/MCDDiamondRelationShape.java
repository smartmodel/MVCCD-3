package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serial;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.utils.UIUtils;

public abstract class MCDDiamondRelationShape extends RelationShape {

  @Serial
  private static final long serialVersionUID = 2142680627104283937L;

  public MCDDiamondRelationShape() {
  }

  public MCDDiamondRelationShape(SquaredShape source, IShape destination, boolean isReflexive) {
    super(source, destination, isReflexive);
  }

  @Override
  public void defineLineAspect(Graphics2D graphics2D) {
    graphics2D.setStroke(new BasicStroke(1));
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    this.drawDiamond(graphics2D);
  }

  protected void drawDiamond(Graphics2D graphics2D) {
    final RelationAnchorPointShape previousPoint = this.anchorPoints.get(this.getLastPoint().getIndex() - 1);
    final RelationAnchorPointShape lastPoint = this.getLastPoint();

    final int NUMBER_OF_POINTS = 4;
    final int DIAMOND_WIDTH = (int) UIUtils.getCompositionDiamondWidth();
    final int DIAMOND_HEIGHT = (int) UIUtils.getCompositionDiamondHeight();

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

    // Dessine le diamant
    graphics2D.setColor(this.getDiamondBackgroundColor());
    graphics2D.fillPolygon(xpoints, ypoints, NUMBER_OF_POINTS);

    // Dessine la bordure autour du diamant
    graphics2D.setColor(this.getDiamondBorderColor());
    graphics2D.drawPolygon(xpoints, ypoints, NUMBER_OF_POINTS);
  }

  protected abstract Color getDiamondBorderColor();
  protected abstract Color getDiamondBackgroundColor();
}