package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import mcd.MCDAssociation;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;

public class MCDAssociationEditAction extends AbstractAction {

  private final MCDAssociationShape shape;

  public MCDAssociationEditAction(String name, Icon icon, MCDAssociationShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.shape.getAssociation() != null) {
      this.edit();
    } else {
      this.create();
    }
    this.shape.repaint();
  }

  private void edit() {
    final MVCCDManager manager = MVCCDManager.instance();
    final MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    mcdAssociationEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getAssociation());
    this.shape.setInformations();
  }

  private void create() {
    final MVCCDManager manager = MVCCDManager.instance();
    final MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    final MCDAssociation association = mcdAssociationEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getRelations());
    this.shape.setAssociation(association);
  }
}
