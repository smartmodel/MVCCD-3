package m;

import project.ProjectElement;

/**
 * Ancêtre de tous les éléments de modélisation.
 * Est un élément de projet qui est lui-même un élément du référentiel.
 */
public abstract class MElement extends ProjectElement {

    private static final long serialVersionUID = 1000;

    public MElement(ProjectElement parent) {
        super(parent);
    }

    public MElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}
