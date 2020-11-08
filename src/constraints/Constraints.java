package constraints;

import exceptions.CodeApplException;
import main.MVCCDElement;
import messages.MessagesBuilder;

import java.util.ArrayList;

public class Constraints extends MVCCDElement {
    public Constraints(MVCCDElement parent) {
        super(parent);
    }

    public Constraints(MVCCDElement parent, String name) {
        super(parent, name);
    }



    public ArrayList<Constraint> getConstraintsByClassName(String className){
        ArrayList<Constraint> resultat = new  ArrayList<Constraint>();
        for (MVCCDElement mvccdElement : this.getChilds()){
            if (mvccdElement instanceof Constraint){
                Constraint constraint = (Constraint) mvccdElement;
                if (constraint.getClassTargetName().equals(className)){
                    resultat.add(constraint);
                }
            }

        }
        return resultat;
    }

    public Constraint getConstraintByLienProg(String className, String lienProg){
        for (Constraint constraint : getConstraintsByClassName(className)){
            if (constraint.getLienProg().equals(lienProg)){
                return constraint;
            }
        }
        String message = MessagesBuilder.getMessagesProperty("error.constraint.lienProg",
                new String[] {lienProg, className} );
        throw new CodeApplException(this.getClass().getName() + " - " + message);
 }

    public ArrayList<Constraint> getConstraintByClassNameAndNames(String className,
                                                                  ArrayList<String> names){
        ArrayList<Constraint> resultat = new ArrayList<Constraint>();
        ArrayList<Constraint> constraintsClassName = getConstraintsByClassName(className);
        for (Constraint constraint : constraintsClassName){
            for (String name : names){
                if (constraint.getName().equals(name)) {
                    resultat.add(constraint);
                }
            }
        }
        return resultat;
    }
}
