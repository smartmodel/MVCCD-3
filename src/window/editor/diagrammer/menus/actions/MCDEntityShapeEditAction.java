package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDEntityEditingTreat;
import dialogOnglets.EntiteOngletsTreat;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;

public class MCDEntityShapeEditAction extends AbstractAction {

  private MCDEntityShape shape;

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
    shape.setZoneEnTeteContent();
    shape.setZoneProprietesContent();
    shape.repaint();
  }

  private void edit() {
    MVCCDManager manager = MVCCDManager.instance();
/*    MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
    mcdEntityEditingTreat.treatUpdate(manager.getMvccdWindow(), this.shape.getEntity());*/
    EntiteOngletsTreat entiteOngletsTreat = new EntiteOngletsTreat();
    entiteOngletsTreat.treatUpdateOnglets(manager.getMvccdWindow(), this.shape.getEntity());
  }

  private void create() {
    MVCCDManager manager = MVCCDManager.instance();
    MCDEntityEditingTreat mcdEntityEditingTreat = new MCDEntityEditingTreat();
    MCDEntity entity = (MCDEntity) mcdEntityEditingTreat.treatNew(manager.getMvccdWindow(),
                                                                  manager.getProject()
                                                                         .getMCDContModels()
                                                                         .getEntities());
    this.shape.setEntity(entity);
  }

}
