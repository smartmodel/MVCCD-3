package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.relations.RelationPointAncrageShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

public class PointAncrageDeleteAction extends AbstractAction {

  RelationPointAncrageShape pointAncrage;
  RelationShape relation;

  public PointAncrageDeleteAction(String name, Icon icon, RelationPointAncrageShape shape,
                                  RelationShape relation) {
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
    if (relation.getPointsAncrage().size() > 2) {
      this.relation.deletePointAncrage(this.pointAncrage);
      DiagrammerService.drawPanel.repaint();
    }
  }
}
