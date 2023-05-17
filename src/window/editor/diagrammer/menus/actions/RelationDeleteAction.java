/***
 * Cette classe peut être utilisée en l'état actuel. Elle permet de supprimer une relation
 * via le menu contextuel affiché lors d'un clic droit.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class RelationDeleteAction extends AbstractAction implements Serializable {

  private final RelationShape shape;
  private static final long serialVersionUID = 1000;

  public RelationDeleteAction(String name, Icon icon, RelationShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.delete();
  }

  private void delete() {
    this.shape.deleteLabels();
    DiagrammerService.getDrawPanel().deleteShape(this.shape);
  }
}
