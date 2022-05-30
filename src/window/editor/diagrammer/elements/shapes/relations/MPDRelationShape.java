package window.editor.diagrammer.elements.shapes.relations;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import m.interfaces.IMRelation;
import mpdr.MPDRRelFKEnd;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;
import window.editor.diagrammer.services.DiagrammerService;

public class MPDRelationShape extends RelationShape {


  public MPDRelationShape(SquaredShape source, SquaredShape destination, IMRelation relation) {
    super((ClassShape) source, destination, relation, false);
    createLabelsAfterRelationShapeEdit();
  }

  private void drawArrowHead(Graphics2D graphics2D, Point tip, Point tail) {
    final double phi = Math.toRadians(25);
    final int barb = 16;

    var bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, null, 0);
    graphics2D.setStroke(bs);

    double dy = tip.y - tail.y;
    double dx = tip.x - tail.x;
    double theta = Math.atan2(dy, dx);

    double x;
    double y;
    double rho = theta + phi;
    for (int j = 0; j < 2; j++) {
      x = tip.x - barb * Math.cos(rho);
      y = tip.y - barb * Math.sin(rho);
      graphics2D.draw(new Line2D.Double(tip.x, tip.y, x, y));
      rho = theta - phi;
    }
  }

  @Override
  public void defineLineAspect(Graphics2D graphics2D) {
    graphics2D.setStroke(new BasicStroke(1));
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    // On repère les points d'ancrage pour dessiner la tête de flèche
    Point tail = getFirstPoint();
    Point top = getLastPoint();

    this.drawArrowHead(graphics2D, top, tail);
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {
    if (!this.getRelatedRepositoryElement().getName().isEmpty()) {

      LabelShape labelShape;

      int middleIndex = anchorPoints.size() / 2;
      labelShape = createOrUpdateLabel(anchorPoints.get(middleIndex),
          getRelatedRepositoryElement().getName(),
          LabelType.ASSOCIATION_NAME, 0, 0);

      DiagrammerService.getDrawPanel().add(labelShape);

    } else {
      deleteLabel(LabelType.ASSOCIATION_NAME);
    }

    // Cardinalité Source
    if (!this.getRelatedRepositoryElement().getB().getImRelation().getName().isEmpty()) {
      MPDRRelFKEnd b = (MPDRRelFKEnd) this.getRelatedRepositoryElement().getB();
      this.setSource(
          DiagrammerService.getDrawPanel().getMDTableShapeByName(b.getmElement().getName()));
      LabelShape labelShape = createOrUpdateLabel(getFirstPoint(), b.getMultiMaxStd().getText(),
          LabelType.SOURCE_CARDINALITY, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      deleteLabel(LabelType.SOURCE_CARDINALITY);
    }

    // Cardinalité Destination
    if (!this.getRelatedRepositoryElement().getA().getImRelation().getName().isEmpty()) {
      MPDRRelFKEnd a = (MPDRRelFKEnd) this.getRelatedRepositoryElement().getA();
      this.setDestination(
          DiagrammerService.getDrawPanel().getMDTableShapeByName(a.getmElement().getName()));
      LabelShape labelShape = createOrUpdateLabel(getLastPoint(), a.getMultiMaxStd().getText(),
          LabelType.DESTINATION_CARDINALITY, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      deleteLabel(LabelType.DESTINATION_CARDINALITY);
    }

    DiagrammerService.getDrawPanel().repaint();
  }

  @Override
  public String getXmlTagName() {
    return null;
  }
}
