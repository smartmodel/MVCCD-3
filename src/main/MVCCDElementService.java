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

    public static ArrayList<MVCCDElement> getDescendants(MVCCDElement root) {
        ArrayList<MVCCDElement> resultat = new ArrayList<MVCCDElement>();
        for (MVCCDElement child : root.getChilds()){
                resultat.add(child);
                if (child.getChilds().size() > 0) {
                    resultat.addAll(getDescendants(child));
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

    public static MVCCDElement getBrotherByClassName (MVCCDElement mvccdElement, String className){
        for (MVCCDElement aBrother : getBrothers( mvccdElement)){
            if ( aBrother.getClass().getName().equals(className)){
               return aBrother;
            }
        }
        return null;
    }



    public static MVCCDElement nameExistInOthersChilds(ArrayList<MVCCDElement> brothers,
                                                       String name,
                                                       boolean uppercase){
        return namingExistInOthersBrothers(brothers, MVCCDElement.SCOPENAME, name, uppercase);
    }

    public static MVCCDElement shortNameExistInOthersChilds(ArrayList<MVCCDElement> brothers,
                                                       String shortName,
                                                       boolean uppercase){
        return namingExistInOthersBrothers(brothers, MVCCDElement.SCOPESHORTNAME, shortName, uppercase);
    }

    public static MVCCDElement longNameExistInOthersChilds(ArrayList<MVCCDElement> brothers,
                                                       String longName,
                                                       boolean uppercase){
        return namingExistInOthersBrothers(brothers, MVCCDElement.SCOPELONGNAME, longName, uppercase);
    }


    public static MVCCDElement namingExistInOthersBrothers(ArrayList<MVCCDElement> brothers,
                                                           int namingScope,
                                                           String namingValue,
                                                           boolean uppercase){

        for (MVCCDElement brother : brothers) {
            String namingToCheck = UtilDivers.toNoFree(namingValue);
            String childNaming = "";
            if (namingScope == MVCCDElement.SCOPENAME) {
                if (brother.getName() != null) {
                    childNaming = UtilDivers.toNoFree(brother.getNameId());
                }
            }
            if (namingScope == MVCCDElement.SCOPESHORTNAME) {
                if (brother.getShortName() != null) {
                    childNaming = UtilDivers.toNoFree(brother.getShortNameId());
                }
            }
            if (namingScope == MVCCDElement.SCOPELONGNAME) {
                if (brother.getLongName() != null) {
                    childNaming = UtilDivers.toNoFree(brother.getLongNameId());
                }
            }
            if (uppercase) {
                namingToCheck = namingToCheck.toUpperCase();
                childNaming = childNaming.toUpperCase();
            }

            if (StringUtils.isNotEmpty(childNaming) && StringUtils.isNotEmpty(namingToCheck)) {
                if (childNaming.equals(namingToCheck)) {
                    return brother;
                }
            }

        }

        return null;
    }

    // Vérifie qu'il n'y ait pas deux frères dont un porte le nom
    // utilisé par l'autre comme shortName ou longName

    public static MVCCDElement namingExistNameInOthersBrothers(ArrayList<MVCCDElement> brothers,
                                                             String namingValue,
                                                             boolean uppercase){

        for (MVCCDElement aBrother : brothers) {
            String namingToCheck = UtilDivers.toNoFree(namingValue);
            String childNaming = UtilDivers.toNoFree(aBrother.getNameId());
            if (uppercase) {
                namingToCheck = namingToCheck.toUpperCase();
                childNaming = childNaming.toUpperCase();
            }

            if (StringUtils.isNotEmpty(childNaming) && StringUtils.isNotEmpty(namingToCheck)) {
                if (childNaming.equals(namingToCheck)) {
                    return aBrother;
                }
            }

        }
        return null;
    }



    public static MVCCDElement nameExistNamingInOthersBrothers(ArrayList<MVCCDElement> brothers,
                                                               int scopeNaming, String namingValue,
                                                               boolean uppercase) {


        for (MVCCDElement aBrother : brothers) {
            String namingToCheck = UtilDivers.toNoFree(namingValue);
            String childNaming = "";
            if (scopeNaming == MCDElement.SCOPESHORTNAME) {
                childNaming = UtilDivers.toNoFree(aBrother.getShortNameId());
            }
            if (scopeNaming == MCDElement.SCOPELONGNAME) {
                childNaming = UtilDivers.toNoFree(aBrother.getLongNameId());
            }
            if (uppercase) {
                namingToCheck = namingToCheck.toUpperCase();
                childNaming = childNaming.toUpperCase();
            }

            if (StringUtils.isNotEmpty(childNaming) && StringUtils.isNotEmpty(namingToCheck)) {
                if (childNaming.equals(namingToCheck)) {
                    return aBrother;
                }
            }
        }
        return null;
    }


}
