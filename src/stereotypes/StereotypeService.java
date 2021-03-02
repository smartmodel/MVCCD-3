package stereotypes;

import main.MVCCDElementService;
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


    public static ArrayList<String> getUMLNamesBySterotypes(ArrayList<Stereotype> stereotypes){
        ArrayList<String> names = MVCCDElementService.convertArrayMVCCDElementsToNames(stereotypes);
        return getUMLNamesByNames(names);
    }


    public static ArrayList<String> getUMLNamesByNames(ArrayList<String> stereotypesNames){
        ArrayList<String> resultat = new ArrayList<String>();
        for (String name : stereotypesNames){
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
                UML_SYMBOLE_BEGIN_STEREOTYPE, UML_SYMBOLE_END_STEREOTYPE, withTag);
    }




}
