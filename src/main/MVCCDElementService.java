package main;

import datatypes.MDDatatype;
import mcd.MCDEntity;

import java.util.ArrayList;
import java.util.Collections;

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




}
