package mldr;

import mcd.MCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

public class MLDRContDiagrams extends MLDRElement implements IMPathOnlyRepositoryTree {

    private static final long serialVersionUID = 1000;

    public MLDRContDiagrams(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MLDRContDiagrams(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MLDRContDiagrams(ProjectElement parent) {
        super(parent);
    }
}
