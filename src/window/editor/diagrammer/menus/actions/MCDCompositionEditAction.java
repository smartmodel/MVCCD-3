package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import window.editor.diagrammer.elements.shapes.relations.mcd.MCDCompositionShape;

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