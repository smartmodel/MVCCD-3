package constraints;

import datatypes.MCDDatatype;
import datatypes.MDDatatypesManager;
import main.MVCCDElementService;
import stereotypes.Stereotype;
import utilities.UtilDivers;

import java.util.ArrayList;

public class ConstraintService {

    private static String UML_SYMBOLE_BEGIN_CONSTRAINT = "{";
    private static String UML_SYMBOLE_END_CONSTRAINT = "}";

    /*
    public static ArrayList<String> stereotypesNames(ArrayList<Stereotype> stereotypes){
        ArrayList<String> resultat = new ArrayList<String>();
        for (Stereotype stereotype : stereotypes){
            resultat.add(stereotype.getName());
        }
        return resultat ;
    }
*/
    public static String getUMLName(Constraint constraint){
        return getUMLName(constraint.getName());
    }

    public static String getUMLName(String constraintName){
        return  UML_SYMBOLE_BEGIN_CONSTRAINT + constraintName + UML_SYMBOLE_END_CONSTRAINT;
    }


    public static ArrayList<String> getUMLNamesByConstraints(ArrayList<Constraint> constraints){
        ArrayList<String> names = MVCCDElementService.convertArrayMVCCDElementsToNames(constraints);
        return getUMLNamesByNames(names);
    }


    public static ArrayList<String> getUMLNamesByNames(ArrayList<String> constraintsNames){
        ArrayList<String> resultat = new ArrayList<String>();
        for (String name : constraintsNames){
            resultat.add(getUMLName(name));
        }
        return resultat;
    }


    /*
    names : Liste de noms taggés
    withTag: Faut-il rajouter les tags aux noms
     */

    public static ArrayList<String> getArrayListFromNamesStringTagged(String names, boolean withTag ){
        return UtilDivers.getArrayListFromNamesStringTagged(names,
                UML_SYMBOLE_BEGIN_CONSTRAINT, UML_SYMBOLE_END_CONSTRAINT, withTag);
    }






}
