package window.editor.diagrammer.elements.shapes.relations;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;
import window.editor.diagrammer.services.DiagrammerService;


public class DependencyLinkShape extends RelationShape implements IShape, Serializable {

  private final String label;

  private final double phi = Math.toRadians(25);

  public DependencyLinkShape(SquaredShape source, SquaredShape destination, String label) {
    super(source, destination, false);
    this.label = label;
    this.createLabelsAfterRelationShapeEdit();
  }

  @Override
  public void defineLineAspect(Graphics2D graphics2D) {
    float[] dash1 = {2f, 0f, 2f};
    var bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
    graphics2D.setStroke(bs1);
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    // On repère le point d'ancrage de la tête de flèche
    Point top = getLastPoint();

    this.drawArrowHead(graphics2D, top);
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {
    if (!label.isEmpty()) {
      LabelShape labelShape;

      if (this.anchorPoints.size() <= 2) {
        RelationAnchorPointShape anchorPoint = this.anchorPoints.get(0);
        Point relationCenter = this.getCenter();

        int distanceInXFromAnchorPoint = (int) (relationCenter.x - anchorPoint.x * 1.1);
        int distanceInYFromAnchorPoint = (relationCenter.y - anchorPoint.y);

        labelShape = this.createOrUpdateLabel(anchorPoint, label,
            LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);
      } else {
        int middleIndex = anchorPoints.size() / 2;

        labelShape = createOrUpdateLabel(anchorPoints.get(middleIndex),
            label,
            LabelType.ASSOCIATION_NAME, 0, 0);
      }
      DiagrammerService.getDrawPanel().add(labelShape);
      super.getLabels().add(labelShape);
    } else {
      deleteLabel(LabelType.ASSOCIATION_NAME);
    }

    super.addLabelsInDiagrammeur();
  }

  @Override
  public String getXmlTagName() {
    return null;
  }


  /**
   * Inspiré de <a href="https://coderanch.com/t/340443/java/Draw-arrow-head-line">...</a>
   */
  private void drawArrowHead(Graphics2D graphics2D, Point tip) {
    try {
      final RelationAnchorPointShape previousPoint = this.anchorPoints.get(
          this.anchorPoints.get(this.getAnchorPoints().size() - 1).getIndex() - 1);
      // On redonne un aspect normal à l'objet Graphics2D afin de dessiner la tête de la flèche sans traits tillés
      var bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, null, 0);
      graphics2D.setStroke(bs);

      double dy = tip.y - previousPoint.y;
      double dx = tip.x - previousPoint.x;
      double theta = Math.atan2(dy, dx);

      double x;
      double y;
      double rho = theta + phi;
      int barb = 16;
      for (int j = 0; j < 2; j++) {
        x = tip.x - barb * Math.cos(rho);
        y = tip.y - barb * Math.sin(rho);
        graphics2D.draw(new Line2D.Double(tip.x, tip.y, x, y));
        rho = theta - phi;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

