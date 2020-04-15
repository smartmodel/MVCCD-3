package main;

import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDTraceability;
import org.apache.commons.lang.StringUtils;
import utilities.UtilDivers;

import java.util.ArrayList;

public class MVCCDElementService {

    public static ArrayList<MCDEntity> getAllEntities(MVCCDElement container){
        ArrayList<MCDEntity> resultat = new ArrayList<MCDEntity>();
        for (MVCCDElement mvccdElement : container.getChilds()){
            if (mvccdElement instanceof MCDEntity){
                resultat.add( (MCDEntity) mvccdElement);
            }
            if (mvccdElement.getChilds() != null){
                resultat.addAll(getAllEntities(mvccdElement));
            }
        }
        return resultat;
    }

    public static ArrayList<IMCDTraceability> getAllITraceabilities(MVCCDElement container) {
        ArrayList<IMCDTraceability> resultat = new ArrayList<IMCDTraceability>();
        for (MVCCDElement mvccdElement : container.getChilds()){
            if (mvccdElement instanceof IMCDTraceability){
                resultat.add( (IMCDTraceability) mvccdElement);
            }
            if (mvccdElement.getChilds() != null){
                resultat.addAll(getAllITraceabilities(mvccdElement));
            }
        }
        return resultat;
    }

    public static <T> ArrayList<String> convertArrayMVCCDElementsToNames (ArrayList<T> mvccdElements){
        ArrayList<String> resultat = new  ArrayList<String>();
        for (T mvccdElement : mvccdElements){
            resultat.add(((MVCCDElement) mvccdElement).getName());
        }
        return resultat;
    }

    //TODO-0 A reprendre en tenant compte du conteneur (ou du path)
    public static MVCCDElement getUniqueInstanceByClassName(MVCCDElement mvccdElementParent, String className) {
        MVCCDElement resultat = null;
        for (MVCCDElement mvccdElement : mvccdElementParent.getChilds()) {
            if (mvccdElement.getClass().getName().equals(className)) {
                resultat = mvccdElement;
            } else {
                if (resultat == null) {
                    resultat = getUniqueInstanceByClassName(mvccdElement, className);
                }
            }
        }
        return resultat;
    }

    // Frères
    public static ArrayList<MVCCDElement> getBrothers(MVCCDElement mvccdElement){
        ArrayList<MVCCDElement> resultat = new ArrayList<MVCCDElement>();
        if (mvccdElement.getParent() != null){
            for ( MVCCDElement child : mvccdElement.getParent().getChilds()){
                if (child != mvccdElement) {
                    resultat.add(child);
                }
            }
        }

        return resultat;
    }



    public static MVCCDElement nameExistInOthersChilds(MVCCDElement parent,
                                                  MVCCDElement child,
                                                  String name,
                                                  boolean uppercase){
        return namingExistInOthersChilds(parent, child, MVCCDElement.SCOPENAME, name, uppercase);
    }

    public static MVCCDElement shortNameExistInOthersChilds(MVCCDElement parent,
                                                       MVCCDElement child,
                                                       String shortName,
                                                       boolean uppercase){
        return namingExistInOthersChilds(parent, child, MVCCDElement.SCOPESHORTNAME, shortName, uppercase);
    }

    public static MVCCDElement longNameExistInOthersChilds(MVCCDElement parent,
                                                       MVCCDElement child,
                                                       String longName,
                                                       boolean uppercase){
        return namingExistInOthersChilds(parent, child, MVCCDElement.SCOPELONGNAME, longName, uppercase);
    }


    // Child peut être null lors de la saisie d'un nouvel enregistrement!
    private static MVCCDElement namingExistInOthersChilds(MVCCDElement parent,
                                                    MVCCDElement child,
                                                    int namingScope,
                                                    String namingValue,
                                                    boolean uppercase){

        for (MVCCDElement aChild : parent.getChildsWithout(child)){
            String namingToCheck =  UtilDivers.toNoFree(namingValue);
            String childNaming = "";
            if (namingScope == MVCCDElement.SCOPENAME){
                if (aChild.getName() != null) {
                    childNaming =  UtilDivers.toNoFree(aChild.getName());
                }           }
            if (namingScope == MVCCDElement.SCOPESHORTNAME){
                if (aChild.getShortName() != null) {
                    childNaming =  UtilDivers.toNoFree(aChild.getShortName());
                }
            }
            if (namingScope == MVCCDElement.SCOPELONGNAME){
                if (aChild.getLongName() != null) {
                    childNaming = UtilDivers.toNoFree(aChild.getLongName());
                }
            }
            if (uppercase){
                namingToCheck = namingToCheck.toUpperCase();
                childNaming = childNaming.toUpperCase();
            }

            if (StringUtils.isNotEmpty(childNaming)  && StringUtils.isNotEmpty(namingToCheck)) {
                if (childNaming.equals(namingToCheck)) {
                    return aChild;
                }
            }
        }
        return null;
    }


    // Vérifie qu'il n'y ait pas deux frères dont un porte le nom
    // utilisé par l'autre comme shortName ou longName

    public static MVCCDElement namingExistNameInOthersChilds(MVCCDElement parent,
                                                     MVCCDElement child,
                                                     String namingValue,
                                                     boolean uppercase){

        for (MVCCDElement aChild : parent.getChildsWithout(child)){
            String namingToCheck =  UtilDivers.toNoFree(namingValue);
            String childNaming =  UtilDivers.toNoFree(aChild.getName());
            if (uppercase){
                namingToCheck = namingToCheck.toUpperCase();
                childNaming = childNaming.toUpperCase();
            }

            if (StringUtils.isNotEmpty(childNaming)  && StringUtils.isNotEmpty(namingToCheck)) {
                if (childNaming.equals(namingToCheck)) {
                    return aChild;
                }
            }
        }
        return null;
    }

    public static MVCCDElement nameExistNamingInOthersChilds(MVCCDElement parent, MVCCDElement child,
                                                        int scopeNaming, String namingValue, boolean uppercase) {
        for (MVCCDElement aChild : parent.getChildsWithout(child)){
            String namingToCheck =  UtilDivers.toNoFree(namingValue);
            String childNaming = "";
            if (scopeNaming == MCDElement.SCOPESHORTNAME) {
                childNaming = UtilDivers.toNoFree(aChild.getShortName());
            }
            if (scopeNaming == MCDElement.SCOPELONGNAME) {
                childNaming = UtilDivers.toNoFree(aChild.getLongName());
            }
            if (uppercase){
                namingToCheck = namingToCheck.toUpperCase();
                childNaming = childNaming.toUpperCase();
            }

            if (StringUtils.isNotEmpty(childNaming)  && StringUtils.isNotEmpty(namingToCheck)) {
                if (childNaming.equals(namingToCheck)) {
                    return aChild;
                }
            }
        }
        return null;


    }
}
