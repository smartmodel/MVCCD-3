package window.editor.diagrammer.menus.actions;

import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDManager;
import window.editor.diagrammer.drawpanel.DrawPanel;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.UMLPackage;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

public abstract class DeleteActions extends AbstractAction {

  private static final DrawPanel drawPanel = DiagrammerService.getDrawPanel();
  private static final MVCCDManager instanceMVCCDManager = MVCCDManager.instance();


  protected DeleteActions(String name, Icon icon) {
    super(name, icon);
  }


  protected void deleteRelation(RelationShape shape) {
    shape.deleteLabels();
    drawPanel.deleteShape(shape);
  }

  protected static void deleteGraphically(IShape shape) {
    drawPanel.deleteShape(shape);

    drawPanel.getRelationShapes().forEach(e -> {
      if (e.getSource().equals(shape) || e.getDestination().equals(shape)) {
        drawPanel.deleteShape(e);
        e.deleteLabels();
      }
    });

    List<IShape> allShapes = drawPanel.getShapes();

    instanceMVCCDManager.getCurrentDiagram().getShapes().clear();
    instanceMVCCDManager.getCurrentDiagram().getShapes().addAll(allShapes);
  }

  protected static void deleteObject(ClassShape shape) {
    drawPanel.getShapes().forEach(e -> {
      if (e instanceof UMLPackage && ((UMLPackage) e).getParentTableName()
          .equals(shape.getName())) {
        drawPanel.deleteShape(e);

        ((UMLPackage) e).getTapisElements().forEach(
            ee -> drawPanel.deleteShape((IShape) ee)
        );
      }
    });

    instanceMVCCDManager.removeMVCCDElementInRepository(shape.getRelatedRepositoryElement(),
        shape.getRelatedRepositoryElement().getParent());
  }
}
