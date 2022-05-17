package window.editor.diagrammer.elements.shapes.relations.mcd;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import preferences.Preferences;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

public class MCDLinkShape extends RelationShape {

  private static final long serialVersionUID = 6884638286599971900L;

  public MCDLinkShape(ClassShape source, RelationShape linkedRelationShape) {
    super(source, linkedRelationShape, false);

/*    // Création de l'objet du référentiel
    MCDLinkEditingTreat editingTreat = new MCDLinkEditingTreat();
    MVCCDManager manager = MVCCDManager.instance();
    this.relatedRepositoryElement = editingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getRelations(), (MCDEntity) source.getRelatedRepositoryElement(), (MCDAssociation) linkedRelationShape.getRelatedRepositoryElement(), false);
    System.out.println(this.relatedRepositoryElement);*/
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
    return Preferences.DIAGRAMMER_MCD_LINK_XML_TAG;
  }
}