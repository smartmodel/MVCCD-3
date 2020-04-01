package mcd.services;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDModel;

import java.util.ArrayList;

public class IMCDModelService {
    public static IMCDModel getIModelContainer(MCDElement mcdElement) {
        IMCDModel resultat = null;
        if ( mcdElement instanceof IMCDModel){
            resultat = ((IMCDModel) mcdElement);
        } else {
            if (mcdElement.getParent() != null) {
                resultat = getIModelContainer((MCDElement) mcdElement.getParent());
            }
        }
        return resultat;
    }


    public static  ArrayList<MCDElement> getMCDElementsByClassName(IMCDModel model,
                                                                           String className){
        return getMCDElementsByClassNameInternal((MCDElement) model, className);

    }

    private static  ArrayList<MCDElement> getMCDElementsByClassNameInternal(MCDElement racine,
                                                                            String className){
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        for (MVCCDElement element :  racine.getChilds()){
            if (element instanceof MCDElement){
                if (element.getClass().getName().equals(className)){
                    resultat.add((MCDElement) element);
                }
                resultat.addAll(getMCDElementsByClassNameInternal((MCDElement) element, className));
            }
        }
        return resultat;
    }

    public static MCDElement getMCDElementByClassAndNamePath(IMCDModel model, String className, String namePath){
        for (MCDElement mcdElement: getMCDElementsByClassName(model, className)){
            if (mcdElement.getNamePath().equals(namePath)){
                return mcdElement;
            }
        }
        return null;
    }


}
