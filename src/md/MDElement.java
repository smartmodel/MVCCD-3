package md;

import m.MElement;
import project.ProjectElement;

/**
 * Ancêtre de tous les éléments de modélisation des données.
 * Est un élément de projet qui est lui-même un élément du référentiel.
 */
public abstract class MDElement extends MElement {

    public MDElement(ProjectElement parent) {
        super(parent);
    }

    public MDElement(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}
