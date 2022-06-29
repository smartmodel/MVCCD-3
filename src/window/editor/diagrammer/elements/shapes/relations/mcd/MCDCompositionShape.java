package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.Color;
import java.io.Serializable;
import mcd.MCDAssociation;
import md.MDElement;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;

public class MCDCompositionShape extends MCDDiamondRelationShape implements Serializable {

  private static final long serialVersionUID = 1660545571630623463L;

  public MCDCompositionShape(MCDEntityShape source, MCDEntityShape destination) {
    super(source, destination, false);
  }

  public MCDCompositionShape(MDElement relatedRepositoryComposition, MCDEntityShape source, MCDEntityShape destination) {
    this(source, destination);
    this.relatedRepositoryElement = relatedRepositoryComposition;
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {

  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_COMPOSITION_XML_TAG;
  }

  public MDElement getMCDComposition() {
    return this.relatedRepositoryElement;
  }

  public void setMCDComposition(MCDAssociation association) {
    this.relatedRepositoryElement = association;
  }

  @Override
  protected Color getDiamondBorderColor() {
    return Color.BLACK;
  }

  @Override
  protected Color getDiamondBackgroundColor() {
    return Color.BLACK;
  }
}