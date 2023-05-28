package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.io.Serializable;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.SquaredShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

public class MCDAnchorShape extends RelationShape implements Serializable {

  private static final long serialVersionUID = 6884638286599971900L;

  public MCDAnchorShape(SquaredShape source, RelationShape linkedShape) {
    super(source, linkedShape, false);
  }

  public MCDAnchorShape(SquaredShape source, SquaredShape linkedShape) {
    super(source, linkedShape, false);
  }

  @Override
  public void defineLineAspect(Graphics2D graphics2D) {
    float[] dash = {2f, 0f, 2f};
    BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
    graphics2D.setStroke(dashedStroke);
  }

  @Override
  public void doDraw(Graphics2D graphics2D) {

  }

  @Override
  public void createLabelsAfterRelationShapeEdit() {

  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_ANCHOR_XML_TAG;
  }
}