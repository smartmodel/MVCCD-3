package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.Color;
import java.io.Serializable;
import preferences.Preferences;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;

public class MCDNaturalIdentifierShape extends MCDDiamondRelationShape implements Serializable {

  private static final long serialVersionUID = 1660545571630623463L;

  public MCDNaturalIdentifierShape() {
  }

  public MCDNaturalIdentifierShape(SquaredShape source, IShape destination, boolean isReflexive) {
    super(source, destination, isReflexive);
  }

  @Override
  protected Color getDiamondBorderColor() {
    return Color.BLACK;
  }

  @Override
  protected Color getDiamondBackgroundColor() {
    return Color.WHITE;
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {

  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_NATURAL_IDENTIFIER_XML_TAG;
  }
}