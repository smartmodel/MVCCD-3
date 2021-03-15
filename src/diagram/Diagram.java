package diagram;

import project.ProjectElement;

/**
 * Tous les diagrammes concrets sont des descendants de Diagram.
 */
public abstract class Diagram extends ProjectElement {

    private static final long serialVersionUID = 1000;


    public Diagram(ProjectElement parent) {
        super(parent);
    }

    public Diagram(ProjectElement parent, int id) {
        super(parent, id);
    }

    public Diagram(ProjectElement parent, String name) {
        super(parent, name);
    }


}
