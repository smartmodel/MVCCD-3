package mdr;

import main.MVCCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public abstract class MDRContConstraints extends MDRElement implements IMPathOnlyRepositoryTree {

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


    public ArrayList<MDRConstraint> getMDRConstraintsSortDefault(){
        ArrayList<MDRConstraint> mdrConstraintsSorted = new ArrayList<MDRConstraint>();
        for (MDRConstraint mdrConstraint : getMDRConstraints()){
            mdrConstraintsSorted.add(mdrConstraint);
        }

        Collections.sort(mdrConstraintsSorted, MDRConstraint::compareToDefault);
        return mdrConstraintsSorted;
    }



    // Surcharge pour l'affichage trié PFK - PK - FK
    public ArrayList<? extends  MVCCDElement> getChildsSortDefault() {
        ArrayList<MDRConstraint> mdrConstraints = getMDRConstraintsSortDefault();
        return mdrConstraints ;
    }


    public ArrayList<MDRCheck> getMDRChecks() {
        ArrayList<MDRCheck> resultat = new ArrayList<MDRCheck>();
        for (MDRConstraint mdrConstraint : getMDRConstraints()) {
            if (mdrConstraint instanceof MDRCheck) {
                resultat.add((MDRCheck) mdrConstraint);
            }
        }
        return resultat;
    }

}
