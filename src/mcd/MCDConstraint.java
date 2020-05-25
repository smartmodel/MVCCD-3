package mcd;

import constraints.Constraint;
import main.MVCCDElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import stereotypes.Stereotype;
import stereotypes.Stereotypes;
import stereotypes.StereotypesManager;

import java.util.ArrayList;

public abstract class MCDConstraint extends MCDOperation{

    private static final long serialVersionUID = 1000;

    public MCDConstraint(ProjectElement parent) {
        super(parent);
    }

    public MCDConstraint(ProjectElement parent, String name) {
        super(parent, name);
    }

    public abstract String getClassShortNameUI();

    public abstract ArrayList<Stereotype> getToStereotypes();

    public abstract ArrayList<Constraint> getToConstraints(); // Contraintes UML

    public ArrayList<MCDParameter> getMcdParameters() {
        ArrayList<MCDParameter> resultat = new ArrayList<MCDParameter>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MCDParameter) mvccdElement);
        }
        return resultat;
    }

}
