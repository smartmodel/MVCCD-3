package window.editor.diagrammer.elements.shapes.relations;

import mcd.MCDAssociation;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;

import java.awt.*;

public class MCDAssociationShape extends RelationShape {

  public MCDAssociationShape(MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
    super(source, destination, isReflexive);
  }
  public MCDAssociationShape(int id, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
    super(id, source, destination, isReflexive);
  }


  public MCDAssociationShape(MCDAssociation relatedRepositoryAssociation, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
    this(source, destination, isReflexive);
    this.relatedRepositoryElement = relatedRepositoryAssociation;
  }

  public MCDAssociationShape(int id, MCDAssociation relatedRepositoryAssociation, MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
    super(id, relatedRepositoryAssociation, source, destination, isReflexive);
  }
  @Override
  public void doDraw(Graphics2D graphics2D) {}

  @Override
  public void createLabelsAfterRelationShapeEdit(){

    MCDAssociation association = getMCDAssociation();

    // Nom d'association
    if (!association.getName().isEmpty()){
      LabelShape labelShape;
      if (pointsAncrage.size() <= 2){
        RelationPointAncrageShape anchorPoint = pointsAncrage.get(0);
        Point relationCenter = getCenter();
        int distanceInXFromAnchorPoint = Math.abs(relationCenter.x - anchorPoint.x);
        int distanceInYFromAnchorPoint = Math.abs(relationCenter.y - anchorPoint.y);
        labelShape = createOrUpdateLabel(anchorPoint, association.getName(), LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

      } else {
        int middleIndex = pointsAncrage.size() / 2;
        labelShape = createOrUpdateLabel(pointsAncrage.get(middleIndex), association.getName(), LabelType.ASSOCIATION_NAME, 0, 0);
      }
        DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      deleteLabel(LabelType.ASSOCIATION_NAME);
    }

    // Rôle source
    if (!association.getFrom().getName().isEmpty()){
      LabelShape labelShape = createOrUpdateLabel(getFirstPoint(), association.getFrom().getName(), LabelType.SOURCE_ROLE, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      deleteLabel(LabelType.SOURCE_ROLE);
    }

    // Rôle destination
    if (!association.getTo().getName().isEmpty()){
      LabelShape labelShape = createOrUpdateLabel(getLastPoint(), association.getTo().getName(), LabelType.DESTINATION_ROLE, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      deleteLabel(LabelType.DESTINATION_ROLE);
    }

    // Cardinalités source
    if (!association.getFrom().getMultiStr().isEmpty()){
      LabelShape labelShape = createOrUpdateLabel(getFirstPoint(), association.getFrom().getMultiStr(), LabelType.SOURCE_CARDINALITY, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      deleteLabel(LabelType.SOURCE_CARDINALITY);
    }

    // Cardinalités destination
    if (!association.getTo().getMultiStr().isEmpty()){
      LabelShape labelShape = createOrUpdateLabel(getLastPoint(), association.getTo().getMultiStr(), LabelType.DESTINATION_CARDINALITY, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else
      deleteLabel(LabelType.DESTINATION_CARDINALITY);

    DiagrammerService.getDrawPanel().repaint();


  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_ASSOCIATION_XML_TAG;
  }

  public MCDAssociation getMCDAssociation() {
    return (MCDAssociation) this.relatedRepositoryElement;
  }

  public void setMCDAssociation(MCDAssociation association) {
    this.relatedRepositoryElement = association;
  }


}
