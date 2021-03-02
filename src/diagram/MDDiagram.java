package diagram;

import project.ProjectElement;

/**
 * Tous les diagrammes de données concrets sont des descendants de MDDiagram.
 */
public abstract class MDDiagram extends Diagram {

    private static final long serialVersionUID = 1000;

    public MDDiagram(ProjectElement parent) {
        super(parent);
    }

    public MDDiagram(ProjectElement parent, String name) {
        super(parent, name);
    }
}
