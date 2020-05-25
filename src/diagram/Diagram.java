package diagram;

import project.ProjectElement;

public abstract class Diagram extends ProjectElement {

    private static final long serialVersionUID = 1000;


    public Diagram(ProjectElement parent) {
        super(parent);
    }

    public Diagram(ProjectElement parent, String name) {
        super(parent, name);
    }

    @Override
    public String getNameTree() {
        return null;
    }
}
