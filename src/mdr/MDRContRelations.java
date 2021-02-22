package mdr;

import main.MVCCDElement;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRContRelations extends MDRElement {

    private static final long serialVersionUID = 1000;

    public MDRContRelations(ProjectElement parent, String name) {
        super(parent, name);
    }

    public ArrayList<MDRRelation> getMDRRelations() {
        ArrayList<MDRRelation> resultat = new ArrayList<MDRRelation>();
        for (MVCCDElement mvccdElement : getChilds()) {
            resultat.add((MDRRelation) mvccdElement);
        }
        return resultat;
    }

}
