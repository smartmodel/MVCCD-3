package mdr;

import main.MVCCDElement;
import mdr.interfaces.IMDRParameter;
import mdr.services.MDRConstraintService;
import project.ProjectElement;
import stereotypes.StereotypeService;

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

    public ArrayList<MDRParameter> getMDRParametersSortDefault(){
        ArrayList<MDRParameter> resultat = getMDRParameters();
        Collections.sort(resultat, MDRParameter::compareToDefault) ;
        return resultat;
    }

    public ArrayList<? extends MVCCDElement> getChildsSortDefault() {
        //return getMDRColumnsSortDefault();
        return getMDRParametersSortDefault();
    }


    public int compareToDefault(MDRConstraint other) {
        return MDRConstraintService.compareToDefault(this, other);
    }


    //TODO-0 A retirer en ayant les icones
    public String toString(){
        try {
            return StereotypeService.getUMLName(getDefaultStereotype()) + " " + getName();
        } catch (Exception e){
            return "Nom et nature à affecter";
        }
    }
}
