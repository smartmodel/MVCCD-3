package constraints;

import exceptions.CodeApplException;
import m.MUMLExtensionNaming;
import main.MVCCDElementService;
import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;
import stereotypes.Stereotype;
import stereotypes.StereotypeService;
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



    public static String getUMLNamingInLine(ArrayList<Constraint> constraints){
        MUMLExtensionNaming mumlExtensionNaming =  PreferencesManager.instance().preferences().getGENERAL_M_UML_CONSTRAINT_NAMING_INLINE();
        return ConstraintService.getUMLNaming(constraints, mumlExtensionNaming);

    }

    public static String getUMLNamingInBox(ArrayList<Constraint> constraints){
        MUMLExtensionNaming mumlExtensionNaming =  PreferencesManager.instance().preferences().getGENERAL_M_UML_CONSTRAINT_NAMING_INBOX();
        return ConstraintService.getUMLNaming(constraints, mumlExtensionNaming);

    }

    public static String getUMLNaming(ArrayList<Constraint> constraints, MUMLExtensionNaming mNaming){
        if (mNaming == MUMLExtensionNaming.ONELINE_ONEMARKER) {
            return getUMLNamingOneLineOneMarker(constraints);
        }
        if (mNaming == MUMLExtensionNaming.ONELINE_MANYMARKER) {
            return getUMLNamingOneLineManyMarker(constraints);
        }
        if (mNaming == MUMLExtensionNaming.MANYLINE) {
            return getUMLNamingManyLine(constraints);
        }

        throw new CodeApplException("MUMLExtensionNaming passé en paramètre est inconnu");
    }

    private static String getUMLNamingOneLineOneMarker(ArrayList<Constraint> constraints){
        ArrayList<String> names = MVCCDElementService.convertArrayMVCCDElementsToNames(constraints);
        if (names.size() > 0) {
            return UML_SYMBOLE_BEGIN_CONSTRAINT + UtilDivers.arrayStringToString(names, ", ")
                    + UML_SYMBOLE_END_CONSTRAINT;
        } else {
            return null;
        }

    }

    private static String getUMLNamingOneLineManyMarker(ArrayList<Constraint> constraints){
        String resultat = "";
        for (Constraint constraint : constraints){
            resultat += getUMLName(constraint);
        }
        return resultat;
    }


    private static String getUMLNamingManyLine(ArrayList<Constraint> constraints){
        String resultat = "";
        for (Constraint constraint : constraints){
            if (StringUtils.isNotEmpty(resultat)) {
                resultat += System.lineSeparator();
            }
            resultat += getUMLName(constraint);
        }
        return resultat;
    }



    /*
    names : Liste de noms taggés
    withTag: Faut-il rajouter les tags aux noms
     */

    // A priori nécessaie (Pas utilisé!)
    public static ArrayList<String> getArrayListFromNamesStringTagged(String names, boolean withTag ){
        return UtilDivers.getArrayListFromNamesStringTagged(names,
                UML_SYMBOLE_BEGIN_CONSTRAINT, UML_SYMBOLE_END_CONSTRAINT, withTag);
    }






}
