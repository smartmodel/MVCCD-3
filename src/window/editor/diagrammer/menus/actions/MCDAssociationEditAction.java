package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import mcd.MCDAssociation;
import mcd.MCDAssociationNature;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDAssociationShape;

public class MCDAssociationEditAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;
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
  }

  private void edit() {
    MVCCDManager manager = MVCCDManager.instance();
    MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    mcdAssociationEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getMCDAssociation());
    this.shape.createLabelsAfterRelationShapeEdit();
  }

  private void create() {
    MVCCDManager manager = MVCCDManager.instance();
    MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();

    MCDEntityShape entityShapeSource = (MCDEntityShape) this.shape.getSource();
    MCDEntityShape entityShapeDestination = (MCDEntityShape) this.shape.getDestination();

    MCDEntity entitySource = entityShapeSource.getEntity();
    MCDEntity entityDestination = entityShapeDestination.getEntity();

    MCDAssociation association = mcdAssociationEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getRelations(), entitySource, entityDestination, MCDAssociationNature.NOID, false);

    this.shape.setMCDAssociation(association);
    this.shape.createLabelsAfterRelationShapeEdit();
  }
}