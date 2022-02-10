package window.editor.diagrammer.menus.actions;

import main.MVCCDManager;
import mcd.MCDAssociation;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MCDAssociationEditAction extends AbstractAction {

  private final MCDAssociationShape shape;

  public MCDAssociationEditAction(String name, Icon icon, MCDAssociationShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.shape.getMCDAssociation() != null) {
      this.edit();
    } else {
      this.create();
    }
    this.shape.repaint();
  }

  private void edit() {
    final MVCCDManager manager = MVCCDManager.instance();
    final MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    mcdAssociationEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getMCDAssociation());
    shape.createLabelsAfterRelationShapeEdit();
  }

  private void create() {
    final MVCCDManager manager = MVCCDManager.instance();
    final MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    final MCDAssociation association = mcdAssociationEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getRelations());
    this.shape.setMCDAssociation(association);
    shape.createLabelsAfterRelationShapeEdit();
  }
}
