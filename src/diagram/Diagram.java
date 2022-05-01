package diagram;

import console.ViewLogsManager;
import console.WarningLevel;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import project.ProjectElement;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.mcd.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.MCDAssociationShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

/**
 * Tous les diagrammes concrets sont des descendants de Diagram.
 */
public abstract class Diagram extends ProjectElement {

  private static final long serialVersionUID = 1000;

  private List<IShape> shapes = new LinkedList<>();

  public Diagram(ProjectElement parent) {
    super(parent);
  }

  public Diagram(ProjectElement parent, int id) {
    super(parent, id);
  }

  public Diagram(ProjectElement parent, String name) {
    super(parent, name);
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getParentIdAsString() {
    ProjectElement p = (ProjectElement) this.getParent();
    return p.getIdProjectElementAsString();
  }

  public List<IShape> getShapes() {
    return this.shapes;
  }

  public void setShapes(List<IShape> shapes) {
    this.shapes = shapes;
  }

  public void addShape(IShape shape) {
      this.shapes.add(shape);
    ViewLogsManager.printMessage(shape.getClass().getSimpleName() + " ajoutée au diagramme " + this.getName(), WarningLevel.INFO);
  }

  public List<ClassShape> getClassShapes() {
    return this.shapes.stream().filter(shape -> shape instanceof ClassShape).map(s -> (ClassShape) s).collect(Collectors.toList());
  }

  public List<RelationShape> getRelationShapes() {
    return this.shapes.stream().filter(shape -> shape instanceof RelationShape).map(s -> (RelationShape) s).collect(Collectors.toList());
  }

  public MCDEntityShape getMCDEntityShapeByID(int id) {
    for (IShape shape : this.shapes) {
      if (shape instanceof MCDEntityShape) {
        MCDEntityShape mcdEntityShape = (MCDEntityShape) shape;
        if (mcdEntityShape.getId() == id) {
          return mcdEntityShape;
        }
      }
    }
    return null;
  }

  public List<MCDAssociationShape> getMCDAssociationShapes() {
    return this.shapes.stream().filter(shape -> shape instanceof MCDAssociationShape).map(s -> (MCDAssociationShape) s).collect(Collectors.toList());
  }

}