package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.Icon;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.UMLPackage;
import window.editor.diagrammer.services.DiagrammerService;

public class UMLPackageDeleteAction extends DeleteActions implements Serializable {

  private UMLPackage shape;

  public UMLPackageDeleteAction(String name, Icon icon, UMLPackage shape) {
    super(name, icon);
    this.shape = shape;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    delete();
  }

  private void delete() {
    shape.getTapisElements().forEach(
        e -> DiagrammerService.getDrawPanel().deleteShape((IShape) e)
    );

    super.deleteGraphically(shape);
  }
}
