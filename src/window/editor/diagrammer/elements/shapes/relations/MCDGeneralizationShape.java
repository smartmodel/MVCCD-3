package window.editor.diagrammer.elements.shapes.relations;

import mcd.MCDGeneralization;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

import java.awt.*;

public class MCDGeneralizationShape extends RelationShape {

  public MCDGeneralizationShape(MCDGeneralization relatedRepositoryGeneralization, MCDEntityShape source, MCDEntityShape destination) {
    this(source, destination);
    this.relatedRepositoryElement = relatedRepositoryGeneralization;
  }

  public MCDGeneralizationShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination, false);
  }

  @Override
  public void setLineAspect(Graphics2D graphics2D) {
    graphics2D.setStroke(new BasicStroke(1));
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    this.drawArrow(graphics2D);
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {

  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_GENERALIZATION_XML_TAG;
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
    return (MCDGeneralization) this.relatedRepositoryElement;
  }

  public void setGeneralization(MCDGeneralization generalization) {
    this.relatedRepositoryElement = generalization;
  }
}
