package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public class MCDContConstraints extends MCDElement implements IMPathOnlyRepositoryTree {

    private static final long serialVersionUID = 1000;

    public MCDContConstraints(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MCDContConstraints(ProjectElement parent) {
        super(parent);
    }


    public ArrayList<MCDConstraint> getMCDConstraints(){
        ArrayList<MCDConstraint> resultat = new ArrayList<MCDConstraint>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MCDConstraint) mvccdElement);
        }
        return resultat;
    }


    public ArrayList<MCDNID> getMCDNIDs(){
        ArrayList<MCDNID> resultat = new ArrayList<MCDNID>();
        for (MCDConstraint mcdConstraint: getMCDConstraints()){
            if ( mcdConstraint instanceof MCDNID) {
                resultat.add((MCDNID) mcdConstraint);
            }
        }
        return resultat;
    }

    public ArrayList<MCDUnique> getMCDUniques(){
        ArrayList<MCDUnique> resultat = new ArrayList<MCDUnique>();
        for (MCDConstraint mcdConstraint: getMCDConstraints()){
            if ( mcdConstraint instanceof MCDUnique) {
                resultat.add((MCDUnique) mcdConstraint);
            }
        }
        return resultat;
    }




    public ArrayList<MCDConstraint> getMCDConstraintsSortDefault(){
        ArrayList<MCDConstraint> mcdConstraintsSorted = new ArrayList<MCDConstraint>();
        for (MCDConstraint mcdConstraint : getMCDConstraints()){
            mcdConstraintsSorted.add(mcdConstraint);
        }
        Collections.sort(mcdConstraintsSorted, MCDConstraint::compareToDefault);
        return mcdConstraintsSorted;
    }



    // Surcharge pour l'affichage trié PFK - PK - FK
    public ArrayList<? extends  MVCCDElement> getChildsSortDefault() {
        return getMCDConstraintsSortDefault() ;
    }

}
