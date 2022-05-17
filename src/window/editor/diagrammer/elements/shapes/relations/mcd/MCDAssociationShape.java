package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import mcd.MCDAssociation;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;
import window.editor.diagrammer.services.DiagrammerService;

public class MCDAssociationShape extends RelationShape {

  private static final long serialVersionUID = 5405071714676740739L;

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
  public void defineLineAspect(Graphics2D graphics2D) {
    graphics2D.setStroke(new BasicStroke(1));
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {}

  @Override
  public void createLabelsAfterRelationShapeEdit() {

    MCDAssociation association = this.getMCDAssociation();

    // Nom d'association
    if (!association.getName().isEmpty()) {
      LabelShape labelShape;
      if (this.anchorPoints.size() <= 2) {
        RelationAnchorPointShape anchorPoint = this.anchorPoints.get(0);
        Point relationCenter = this.getCenter();
        int distanceInXFromAnchorPoint = Math.abs(relationCenter.x - anchorPoint.x);
        int distanceInYFromAnchorPoint = Math.abs(relationCenter.y - anchorPoint.y);
        labelShape = this.createOrUpdateLabel(anchorPoint, association.getName(), LabelType.ASSOCIATION_NAME, distanceInXFromAnchorPoint, distanceInYFromAnchorPoint);

      } else {
        int middleIndex = this.anchorPoints.size() / 2;
        labelShape = this.createOrUpdateLabel(this.anchorPoints.get(middleIndex), association.getName(), LabelType.ASSOCIATION_NAME, 0, 0);
      }
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      this.deleteLabel(LabelType.ASSOCIATION_NAME);
    }

    // Rôle source
    if (!association.getFrom().getName().isEmpty()) {
      LabelShape labelShape = this.createOrUpdateLabel(this.getFirstPoint(), association.getFrom().getName(), LabelType.SOURCE_ROLE, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      this.deleteLabel(LabelType.SOURCE_ROLE);
    }

    // Rôle destination
    if (!association.getTo().getName().isEmpty()) {
      LabelShape labelShape = this.createOrUpdateLabel(this.getLastPoint(), association.getTo().getName(), LabelType.DESTINATION_ROLE, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      this.deleteLabel(LabelType.DESTINATION_ROLE);
    }

    // Cardinalités source
    if (!association.getFrom().getMultiStr().isEmpty()) {
      LabelShape labelShape = this.createOrUpdateLabel(this.getFirstPoint(), association.getFrom().getMultiStr(), LabelType.SOURCE_CARDINALITY, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      this.deleteLabel(LabelType.SOURCE_CARDINALITY);
    }

    // Cardinalités destination
    if (!association.getTo().getMultiStr().isEmpty()) {
      LabelShape labelShape = this.createOrUpdateLabel(this.getLastPoint(), association.getTo().getMultiStr(), LabelType.DESTINATION_CARDINALITY, 0, 0);
      DiagrammerService.getDrawPanel().add(labelShape);
    } else {
      this.deleteLabel(LabelType.DESTINATION_CARDINALITY);
    }

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