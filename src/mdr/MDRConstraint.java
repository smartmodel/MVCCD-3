package mdr;

import main.MVCCDElement;
import mcd.MCDConstraint;
import mcd.services.MCDConstraintService;
import mdr.interfaces.IMDRParameter;
import mdr.services.MDRConstraintService;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public abstract class MDRConstraint extends MDROperation{

    private  static final long serialVersionUID = 1000;

    public MDRConstraint(ProjectElement parent) {
        super(parent);
    }

    public MDRConstraint(ProjectElement parent, int id) {
        super(parent, id);
    }


    public MDRTable getMDRTableAccueil(){
        return (MDRTable) getParent().getParent();
    }



    public ArrayList<MDRColumn> getMDRColumns(){
        ArrayList<MDRColumn> resultat = new ArrayList<MDRColumn>();
        for (IMDRParameter imdrParameter : getTargets()){
            if (imdrParameter instanceof MDRColumn) {
                resultat.add((MDRColumn) imdrParameter);
            }
        }
        return resultat;
    }

    public ArrayList<MDRColumn> getMDRColumnsSortDefault(){
        ArrayList<MDRColumn> resultat = getMDRColumns();
        Collections.sort(resultat, MDRColumn::compareToDefault) ;
        return resultat;
    }

    public ArrayList<? extends MVCCDElement> getChildsSortDefault() {
        return getMDRColumnsSortDefault();
    }


    public int compareToDefault(MDRConstraint other) {
        return MDRConstraintService.compareToDefault(this, other);
    }

}
