/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère l'action d'éditer une composition MCD
 * via le menu contextuel affiché lors d'un clic droit.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.menus.actions;

import window.editor.diagrammer.elements.shapes.relations.mcd.MCDCompositionShape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class MCDCompositionEditAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = -5907897825173410313L;
  private final MCDCompositionShape shape;

  public MCDCompositionEditAction(String name, Icon icon, MCDCompositionShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.shape.getMCDComposition() != null) {
      this.edit();
    } else {
      this.create();
    }
  }

  private void edit() {

  }

  private void create() {

  }

}