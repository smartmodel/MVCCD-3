/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère l'action d'de supprimer une entité MCD
 * via le menu contextuel affiché lors d'un clic droit.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.services.DiagrammerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class MCDEntityShapeDeleteAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;
  private MCDEntityShape shape;

  public MCDEntityShapeDeleteAction(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.delete();
  }

  private void delete() {
    DiagrammerService.getDrawPanel().deleteShape(this.shape);
  }
}