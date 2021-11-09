package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import mcd.MCDAssociation;
import mcd.MCDEntity;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;

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
    if (!association.getName().isEmpty())
      createLabel(new RelationPointAncrageShape(getCenter().x, getCenter().y), association.getName(), LabelType.ASSOCIATION_NAME);

    // Rôle source
    if (!association.getFrom().getName().isEmpty())
      createLabel(getFirstPoint(), association.getFrom().getName(), LabelType.SOURCE_ROLE);


    // Rôle destination
    if (!association.getTo().getName().isEmpty())
       createLabel(getLastPoint(), association.getTo().getName(), LabelType.DESTINATION_ROLE);


    // Cardinalités source
    if (!association.getFrom().getMultiStr().isEmpty())
      createLabel(getFirstPoint(), association.getFrom().getMultiStr(), LabelType.SOURCE_CARDINALITY);


    // Cardinalités destination
    if (!association.getTo().getMultiStr().isEmpty())
      createLabel(getLastPoint(), association.getTo().getMultiStr(), LabelType.DESTINATION_CARDINALITY);

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
