package diagram;

import console.ConsoleManager;
import main.MVCCDElement;
import project.Project;
import project.ProjectElement;
import window.editor.diagrammer.elements.interfaces.IShape;
import window.editor.diagrammer.elements.shapes.classes.ClassShape;
import window.editor.diagrammer.elements.shapes.classes.MCDEntityShape;
import window.editor.diagrammer.elements.shapes.relations.RelationShape;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getParentIdAsString() {
        ProjectElement p = (ProjectElement) this.getParent();
        return p.getIdProjectElementAsString();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<IShape> getShapes() {
        return shapes;
    }

    public void addShape(IShape shape){
        shapes.add(shape);
        ConsoleManager.printMessage(shape.getClass().getSimpleName() + " ajoutée au diagramme " + getName());
    }

    public void setShapes(List<IShape> shapes) {
        this.shapes = shapes;
    }

    public List<ClassShape> getClassShapes(){
        return shapes.stream().filter(shape -> shape instanceof ClassShape).map(s -> (ClassShape) s).collect(Collectors.toList());
    }

    public List<RelationShape> getRelationShapes(){
        return shapes.stream().filter(shape -> shape instanceof RelationShape).map(s -> (RelationShape) s).collect(Collectors.toList());
    }

    public MCDEntityShape getMCDEntityShapeByID(int id){
        for (IShape shape : shapes){
            if (shape instanceof MCDEntityShape){
                MCDEntityShape mcdEntityShape = (MCDEntityShape) shape;
                if (mcdEntityShape.getId() == id){
                    return mcdEntityShape;
                }
            }
        }
        return null;
    }

}
