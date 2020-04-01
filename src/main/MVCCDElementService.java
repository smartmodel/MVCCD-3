package main;

import mcd.MCDEntity;
import mcd.interfaces.IMCDTraceability;

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

    // Fratrie éléments et ses frères et soeurs
    public static ArrayList<MVCCDElement> getBrotherhood(MVCCDElement mvccdElement){
        ArrayList<MVCCDElement> resultat = new ArrayList<MVCCDElement>();
        if (mvccdElement.getParent() != null){
            for ( MVCCDElement child : mvccdElement.getParent().getChilds()){
                resultat.add(child);
            }
        }

        return resultat;
    }

    public static boolean nameExist(MVCCDElement mvccdElement, String name){
        for (MVCCDElement aMember : getBrotherhood(mvccdElement)){
            if (aMember.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

}
