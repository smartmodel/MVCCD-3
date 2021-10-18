package stereotypes;

import exceptions.CodeApplException;
import m.MUMLExtensionNaming;
import main.MVCCDElementService;
import org.apache.commons.lang.StringUtils;
import preferences.PreferencesManager;
import utilities.UtilDivers;

import java.util.ArrayList;

public class StereotypeService {

    private static String UML_SYMBOLE_BEGIN_STEREOTYPE = "«";
    private static String UML_SYMBOLE_END_STEREOTYPE = "»";

    /*
    public static ArrayList<String> stereotypesNames(ArrayList<Stereotype> stereotypes){
        ArrayList<String> resultat = new ArrayList<String>();
        for (Stereotype stereotype : stereotypes){
            resultat.add(stereotype.getName());
        }
        return resultat ;
    }
*/
    public static String getUMLName(Stereotype stereotype){
        return getUMLName(stereotype.getName());
    }

    public static String getUMLName(String stereotypeName){
        return  UML_SYMBOLE_BEGIN_STEREOTYPE + stereotypeName + UML_SYMBOLE_END_STEREOTYPE;
    }

    //#MAJ 2021-07-28 Changement réalisation IMWithStereotype
    // Renommage de la méthode
    public static ArrayList<String> getUMLNames(ArrayList<Stereotype> stereotypes){
        ArrayList<String> resultat = new ArrayList<String>();
        for (Stereotype stereotype : stereotypes){
            resultat.add(getUMLName(stereotype));
        }
        return resultat;
    }


    public static String getUMLNamingInLine(ArrayList<Stereotype> stereotypes){
        MUMLExtensionNaming mumlExtensionNaming =  PreferencesManager.instance().preferences().getGENERAL_M_UML_STEREOTYPE_NAMING_INLINE();
        return StereotypeService.getUMLNaming(stereotypes, mumlExtensionNaming);

    }


    public static String getUMLNamingInBox(ArrayList<Stereotype> stereotypes){
        MUMLExtensionNaming mumlExtensionNaming =  PreferencesManager.instance().preferences().getGENERAL_M_UML_STEREOTYPE_NAMING_INBOX();
        return StereotypeService.getUMLNaming(stereotypes, mumlExtensionNaming);

    }

    public static String getUMLNaming(ArrayList<Stereotype> stereotypes, MUMLExtensionNaming mNaming){
        if (mNaming == MUMLExtensionNaming.ONELINE_ONEMARKER) {
            return getUMLNamingOneLineOneMarker(stereotypes);
        }
        if (mNaming == MUMLExtensionNaming.ONELINE_MANYMARKER) {
            return getUMLNamingOneLineManyMarker(stereotypes);
        }
        if (mNaming == MUMLExtensionNaming.MANYLINE) {
            return getUMLNamingManyLine(stereotypes);
        }

        throw new CodeApplException("MUMLExtensionNaming passé en paramètre est inconnu");
    }

    private static String getUMLNamingOneLineOneMarker(ArrayList<Stereotype> stereotypes){
        ArrayList<String> names = MVCCDElementService.convertArrayMVCCDElementsToNames(stereotypes);
        if (names.size() > 0) {
            return UML_SYMBOLE_BEGIN_STEREOTYPE + UtilDivers.arrayStringToString(names, ", ")
                    + UML_SYMBOLE_END_STEREOTYPE;
        } else {
            return null;
        }

    }

    private static String getUMLNamingOneLineManyMarker(ArrayList<Stereotype> stereotypes){
        String resultat = "";
        for (Stereotype stereotype : stereotypes){
            resultat += getUMLName(stereotype);
        }
        return resultat;
    }


    private static String getUMLNamingManyLine(ArrayList<Stereotype> stereotypes){
        String resultat = "";
        for (Stereotype stereotype : stereotypes){
            if (StringUtils.isNotEmpty(resultat)) {
                resultat += System.lineSeparator();
            }
            resultat += getUMLName(stereotype);
        }
        return resultat;
    }



/*
    //#MAJ 2021-07-28 Changement réalisation IMWithStereotype
    // Renommage de la méthode
    public static ArrayList<String> getUMLNamesByNames(ArrayList<String> stereotypesNames){
        ArrayList<String> resultat = new ArrayList<String>();
        for (String name : stereotypesNames){
            resultat.add(getUMLName(name));
        }
        return resultat;
    }

 */


    /*
    names : Liste de noms taggés
    withTag: Faut-il rajouter les tags aux noms
     */

    public static ArrayList<String> getArrayListFromNamesStringTagged(String names, boolean withTag ){
        return UtilDivers.getArrayListFromNamesStringTagged(names,
                UML_SYMBOLE_BEGIN_STEREOTYPE, UML_SYMBOLE_END_STEREOTYPE, withTag);
    }




}
