package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

public class MCDCompositionShape extends RelationShape {

  private static final long serialVersionUID = 1660545571630623463L;

  // TODO -> Changer le premier paramètre en MCDComposition lorsqu'elle aura été implémentée (ne pas oublier de changer le getter)

  public MCDCompositionShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination, false);
  }

  public MCDCompositionShape(MDElement relatedRepositoryComposition, MCDEntityShape source, MCDEntityShape destination) {
    this(source, destination);
    this.relatedRepositoryElement = relatedRepositoryComposition;
  }

  @Override
  public void defineLineAspect(Graphics2D graphics2D) {
    graphics2D.setStroke(new BasicStroke(1));
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    this.drawDiamond(graphics2D);
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {

  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_COMPOSITION_XML_TAG;
  }

  public void drawDiamond(Graphics2D graphics2D) {
    final RelationAnchorPointShape previousPoint = this.pointsAncrage.get(this.getLastPoint().getIndex() - 1);
    final RelationAnchorPointShape lastPoint = this.getLastPoint();

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

  public MDElement getMCDComposition() {
    return this.relatedRepositoryElement;
  }
}