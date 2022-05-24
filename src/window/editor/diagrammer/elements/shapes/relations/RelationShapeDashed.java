package window.editor.diagrammer.elements.shapes.relations;

import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.SquaredShape;
import window.editor.diagrammer.listeners.LabelShapeListener;
import window.editor.diagrammer.services.DiagrammerService;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 * Inspiré de <a href="https://coderanch.com/t/340443/java/Draw-arrow-head-line">...</a>
 */

public class RelationShapeDashed extends RelationShape implements IShape, Serializable {

    private final String label;

    double phi = Math.toRadians(25);
    int barb = 16;

    public RelationShapeDashed(SquaredShape source, SquaredShape destination, String label) {
        super(source, destination, false);
        this.label = label;
        addListeners();
        createLabelsAfterRelationShapeEdit();
    }


    @Override
    public void drawSegments(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pour chaque point d'ancrage
        for (int i = 0; i < this.pointsAncrage.size(); i++) {
            if (i != this.pointsAncrage.size() - 1) {
                // Lignes de flèche
                float[] dash1 = {2f, 0f, 2f};
                var bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
                graphics2D.setStroke(bs1);
                graphics2D.drawLine((int) this.pointsAncrage.get(i).getX(), (int) this.pointsAncrage.get(i).getY(), (int) this.pointsAncrage.get(i + 1).getX(), (int) this.pointsAncrage.get(i + 1).getY());
                // Tête de flèche
                Point sw = new Point((int) this.pointsAncrage.get(i).getX(), (int) this.pointsAncrage.get(i).getY());
                Point ne = new Point((int) this.pointsAncrage.get(i + 1).getX(), (int) this.pointsAncrage.get(i + 1).getY());

                drawArrowHead(graphics2D, ne, sw);
            }
        }

    }

    @Override
    public void doDraw(Graphics2D graphics2D) {
    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {
        if (!label.isEmpty()) {
            LabelShape labelShape;
            if (pointsAncrage.size() <= 2) {
                RelationPointAncrageShape anchorPoint = pointsAncrage.get(0);
                Point relationCenter = getCenter();
                int distanceInXFromAnchorPoint = Math.abs(relationCenter.x - anchorPoint.x);
                int distanceInYFromAnchorPoint = Math.abs(relationCenter.y - anchorPoint.y);
                labelShape = createOrUpdateLabel(anchorPoint, label, LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

            } else {
                int middleIndex = pointsAncrage.size() / 2;
                labelShape = createOrUpdateLabel(pointsAncrage.get(middleIndex), label, LabelType.ASSOCIATION_NAME, 0, 0);
            }
            DiagrammerService.getDrawPanel().add(labelShape);
        } else {
            deleteLabel(LabelType.ASSOCIATION_NAME);
        }

        DiagrammerService.getDrawPanel().repaint();
    }

    private void addListeners() {
        LabelShapeListener listener = new LabelShapeListener();
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
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

