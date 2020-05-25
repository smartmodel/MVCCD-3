package diagram;

import project.ProjectElement;

public abstract class MDDiagram extends Diagram {

    private static final long serialVersionUID = 1000;

    public MDDiagram(ProjectElement parent) {
        super(parent);
    }

    public MDDiagram(ProjectElement parent, String name) {
        super(parent, name);
    }
}
