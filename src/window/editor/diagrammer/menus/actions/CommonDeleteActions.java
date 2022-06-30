package window.editor.diagrammer.menus.actions;

import diagram.Diagram;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import main.MVCCDElement;
import main.MVCCDManager;
import md.MDElement;
import window.editor.diagrammer.drawpanel.DrawPanel;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.interfaces.UMLPackageIntegrableShapes;
import window.editor.diagrammer.elements.shapes.UMLPackage;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;
import window.editor.diagrammer.services.DiagrammerService;

public abstract class CommonDeleteActions extends AbstractAction {

  private final DrawPanel drawPanel = DiagrammerService.getDrawPanel();
  private final MVCCDManager instanceMVCCDManager = MVCCDManager.instance();


  protected CommonDeleteActions(String name, Icon icon) {
    super(name, icon);
  }


  protected void deleteRelation(RelationShape shape) {
    shape.deleteLabels();
    drawPanel.deleteShape(shape);
  }

  protected void deleteGraphically(IShape shape) {
    drawPanel.deleteShape(shape);

    drawPanel.getRelationShapes().forEach(e -> {
      if (e.getSource().equals(shape) || e.getDestination().equals(shape)) {
        drawPanel.deleteShape(e);
        e.deleteLabels();
      }
    });

    if (shape instanceof UMLPackage) {
      ((UMLPackage) shape).getTapisElements().forEach(e -> drawPanel.deleteShape((IShape) e));
    }

    List<IShape> allShapes = drawPanel.getShapes();
    Diagram currentDiagram = instanceMVCCDManager.getCurrentDiagram();
    List<IShape> shapeListCurrentDiagram = currentDiagram.getShapes();

    shapeListCurrentDiagram.clear();
    shapeListCurrentDiagram.addAll(allShapes);
  }

  protected void deleteObject(ClassShape shape) {
    Diagram currentDiagram = instanceMVCCDManager.getCurrentDiagram();
    List<IShape> shapeListCurrentDiagram = currentDiagram.getShapes();

    drawPanel.getShapes().forEach(e -> {
      if (e instanceof UMLPackage && ((UMLPackage) e).getParentTableName()
          .equals(shape.getName())) {
        drawPanel.deleteShape(e);
        shapeListCurrentDiagram.remove(e);

        ((UMLPackage) e).getTapisElements().forEach(ee -> {
          drawPanel.deleteShape((IShape) ee);
          shapeListCurrentDiagram.remove(ee);
        });
      }
    });

    instanceMVCCDManager.removeMVCCDElementInRepository(shape.getRelatedRepositoryElement(),
        shape.getRelatedRepositoryElement().getParent());
  }


  protected void deleteObjectAndClones(ClassShape shape) {
    MDElement mdElement = shape.getRelatedRepositoryElement();
    MVCCDElement nodeTables = mdElement.getParent();
    MVCCDElement nodeMPDR = nodeTables.getParent();
    MVCCDElement nodeDiagrammes = nodeMPDR.getChilds().get(2);
    ArrayList<MVCCDElement> diagramElements = nodeDiagrammes.getChilds();
    List<IShape> shapesToDelete = new ArrayList<>();

    for (MVCCDElement currentDiagram : diagramElements) {
      shapesToDelete.clear();
      List<IShape> shapes = ((Diagram) currentDiagram).getShapes();

      for (IShape currentShape : shapes) {
        if (currentShape instanceof UMLPackage && ((UMLPackage) currentShape).getParentTableName()
            .equals(shape.getName())) {
          shapesToDelete.add(currentShape);

          for (UMLPackageIntegrableShapes mvccdElement : ((UMLPackage) currentShape).getTapisElements()) {
            shapesToDelete.add((IShape) mvccdElement);
          }
        } else if (currentShape.equals(shape)) {
          shapesToDelete.add(currentShape);
          for (RelationShape relationShape : ((Diagram) currentDiagram).getRelationShapes()) {
            if (relationShape.getSource().equals(currentShape) || relationShape.getDestination()
                .equals(currentShape)) {
              shapesToDelete.add(relationShape);
            }
          }
        }
      }
      shapes.removeAll(shapesToDelete);
    }

    deleteGraphically(shape);
    deleteObject(shape);
  }
}
