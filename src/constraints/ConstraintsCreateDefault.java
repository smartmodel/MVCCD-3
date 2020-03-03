package constraints;

import mcd.MCDAttribute;
import preferences.Preferences;

public class ConstraintsCreateDefault {

    private Constraints constraints;

    public ConstraintsCreateDefault(Constraints constraints) {
        this.constraints = constraints;
    }

    public void create(){
        createContrainte(
                Preferences.CONSTRAINT_MCDATTRIBUTE_ORDERED_NAME,
                Preferences.CONSTRAINT_MCDATTRIBUTE_ORDERED_LIENPROG,
                MCDAttribute.class.getName());
    }


    private Constraint createContrainte(String name, String lienProg, String className){
        // Vérifier l'unicité  à faire !
        Constraint constraint = new Constraint(constraints,name, lienProg, className);
        return constraint;
    }

}
