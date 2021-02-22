package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;

import java.util.ArrayList;

public abstract class MCDUnicity extends MCDConstraint{

    private  static final long serialVersionUID = 1000;
    private boolean absolute = false;

    public MCDUnicity(ProjectElement parent) {
        super(parent);
    }

    public MCDUnicity(ProjectElement parent, String name) {
        super(parent, name);
    }

    public abstract Stereotype getDefaultStereotype();

    public abstract String getOfUnicity();


    public boolean isAbsolute() {
        return absolute;
    }

    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }

    @Override
    public ArrayList<Constraint> getToConstraints() {
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();

        Constraints constraints = ConstraintsManager.instance().constraints();
        Preferences preferences = PreferencesManager.instance().preferences();

        if (absolute){
            resultat.add(constraints.getConstraintByLienProg(this.getClass().getName(),
                    preferences.CONSTRAINT_ABSOLUTE_LIENPROG));
        }
        return resultat;
    }

}
