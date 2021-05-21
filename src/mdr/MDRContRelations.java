package mdr;

import main.MVCCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRContRelations extends MDRElement implements IMPathOnlyRepositoryTree {

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

    public ArrayList<MDRRelationFK> getMDRRelationsFK() {
        ArrayList<MDRRelationFK> resultat = new ArrayList<MDRRelationFK>();
        for(MVCCDElement mvccdElement : getChilds()) {
            if(mvccdElement instanceof MDRRelationFK){
                resultat.add((MDRRelationFK) mvccdElement);
            }
        }
        return resultat;
    }

}
