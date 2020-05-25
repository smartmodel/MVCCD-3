package constraints;

import mcd.MCDAttribute;
import mcd.MCDUnique;
import preferences.Preferences;

public class ConstraintsCreateDefault {

    private Constraints constraints;

    public ConstraintsCreateDefault(Constraints constraints) {
        this.constraints = constraints;
    }

    public void create(){
        createConstraint(
                Preferences.CONSTRAINT_ORDERED_NAME,
                Preferences.CONSTRAINT_ORDERED_LIENPROG,
                MCDAttribute.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ABSOLUTE_NAME,
                Preferences.CONSTRAINT_ABSOLUTE_LIENPROG,
                MCDUnique.class.getName());
    }


    private Constraint createConstraint(String name, String lienProg, String className){
        // Vérifier l'unicité  à faire !
        Constraint constraint = new Constraint(constraints,name, lienProg, className);
        return constraint;
    }

}
