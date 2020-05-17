package mcd;

import project.ProjectElement;
import project.ProjectService;

public class MCDContConstraints extends MCDElement {

    private static final long serialVersionUID = 1000;

    public MCDContConstraints(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MCDContConstraints(ProjectElement parent) {
        super(parent);
    }

    @Override
    public String getNameTree() {
        return null;
    }


    public static MCDContConstraints getMCDContConstraintsByNamePath(int pathMode, String namePath) {
        for (MCDElement mcdElement : ProjectService.getAllMCDElementsByNamePath(pathMode, namePath)) {
            if (mcdElement instanceof MCDContConstraints) {
                return (MCDContConstraints) mcdElement;
            }
        }
        return null;
    }
}
