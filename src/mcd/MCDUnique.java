package mcd;

import constraints.Constraint;
import constraints.Constraints;
import constraints.ConstraintsManager;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public class MCDUnique extends MCDUnicity{

    private  static final long serialVersionUID = 1000;
    public static final String CLASSSHORTNAMEUI = "Unique";


    private boolean absolute = false;

    public MCDUnique(ProjectElement parent) {

        super(parent);
    }
    public MCDUnique(ProjectElement parent, String name) {

        super(parent, name);
    }

    @Override
    public Stereotype getDefaultStereotype() {
        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();
        return stereotypes.getStereotypeByLienProg(MCDUnique.class.getName(),
                preferences.STEREOTYPE_U_LIENPROG,
                getOrderIndexInParentSameClass() + 1);
    }

    @Override
    public ArrayList<Stereotype> getToStereotypes() {
        ArrayList<Stereotype> resultat = new ArrayList<Stereotype>();

        Stereotypes stereotypes = StereotypesManager.instance().stereotypes();
        Preferences preferences = PreferencesManager.instance().preferences();

        resultat.add(getDefaultStereotype());

        return resultat;
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


    public boolean isAbsolute() {
        return absolute;
    }

    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }

    @Override
    public String getClassShortNameUI() {
        return CLASSSHORTNAMEUI;
    }
}
