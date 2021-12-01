package diagram;

import console.ConsoleManager;
import main.MVCCDElement;
import project.Project;
import project.ProjectElement;
import window.editor.diagrammer.elements.interfaces.IShape;

import java.util.LinkedList;
import java.util.List;

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
}
