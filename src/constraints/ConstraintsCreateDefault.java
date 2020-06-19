package constraints;

import mcd.MCDAssEnd;
import mcd.MCDAssociation;
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
                Preferences.CONSTRAINT_FROZEN_NAME,
                Preferences.CONSTRAINT_FROZEN_LIENPROG,
                MCDAttribute.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ORDERED_NAME,
                Preferences.CONSTRAINT_ORDERED_LIENPROG,
                MCDAssEnd.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_FROZEN_NAME,
                Preferences.CONSTRAINT_FROZEN_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_DELETECASCADE_NAME,
                Preferences.CONSTRAINT_DELETECASCADE_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ORIENTED_NAME,
                Preferences.CONSTRAINT_ORIENTED_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_NONORIENTED_NAME,
                Preferences.CONSTRAINT_NONORIENTED_LIENPROG,
                MCDAssociation.class.getName());

        createConstraint(
                Preferences.CONSTRAINT_ABSOLUTE_NAME,
                Preferences.CONSTRAINT_ABSOLUTE_LIENPROG,
                MCDUnique.class.getName());
    }


    private Constraint createConstraint(String name, String lienProg, String className){
        // Vérifier l'unicité  - A faire !
        Constraint constraint = new Constraint(constraints,name, lienProg, className);
        return constraint;
    }

}
