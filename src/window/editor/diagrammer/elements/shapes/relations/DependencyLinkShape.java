package window.editor.diagrammer.elements.shapes.relations;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.io.Serializable;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;
import window.editor.diagrammer.services.DiagrammerService;

/**
 * Inspiré de <a href="https://coderanch.com/t/340443/java/Draw-arrow-head-line">...</a>
 */

public class DependencyLinkShape extends RelationShape implements IShape, Serializable {

  private final String label;

  double phi = Math.toRadians(25);
  int barb = 16;

  public DependencyLinkShape(SquaredShape source, SquaredShape destination, String label) {
    super(source, destination, false);
    this.label = label;
    this.createLabelsAfterRelationShapeEdit();
  }


  @Override
  public void drawSegments(Graphics2D graphics2D) {
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Pour chaque point d'ancrage
    for (int i = 0; i < this.anchorPoints.size(); i++) {
      if (i != this.anchorPoints.size() - 1) {
        // Lignes de flèche
        this.defineLineAspect(graphics2D);
        graphics2D.drawLine(
            (int) this.anchorPoints.get(i).getX(),
            (int) this.anchorPoints.get(i).getY(), (int) this.anchorPoints.get(i + 1).getX(),
            (int) this.anchorPoints.get(i + 1).getY());
      }
    }
    // On repère les points d'ancrage pour dessinger la tête de flèche
    Point tail = getFirstPoint();
    Point tip = getLastPoint();

    this.drawArrowHead(graphics2D, tip, tail);
  }

  @Override
  public void defineLineAspect(Graphics2D graphics2D) {
    float[] dash1 = {2f, 0f, 2f};
    var bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
    graphics2D.setStroke(bs1);
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {

  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {
    if (!label.isEmpty()) {
      LabelShape labelShape;

      int middleIndex = anchorPoints.size() / 2;
      labelShape = createOrUpdateLabel(anchorPoints.get(middleIndex), label,
          LabelType.ASSOCIATION_NAME, 0, 0);

      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      deleteLabel(LabelType.ASSOCIATION_NAME);
    }

    DiagrammerService.getDrawPanel().repaint();
  }

  @Override
  public String getXmlTagName() {
    return null;
  }

  private void drawArrowHead(Graphics2D graphics2D, Point tip, Point tail) {
    // On redonne un aspect normal à l'objet Graphics2D afin de dessiner la tête de la flèche sans traits tillés
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

}

