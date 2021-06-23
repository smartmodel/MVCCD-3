package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import mcd.MCDAssociation;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDAssociationShape extends RelationShape{

  MCDAssociation association;

  public MCDAssociationShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination);
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
  public void draw(Graphics2D graphics2D) {
    this.drawSegments(graphics2D);

  }

  @Override
  protected void setInformations() {
    if (this.association != null){
        this.setRelationName(this.association.getName());
        this.setSourceRole(this.association.getFrom().getName());
        this.setDestinationRole(this.association.getTo().getName());
        this.setDestinationCardinalite(this.association.getTo().getMultiStr());
        this.setSourceCardinalite(this.association.getFrom().getMultiStr());
    }
  }

  public void setAssociation(MCDAssociation association) {
    this.association = association;
    this.setInformations();
  }

  public MCDAssociation getAssociation() {
    return association;
  }
}
