package diagram.mldr;

import diagram.MDDiagram;
import project.ProjectElement;

public class MLDRDiagram extends MDDiagram {

    private static final long serialVersionUID = 1000;

    public MLDRDiagram(ProjectElement parent) {
        super(parent);
    }

    public MLDRDiagram(ProjectElement parent, String name) {
        super(parent, name);
    }
}
