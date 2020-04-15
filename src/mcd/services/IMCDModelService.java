package mcd.services;

import main.MVCCDElement;
import mcd.MCDContEntities;
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
                                                                   boolean withModel,
                                                                   String className){

        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        if (withModel){
            /*
            if (model.getClass().getName().equals(className)){
                resultat.add((MCDElement) model);
            } else if (((MVCCDElement)model).implementsInterface(className)){
                resultat.add((MCDElement) model);
            }

             */
            if (((MVCCDElement)model).implementsInterface(IMCDModel.class.getName())) {
                resultat.add((MCDElement) model);
            }
        }
        resultat.addAll(getMCDElementsByClassNameInternal((MCDElement) model, className));
        return resultat;
    }


    private static  ArrayList<MCDElement> getMCDElementsByClassNameInternal(MCDElement root,
                                                                            String className){
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();

        for (MVCCDElement element :  root.getChilds()){
            if (element instanceof MCDElement){
                if (element.getClass().getName().equals(className)){
                    resultat.add((MCDElement) element);
                } else if (element.implementsInterface(className)){
                    resultat.add((MCDElement) element);
                }
                resultat.addAll(getMCDElementsByClassNameInternal((MCDElement) element, className));
            }
        }
        return resultat;
    }

    public static MCDElement getMCDElementByClassAndNamePath(IMCDModel model, boolean withModel, String className, int pathMode, String namePath){
        for (MCDElement mcdElement: getMCDElementsByClassName(model, withModel, className)){
            if (mcdElement.getNamePath(pathMode).equals(namePath)){
                return mcdElement;
            }
        }
        return null;
    }


}
