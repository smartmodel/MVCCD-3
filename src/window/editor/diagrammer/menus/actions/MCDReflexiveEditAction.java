package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import mcd.MCDAssociation;
import repository.editingTreat.mcd.MCDAssociationEditingTreat;
import repository.editingTreat.mcd.MCDLinkEditingTreat;
import window.editor.diagrammer.elements.shapes.relations.MCDReflexiveShape;

public class MCDReflexiveEditAction extends AbstractAction {

  private MCDReflexiveShape shape;

  public MCDReflexiveEditAction(String name, Icon icon, MCDReflexiveShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.shape.getAssociation() != null){
      this.edit();
    } else{
      this.create();
    }
  }

  private void edit(){
    MVCCDManager manager = MVCCDManager.instance();
    MCDLinkEditingTreat mcdLinkEditingTreat = new MCDLinkEditingTreat();
    mcdLinkEditingTreat.treatUpdate(manager.getMvccdWindow(),this.shape.getAssociation());
  }

  private void create(){
    MVCCDManager manager = MVCCDManager.instance();
    MCDAssociationEditingTreat mcdAssociationEditingTreat = new MCDAssociationEditingTreat();
    MCDAssociation association = mcdAssociationEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getRelations());
    this.shape.setAssociation(association);
  }
}
