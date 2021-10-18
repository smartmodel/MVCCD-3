package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDEntityShapeEditAction extends AbstractAction {

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
    this.shape.setZoneEnTeteContent();
    this.shape.setZoneProprietesContent();
    this.shape.repaint();
  }

  private void edit() {
    final MVCCDManager manager = MVCCDManager.instance();
    final MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
    mcdEntityEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getEntity());
  }

  private void create() {
    final MVCCDManager manager = MVCCDManager.instance();
    final MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
    final MCDEntity entity = (MCDEntity) mcdEntityEditingTreat.treatNew(manager.getMvccdWindow(), manager.getProject().getMCDContModels().getEntities());

    this.shape.setEntity(entity);
  }

}
