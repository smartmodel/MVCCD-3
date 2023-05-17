/***
 * Cette classe peut être utilisée en l'état actuel. Elle permet de supprimer un point d'ancrage d'une relation
 * via le menu contextuel affiché lors d'un clic droit.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.relations.RelationAnchorPointShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

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