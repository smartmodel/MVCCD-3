package mcd;

import constraints.Constraint;
import constraints.ConstraintService;
import constraints.Constraints;
import constraints.ConstraintsManager;
import mdr.MDRUniqueNature;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;

import java.util.ArrayList;

/**
 * Classe abstraite qui représente les contraintes MCD de type unique. Généralement ce sont 2 types de contraintes
 * uniques qui existent: Unique (<<U>>) (représentée par MCDUnique) et NID (<<NID>>) (MCDNID).
 */
public abstract class MCDUnicity extends MCDConstraint{

    private  static final long serialVersionUID = 1000;
    private boolean absolute = false;

    public MCDUnicity(ProjectElement parent) {
        super(parent);
    }

    public MCDUnicity(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MCDUnicity(ProjectElement parent, String name) {
        super(parent, name);
    }


    public abstract String getOfUnicity();


    public boolean isAbsolute() {
        return absolute;
    }

    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }

    public ArrayList<Constraint> getConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (absolute){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_ABSOLUTE_LIENPROG));
        }
        return resultat;
    }

    public abstract MDRUniqueNature getMDRUniqueNature();
}
