package mdr;

import main.MVCCDElement;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRContConstraints extends MDRElement {

    private static final long serialVersionUID = 1000;

    public MDRContConstraints(ProjectElement parent, String name) {
        super(parent, name);
    }

    public ArrayList<MDRConstraint> getMDRConstraints() {
        ArrayList<MDRConstraint> resultat = new ArrayList<MDRConstraint>();
        for (MVCCDElement mvccdElement : getChilds()) {
            resultat.add((MDRConstraint) mvccdElement);
        }
        return resultat;
    }

}
