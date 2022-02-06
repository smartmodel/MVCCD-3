package mdr;

import constraints.Constraint;
import exceptions.CodeApplException;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MDRCheck extends MDRConstraint {

    private static final long serialVersionUID = 1000;

    public MDRCheck(ProjectElement parent) {
        super(parent);
    }

    public MDRCheck(ProjectElement parent, int id) {
        super(parent, id);
    }

    public Stereotype getDefaultStereotype() {
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        return stereotypes.getStereotypeByLienProg(MDRCheck.class.getName(),
                preferences.STEREOTYPE_CHECK_LIENPROG);
    }


    @Override
    public ArrayList<Stereotype> getStereotypes() {
        // Les stéréotypes doivent être ajoutés en respectant l'ordre d'affichage
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(getDefaultStereotype());
        return resultat;
    }


    @Override
    public ArrayList<Constraint> getConstraints() {
        return new ArrayList<Constraint>();
    }

    public MDRParameter getMDRParameter(){
         ArrayList<MDRParameter> mdrParameters = getMDRParameters();
         if (mdrParameters.size() > 1 ){
             throw new CodeApplException("La contrainte de Check " + getName() + " comporte plus d'un seul parammètre (l'expression)");
         } else  if (mdrParameters.size() == 1 ){
             return mdrParameters.get(0);
         } else {
             return null;
         }
    }
}

