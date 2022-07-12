package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.io.Serializable;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.LinkShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

public class MCDLinkShape extends LinkShape implements Serializable {

  private static final long serialVersionUID = 6884638286599971900L;

  public MCDLinkShape(ClassShape source, RelationShape linkedRelationShape) {
    super(source, linkedRelationShape);
  }

  private void addAnchorPointToDestinationRelationShape() {

  }

  @Override
  public String getXmlTagName() {
    return Preferences.DIAGRAMMER_MCD_LINK_XML_TAG;
  }
}