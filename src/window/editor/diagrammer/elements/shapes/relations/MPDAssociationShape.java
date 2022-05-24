package window.editor.diagrammer.elements.shapes.relations;

import window.editor.diagrammer.elements.shapes.classes.SquaredShape;
import window.editor.diagrammer.services.DiagrammerService;

import java.awt.*;

public class MPDAssociationShape extends RelationShape {

    private final String associationLabel;

    public MPDAssociationShape(SquaredShape source, SquaredShape destination, String associationLabel) {
        super(source, destination, false);
        this.associationLabel = associationLabel;
        createLabelsAfterRelationShapeEdit();
    }

    @Override
    public void doDraw(Graphics2D graphics2D) {

    }

    @Override
    public void createLabelsAfterRelationShapeEdit() {
        if (!associationLabel.isEmpty()) {
            LabelShape labelShape;
            if (pointsAncrage.size() <= 2) {
                RelationPointAncrageShape anchorPoint = pointsAncrage.get(0);
                Point relationCenter = getCenter();
                int distanceInXFromAnchorPoint = Math.abs(relationCenter.x - anchorPoint.x);
                int distanceInYFromAnchorPoint = Math.abs(relationCenter.y - anchorPoint.y);
                labelShape = createOrUpdateLabel(anchorPoint, associationLabel, LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

            } else {
                int middleIndex = pointsAncrage.size() / 2;
                labelShape = createOrUpdateLabel(pointsAncrage.get(middleIndex), associationLabel, LabelType.ASSOCIATION_NAME, 0, 0);
            }
            DiagrammerService.getDrawPanel().add(labelShape);
        } else {
            deleteLabel(LabelType.ASSOCIATION_NAME);
        }
/*
        // Cardinalités source
        if (!association.getFrom().getMultiStr().isEmpty()) {
            LabelShape labelShape = createOrUpdateLabel(getFirstPoint(), association.getFrom().getMultiStr(), LabelType.SOURCE_CARDINALITY, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape);
        } else {
            deleteLabel(LabelType.SOURCE_CARDINALITY);
        }

        // Cardinalités destination
        if (!association.getTo().getMultiStr().isEmpty()) {
            LabelShape labelShape = createOrUpdateLabel(getLastPoint(), association.getTo().getMultiStr(), LabelType.DESTINATION_CARDINALITY, 0, 0);
            DiagrammerService.getDrawPanel().add(labelShape);
        } else
            deleteLabel(LabelType.DESTINATION_CARDINALITY);*/

        DiagrammerService.getDrawPanel().repaint();
    }

    @Override
    public String getXmlTagName() {
        return null;
    }
}
