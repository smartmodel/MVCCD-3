package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

public class PointAncrageDeleteAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;
  private final RelationAnchorPointShape pointAncrage;
  private final RelationShape relation;

  public PointAncrageDeleteAction(String name, Icon icon, RelationAnchorPointShape shape, RelationShape relation) {
    super(name, icon);
    this.pointAncrage = shape;
    this.relation = relation;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.delete();
  }

  private void delete() {
    // Suppression possible uniquement s'il y a plus de 2 points d'ancrage dans l'association
    if (this.relation.getAnchorPoints().size() > 2) {
      this.relation.deleteAnchorPoint(this.pointAncrage);
      DiagrammerService.getDrawPanel().repaint();
    }
  }
}