package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import mcd.MCDGeneralization;
import repository.editingTreat.mcd.MCDGeneralizationEditingTreat;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDGeneralizationShape;

public class MCDGeneralizationEditAction extends AbstractAction implements Serializable {

  private static final long serialVersionUID = 1000;
  private final MCDGeneralizationShape shape;

  public MCDGeneralizationEditAction(String name, Icon icon, MCDGeneralizationShape shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.shape.getGeneralization() != null) {
      this.edit();
    } else {
      this.create();
    }
  }

  private void edit() {
    MVCCDManager manager = MVCCDManager.instance();
    MCDGeneralizationEditingTreat mcdGeneralizationEditingTreat = new MCDGeneralizationEditingTreat();
    mcdGeneralizationEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getGeneralization());
  }

  private void create() {
    MVCCDManager manager = MVCCDManager.instance();
    MCDGeneralizationEditingTreat mcdGeneralizationEditingTreat = new MCDGeneralizationEditingTreat();
    MCDGeneralization generalization = mcdGeneralizationEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getRelations());

    this.shape.setGeneralization(generalization);
  }
}