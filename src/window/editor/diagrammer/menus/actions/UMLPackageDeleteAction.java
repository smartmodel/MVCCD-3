package window.editor.diagrammer.menus.actions;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.UMLPackage;
import window.editor.diagrammer.services.DiagrammerService;

public class UMLPackageDeleteAction extends AbstractAction implements Serializable {

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
    DiagrammerService.getDrawPanel().deleteShape(shape);
    shape.getTapisElements().forEach(
        e -> DiagrammerService.getDrawPanel().deleteShape((IShape) e)
    );

    DiagrammerService.getDrawPanel().getRelationShapes().forEach(e -> {
      if (e.getSource().equals(shape) || e.getDestination().equals(shape)) {
        DiagrammerService.getDrawPanel().deleteShape(e);
        e.getLabels().forEach(l -> DiagrammerService.getDrawPanel().deleteShape(l));
      }
    });

    List<IShape> allShapes = DiagrammerService.getDrawPanel().getShapes();
    MVCCDManager.instance().getCurrentDiagram().getShapes().clear();
    MVCCDManager.instance().getCurrentDiagram().getShapes().addAll(allShapes);
  }
}
