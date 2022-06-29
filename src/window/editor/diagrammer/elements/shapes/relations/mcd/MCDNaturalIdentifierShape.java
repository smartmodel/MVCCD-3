package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import preferences.Preferences;
import preferences.PreferencesManager;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.labels.LabelType;

public class MCDNaturalIdentifierShape extends MCDDiamondRelationShape implements Serializable {

  private static final long serialVersionUID = 1660545571630623463L;

  public MCDNaturalIdentifierShape() {
  }

  public MCDNaturalIdentifierShape(SquaredShape source, IShape destination, boolean isReflexive) {
    super(source, destination, isReflexive);
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {
    boolean isUMLNotation = PreferencesManager.instance().getApplicationPref().isDIAGRAMMER_UML_NOTATION();
    if (isUMLNotation) {
      this.drawDiamond(graphics2D);
    } else {
      this.addLabelWhenNotUMLNotation();
      this.displayLabels();
    }
  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {

  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_NATURAL_IDENTIFIER_XML_TAG;
  }

  private void addLabelWhenNotUMLNotation() {
    int middleAnchorPointIndex = this.getIndexOfMiddleAnchorPoint();
    RelationAnchorPointShape anchorPoint = this.anchorPoints.get(middleAnchorPointIndex);

    this.createLabel(anchorPoint, "CID", LabelType.ASSOCIATION_NAME, 0, 0);

  }

  @Override
  protected Color getDiamondBorderColor() {
    return Color.BLACK;
  }

  @Override
  protected Color getDiamondBackgroundColor() {
    return Color.WHITE;
  }
}