package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import mcd.MCDAssociation;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDAssociationShape extends RelationShape {

  MCDAssociation association;

  public MCDAssociationShape(MCDEntityShape source, MCDEntityShape destination, boolean isReflexive) {
    super(source, destination);
    if (isReflexive) {
      this.pointsAncrage.clear(); // On supprime les points d'ancrage ajoutés par la classe parente
      RelationPointAncrageShape p1 = new RelationPointAncrageShape((int) source.getBounds().getMaxX() - 50, (int) source.getBounds().getMinY());
      RelationPointAncrageShape p2 = new RelationPointAncrageShape((int) source.getBounds().getMaxX() - 50, (int) source.getBounds().getMinY() - 50);
      RelationPointAncrageShape p3 = new RelationPointAncrageShape((int) source.getBounds().getMaxX() + 50, (int) source.getBounds().getMinY() - 50);
      RelationPointAncrageShape p4 = new RelationPointAncrageShape((int) source.getBounds().getMaxX() + 50, (int) source.getBounds().getMinY() + 50);
      RelationPointAncrageShape p5 = new RelationPointAncrageShape((int) source.getBounds().getMaxX(), (int) source.getBounds().getMinY() + 50);
      this.addPointAncrage(p1, 0);
      this.addPointAncrage(p2, 1);
      this.addPointAncrage(p3, 2);
      this.addPointAncrage(p4, 3);
      this.addPointAncrage(p5, 4);
      this.isReflexive = true;
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
  public void draw(Graphics2D graphics2D) {
    this.drawSegments(graphics2D);
  }

  @Override
  public void setInformations() {
    if (this.association != null) {
      // Nom d'association
      if (!this.association.getName().isEmpty()) {
        this.setRelationName(this.association.getName());
        this.associationName.setVisible(true);
      } else {
        this.associationName.setVisible(false);
      }
      // Rôle source
      if (!this.association.getFrom().getName().isEmpty()) {
        this.setSourceRole(this.association.getFrom().getName());
        this.sourceRole.setVisible(true);
      } else {
        this.sourceRole.setVisible(false);
      }
      // Rôle destination
      if (!this.association.getTo().getName().isEmpty()) {
        this.setDestinationRole(this.association.getTo().getName());
        this.destinationRole.setVisible(true);
      } else {
        this.destinationRole.setVisible(false);
      }
      // Cardinalité destination
      if (!this.association.getTo().getMultiStr().isEmpty()) {
        this.setDestinationCardinalite(this.association.getTo().getMultiStr());
        this.destinationCardinalite.setVisible(true);
      } else {
        this.destinationCardinalite.setVisible(false);
      }
      // Cardinalité source
      if (!this.association.getFrom().getMultiStr().isEmpty()) {
        this.setSourceCardinalite(this.association.getFrom().getMultiStr());
        this.sourceCardinalite.setVisible(true);
      } else {
        this.sourceCardinalite.setVisible(false);
      }

    }
  }

  public MCDAssociation getAssociation() {
    return association;
  }

  public void setAssociation(MCDAssociation association) {
    this.association = association;
    this.setInformations();
  }

}
