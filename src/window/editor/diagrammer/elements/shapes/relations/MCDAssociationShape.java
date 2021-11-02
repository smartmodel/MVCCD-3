package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import mcd.MCDAssociation;
import mcd.MCDEntity;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

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
  public void setInformations() {
    if (this.getMCDAssociation() != null) {
      // Nom d'association
      if (!this.getMCDAssociation().getName().isEmpty()) {
        this.setRelationName(this.getMCDAssociation().getName());
        this.associationName.setVisible(true);
      } else {
        this.associationName.setVisible(false);
      }
      // Rôle source
      if (!this.getMCDAssociation().getFrom().getName().isEmpty()) {
        this.setSourceRole(this.getMCDAssociation().getFrom().getName());
        this.sourceRole.setVisible(true);
      } else {
        this.sourceRole.setVisible(false);
      }
      // Rôle destination
      if (!this.getMCDAssociation().getTo().getName().isEmpty()) {
        this.setDestinationRole(this.getMCDAssociation().getTo().getName());
        this.destinationRole.setVisible(true);
      } else {
        this.destinationRole.setVisible(false);
      }
      // Cardinalité destination
      if (!this.getMCDAssociation().getTo().getMultiStr().isEmpty()) {
        this.setDestinationCardinalite(this.getMCDAssociation().getTo().getMultiStr());
        this.destinationCardinalite.setVisible(true);
      } else {
        this.destinationCardinalite.setVisible(false);
      }
      // Cardinalité source
      if (!this.getMCDAssociation().getFrom().getMultiStr().isEmpty()) {
        this.setSourceCardinalite(this.getMCDAssociation().getFrom().getMultiStr());
        this.sourceCardinalite.setVisible(true);
      } else {
        this.sourceCardinalite.setVisible(false);
      }

    }
  }

  @Override
  public void setDestinationRole(String role) {
    this.destinationRole.setText(role);
    this.destinationRole.repaint();
  }

  @Override
  public void setSourceRole(String role) {
    this.sourceRole.setText(role);
    this.sourceRole.repaint();
  }

  @Override
  public void setRelationName(String name) {
    this.associationName.setText(name);
    this.associationName.repaint();
  }

  @Override
  public void setSourceCardinalite(String cardinalite) {
    this.sourceCardinalite.setText(cardinalite);
    this.sourceCardinalite.repaint();
  }

  @Override
  public void setDestinationCardinalite(String cardinalite) {
    this.destinationCardinalite.setText(cardinalite);
    this.destinationCardinalite.repaint();
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
    // Ajoute les informations dans les labels
    this.setInformations();
  }

}
