package window.editor.diagrammer.elements.shapes.relations;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;
import m.interfaces.IMRelation;
import mpdr.MPDRRelFKEnd;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;
import window.editor.diagrammer.services.DiagrammerService;

public class MPDRelationShape extends RelationShape implements IShape, Serializable {


  public MPDRelationShape(SquaredShape source, SquaredShape destination, IMRelation relation) {
    super(source, destination, relation, false);
    createLabelsAfterRelationShapeEdit();
  }

  @Override
  public void defineLineAspect(Graphics2D graphics2D) {
    graphics2D.setStroke(new BasicStroke(1));
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    // On repère le point d'ancrage de la tête de flèche
    Point top = getLastPoint();

    this.drawArrowHead(graphics2D, top);
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {
    if (!relation.getName().isEmpty()) {

      if (this.anchorPoints.size() <= 2) {
        RelationAnchorPointShape anchorPoint = this.anchorPoints.get(0);
        Point relationCenter = this.getCenter();

        int distanceInXFromAnchorPoint = (int) (relationCenter.x - anchorPoint.x * 1.1);
        int distanceInYFromAnchorPoint = (relationCenter.y - anchorPoint.y);

        this.createOrUpdateLabel(anchorPoint, relation.getName(),
            LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

      } else {
        int middleIndex = anchorPoints.size() / 2;

        createOrUpdateLabel(anchorPoints.get(middleIndex),
            relation.getName(),
            LabelType.ASSOCIATION_NAME, 0, 0);
      }

    } else {
      deleteLabel(LabelType.ASSOCIATION_NAME);
    }

    // Cardinalité Source
    if (!relation.getB().getImRelation().getName()
        .isEmpty()) {
      MPDRRelFKEnd b = (MPDRRelFKEnd) relation.getB();

      this.setSource(
          DiagrammerService.getDrawPanel().getMDTableShapeByName(b.getmElement().getName()));
      createOrUpdateLabel(getFirstPoint(), b.getMultiMaxStd().getText(),
          LabelType.SOURCE_CARDINALITY, 0, 0);

    } else {
      deleteLabel(LabelType.SOURCE_CARDINALITY);
    }

    // Cardinalité Destination
    if (!relation.getA().getImRelation().getName()
        .isEmpty()) {
      MPDRRelFKEnd a = (MPDRRelFKEnd) relation.getA();

      this.setDestination(
          DiagrammerService.getDrawPanel().getMDTableShapeByName(a.getmElement().getName()));

      createOrUpdateLabel(getLastPoint(), a.getMultiMaxStd().getText(),
          LabelType.DESTINATION_CARDINALITY, 0, 0);
    } else {
      deleteLabel(LabelType.DESTINATION_CARDINALITY);
    }

    super.addLabelsInDiagrammeur();
  }

  @Override
  public String getXmlTagName() {
    return null;
  }

  private void drawArrowHead(Graphics2D graphics2D, Point top) {
    try {
      final RelationAnchorPointShape previousPoint = this.anchorPoints.get(
          this.anchorPoints.get(this.getAnchorPoints().size() - 1).getIndex() - 1);

      final double phi = Math.toRadians(25);
      final int barb = 16;

      var bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, null, 0);
      graphics2D.setStroke(bs);

      double dy = top.y - previousPoint.y;
      double dx = top.x - previousPoint.x;
      double theta = Math.atan2(dy, dx);

      double x;
      double y;
      double rho = theta + phi;
      for (int j = 0; j < 2; j++) {
        x = top.x - barb * Math.cos(rho);
        y = top.y - barb * Math.sin(rho);
        graphics2D.draw(new Line2D.Double(top.x, top.y, x, y));
        rho = theta - phi;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
