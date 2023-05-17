/***
 * Cette classe peut être utilisée en l'état actuel. Elle gère l'action d'éditer une entité MCD
 * via le menu contextuel affiché lors d'un clic droit.
 * Auteur : Melvyn Vogelsang
 * Dernière mise à jour : 17.05.2023
 */

package window.editor.diagrammer.menus.actions;

import main.MVCCDManager;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class MCDEntityShapeEditAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;
  private final MCDEntityShape shape;

  public MCDEntityShapeEditAction(String name, Icon icon, MCDEntityShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (this.shape.getEntity() != null) {
      this.edit();
    } else {
      this.create();
    }

    // Affiche les diverses informations de l'entité
    this.shape.refreshInformations();
  }

  private void edit() {
    MVCCDManager manager = MVCCDManager.instance();
    MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
    mcdEntityEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getEntity());
  }

  private void create() {
    MVCCDManager manager = MVCCDManager.instance();
    MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
    MCDEntity entity = (MCDEntity) mcdEntityEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getEntities());

    this.shape.setEntity(entity);
  }

}