package mdr;

import main.MVCCDElement;
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

        /*
        if (StringUtils.isNotEmpty(getName())){
            return StereotypeService.getUMLName(getDefaultStereotype()) + " " + getName();
        } else {
            return "Nom à affecter" ;
        }

         */

        /*
        try {
            return StereotypeService.getUMLName(getDefaultStereotype()) + " " + getName();
        } catch (Exception e){
            if (getName() != null){
                return "Err. stéréo " + getName() ;
            }
            return "Nom (et nature à affecter)";
        }
        */



        /*
        String resultat = "";
        if (getDefaultStereotype() != null ){
            if (StringUtils.isNotEmpty(StereotypeService.getUMLName(getDefaultStereotype()))){
                resultat += StereotypeService.getUMLName(getDefaultStereotype()) ;
            } else {
                resultat = "Err. stéréo UML";
            }
        } else {
            resultat = "Err. stéréo défault ";
        }
        if (StringUtils.isNotEmpty(getName())){
            resultat += getName();
        } else {
            resultat += "Err. name ";
        }
        return resultat;

         */

        //TODO-0 getDefaultStereotype() provoque une erreur ....
        //Trace.println(this.getName() + "   " + this.getClass().getName());
        //Trace.println(getDefaultStereotype().getName());
        //return getDefaultStereotype() + " " + getName();
        return getName();
    }
}
