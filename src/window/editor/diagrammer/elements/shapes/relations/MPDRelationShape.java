package window.editor.diagrammer.elements.shapes.relations;

import m.interfaces.IMRelation;
import mpdr.MPDRRelFKEnd;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.services.DiagrammerService;

import java.awt.*;
import java.awt.geom.Line2D;

public class MPDRelationShape extends RelationShape {


    public MPDRelationShape(SquaredShape source, SquaredShape destination, IMRelation relation) {
        super(source, destination, relation, false);
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
    public void doDraw(Graphics2D graphics2D) {
        // On repère les points d'ancrage pour dessiner la tête de flèche
        Point tail = getFirstPoint();
        Point top = getLastPoint();

        this.drawArrowHead(graphics2D, top, tail);
    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {
        if (!this.getRelation().getName().isEmpty()) {
            LabelShape labelShape;
            if (pointsAncrage.size() <= 2) {
                RelationPointAncrageShape anchorPoint = pointsAncrage.get(0);
                Point relationCenter = getCenter();
                int distanceInXFromAnchorPoint = Math.abs(relationCenter.x - anchorPoint.x);
                int distanceInYFromAnchorPoint = Math.abs(relationCenter.y - anchorPoint.y);
                labelShape = createOrUpdateLabel(anchorPoint, getRelation().getName(), LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

            } else {
                int middleIndex = pointsAncrage.size() / 2;
                labelShape = createOrUpdateLabel(pointsAncrage.get(middleIndex), getRelation().getName(), LabelType.ASSOCIATION_NAME, 0, 0);
            }
            DiagrammerService.getDrawPanel().add(labelShape);
        } else {
            deleteLabel(LabelType.ASSOCIATION_NAME);
        }

        // Cardinalité Source
        if (!getRelation().getB().getImRelation().getName().isEmpty()) {
            MPDRRelFKEnd b = (MPDRRelFKEnd) getRelation().getB();
            setSource(DiagrammerService.getDrawPanel().getMDTableShapeByName(b.getmElement().getName()));
            LabelShape labelShape = createOrUpdateLabel(getFirstPoint(), b.getMultiMaxStd().getText(), LabelType.SOURCE_CARDINALITY, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape);
        } else {
            deleteLabel(LabelType.SOURCE_CARDINALITY);
        }

        // Cardinalité Destination
        if (!getRelation().getA().getImRelation().getName().isEmpty()) {
            MPDRRelFKEnd a = (MPDRRelFKEnd) getRelation().getA();
            setDestination(DiagrammerService.getDrawPanel().getMDTableShapeByName(a.getmElement().getName()));
            LabelShape labelShape = createOrUpdateLabel(getLastPoint(), a.getMultiMaxStd().getText(), LabelType.DESTINATION_CARDINALITY, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape);
        } else
            deleteLabel(LabelType.DESTINATION_CARDINALITY);


        DiagrammerService.getDrawPanel().repaint();
    }

    @Override
    public String getXmlTagName() {
        return null;
    }
}
