package diagram.mcd;

import diagram.MDDiagram;
import project.ProjectElement;

public class MCDDiagram extends MDDiagram {

    private static final long serialVersionUID = 1000;

    public MCDDiagram(ProjectElement parent) {
        super(parent);
    }

    public MCDDiagram(ProjectElement parent, String name) {
        super(parent, name);
    }
}
