package mpdr;

import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

public class MPDRContDiagrams extends MPDRElement implements IMPathOnlyRepositoryTree {

    private static final long serialVersionUID = 1000;

    public MPDRContDiagrams(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MPDRContDiagrams(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MPDRContDiagrams(ProjectElement parent) {
        super(parent);
    }
}
