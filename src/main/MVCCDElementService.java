package main;

import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDTraceability;
import org.apache.commons.lang.StringUtils;
import project.ProjectElement;
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



    public static MVCCDElement nameExistInOthersChilds(MVCCDElement parent,
                                                       MVCCDElement child,
                                                       boolean deep,
                                                       String name,
                                                       boolean uppercase){
        return namingExistInOthersChilds(parent, child, deep, MVCCDElement.SCOPENAME, name, uppercase);
    }

    public static MVCCDElement shortNameExistInOthersChilds(MVCCDElement parent,
                                                       MVCCDElement child,
                                                       boolean deep,
                                                       String shortName,
                                                       boolean uppercase){
        return namingExistInOthersChilds(parent, child, deep, MVCCDElement.SCOPESHORTNAME, shortName, uppercase);
    }

    public static MVCCDElement longNameExistInOthersChilds(MVCCDElement parent,
                                                       MVCCDElement child,
                                                       boolean deep,
                                                       String longName,
                                                       boolean uppercase){
        return namingExistInOthersChilds(parent, child, deep, MVCCDElement.SCOPELONGNAME, longName, uppercase);
    }


    // Child peut être null lors de la saisie d'un nouvel enregistrement!
    private static MVCCDElement namingExistInOthersChilds(MVCCDElement parent,
                                                          MVCCDElement child,
                                                          boolean deep,
                                                          int namingScope,
                                                          String namingValue,
                                                          boolean uppercase){
        ArrayList<MVCCDElement> childs ;
        if (deep) {
            childs = parent.getDescendantsWithout(child);
        } else {
            childs = parent.getChildsWithout(child);
        }

        for (MVCCDElement aChild : childs) {
                String namingToCheck = UtilDivers.toNoFree(namingValue);
                String childNaming = "";
                if (namingScope == MVCCDElement.SCOPENAME) {
                    if (aChild.getName() != null) {
                       childNaming = UtilDivers.toNoFree(aChild.getNameId());
                    }
                }
                if (namingScope == MVCCDElement.SCOPESHORTNAME) {
                    if (aChild.getShortName() != null) {
                        childNaming = UtilDivers.toNoFree(aChild.getShortNameId());
                    }
                }
                if (namingScope == MVCCDElement.SCOPELONGNAME) {
                    if (aChild.getLongName() != null) {
                        childNaming = UtilDivers.toNoFree(aChild.getLongNameId());
                    }
                }
                if (uppercase) {
                    namingToCheck = namingToCheck.toUpperCase();
                    childNaming = childNaming.toUpperCase();
                }

                if (StringUtils.isNotEmpty(childNaming) && StringUtils.isNotEmpty(namingToCheck)) {
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
                                                     boolean deep,
                                                     String namingValue,
                                                     boolean uppercase){

        ArrayList<MVCCDElement> childs ;
        if (deep) {
            childs = parent.getDescendantsWithout(child);
        } else {
            childs = parent.getChildsWithout(child);
        }
        for (MVCCDElement aChild : childs) {
                String namingToCheck = UtilDivers.toNoFree(namingValue);
                String childNaming = UtilDivers.toNoFree(aChild.getNameId());
                if (uppercase) {
                    namingToCheck = namingToCheck.toUpperCase();
                    childNaming = childNaming.toUpperCase();
                }

                if (StringUtils.isNotEmpty(childNaming) && StringUtils.isNotEmpty(namingToCheck)) {
                    if (childNaming.equals(namingToCheck)) {
                        return aChild;
                    }
                }

        }
        return null;
    }

    //TODO-1 Reprendre le paramétrage de cette méthode et des 2 autres précédents pour passer
    // directement un ArrayList<MVCCDElement> childs
    public static MVCCDElement nameExistNamingInOthersChilds(MVCCDElement parent, MVCCDElement child,
                                                        boolean deep,
                                                        int scopeNaming, String namingValue, boolean uppercase) {
        ArrayList<MVCCDElement> childs ;
        if (deep) {
            childs = parent.getDescendantsWithout(child);
        } else {
            childs = parent.getChildsWithout(child);
        }
        for (MVCCDElement aChild : childs) {
            String namingToCheck = UtilDivers.toNoFree(namingValue);
                String childNaming = "";
                if (scopeNaming == MCDElement.SCOPESHORTNAME) {
                    childNaming = UtilDivers.toNoFree(aChild.getShortNameId());
                }
                if (scopeNaming == MCDElement.SCOPELONGNAME) {
                    childNaming = UtilDivers.toNoFree(aChild.getLongNameId());
                }
                if (uppercase) {
                    namingToCheck = namingToCheck.toUpperCase();
                    childNaming = childNaming.toUpperCase();
                }

                if (StringUtils.isNotEmpty(childNaming) && StringUtils.isNotEmpty(namingToCheck)) {
                    if (childNaming.equals(namingToCheck)) {
                        return aChild;
                    }
                }
        }
        return null;


    }
}
