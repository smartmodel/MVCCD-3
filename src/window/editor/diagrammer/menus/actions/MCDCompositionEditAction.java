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
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.MCDCompositionShape;

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
    MVCCDManager manager = MVCCDManager.instance();
    MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    mcdAssociationEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getMCDComposition());
    this.shape.createLabelsAfterRelationShapeEdit();
  }

  private void create() {
    MVCCDManager manager = MVCCDManager.instance();

    MCDEntityShape entityShapeSource = (MCDEntityShape) this.shape.getSource();
    MCDEntityShape entityShapeDestination = (MCDEntityShape) this.shape.getDestination();

    MCDEntity entitySource = entityShapeSource.getEntity();
    MCDEntity entityDestination = entityShapeDestination.getEntity();

    MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    MCDAssociation association = mcdAssociationEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getRelations(), entitySource, entityDestination, MCDAssociationNature.IDCOMP, false);

    this.shape.setMCDComposition(association);
    this.shape.createLabelsAfterRelationShapeEdit();
  }

}