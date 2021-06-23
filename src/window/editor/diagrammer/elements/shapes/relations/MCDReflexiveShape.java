package window.editor.diagrammer.elements.shapes.relations;

import java.awt.Graphics2D;
import mcd.MCDAssociation;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDReflexiveShape extends RelationShape {

  MCDAssociation association = null;

  public MCDReflexiveShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination);
    this.pointsAncrage.clear();
    RelationPointAncrageShape p1 = new RelationPointAncrageShape(
        (int) source.getBounds().getMaxX() - 50, (int) source.getBounds().getMinY());
    RelationPointAncrageShape p2 = new RelationPointAncrageShape(
        (int) source.getBounds().getMaxX() - 50, (int) source.getBounds().getMinY() - 50);
    RelationPointAncrageShape p3 = new RelationPointAncrageShape(
        (int) source.getBounds().getMaxX() + 50, (int) source.getBounds().getMinY() - 50);
    RelationPointAncrageShape p4 = new RelationPointAncrageShape(
        (int) source.getBounds().getMaxX() + 50, (int) source.getBounds().getMinY() + 50);
    RelationPointAncrageShape p5 = new RelationPointAncrageShape((int) source.getBounds().getMaxX(),
                                                                 (int) source.getBounds()
                                                                             .getMinY() + 50);
    this.addPointAncrage(p1, 0);
    this.addPointAncrage(p2, 1);
    this.addPointAncrage(p3, 2);
    this.addPointAncrage(p4, 3);
    this.addPointAncrage(p5, 4);

  }

  @Override
  public void setDestinationRole(String role) {
  }

  @Override
  public void setSourceRole(String role) {
  }

  @Override
  public void setRelationName(String name) {
  }

  @Override
  public void setSourceCardinalite(String cardinalite) {
  }

  @Override
  public void setDestinationCardinalite(String cardinalite) {
  }

  @Override
  public void draw(Graphics2D graphics2D) {
    this.drawSegments(graphics2D);
    if (this.association != null) {
      this.setName(this.association.getName());
      this.setSourceRole(this.association.getFrom().getName());
      this.setDestinationRole(this.association.getTo().getName());
    }
  }

  @Override
  public void setInformations() {
  }

  public MCDAssociation getAssociation() {
    return association;
  }

  public void setAssociation(MCDAssociation association) {
    this.association = association;
  }
}
