package mcd.services;

import main.MVCCDElement;
import mcd.*;
import mcd.interfaces.IMCDModel;
import mldr.MLDRModelDT;
import mldr.MLDRModelTI;
import project.ProjectService;
import utilities.Trace;

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


    public static boolean foundMCDElementInIModelByInstance(IMCDModel model, MCDElement mcdElement){
        for ( MCDElement aMCDElement : ProjectService.getMCDElements()){
            if (aMCDElement == mcdElement){
               return true;
            }
        }
        return false;
    }

    public static ArrayList<MCDEntity> getAllMCDEntitiesInIModel(IMCDModel model){
        ArrayList<MCDEntity> resultat = new ArrayList<MCDEntity>();
        for (MCDElement mcdElement : getMCDElementsByClassName(model, false, MCDEntity.class.getName())){
            resultat.add ((MCDEntity) mcdElement);
        }
        return resultat;
    }

    public static ArrayList<MCDRelation> getAllMCDRelationsInIModel(IMCDModel model){
        ArrayList<MCDRelation> resultat = new ArrayList<MCDRelation>();
        for (MCDElement mcdElement : getMCDElementsByClassName(model, false, MCDRelation.class.getName())){
            resultat.add ((MCDRelation) mcdElement);
        }
        return resultat;
    }

    public static ArrayList<MCDAssociation> getAllMCDAssociationsInIModel(IMCDModel model){
        ArrayList<MCDAssociation> resultat = new ArrayList<MCDAssociation>();
        for (MCDElement mcdElement : getMCDElementsByClassName(model, false, MCDAssociation.class.getName())){
            resultat.add ((MCDAssociation) mcdElement);
        }
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


    public static MCDElement getMCDElementByClassAndNameTree(IMCDModel model, boolean withModel, String className,String nameTree){
        for (MCDElement mcdElement: getMCDElementsByClassName(model, withModel, className)){
            if (mcdElement.getNameTree().equals(nameTree)){
                return mcdElement;
            }
        }
        return null;
    }


    public static MLDRModelDT getMLDRModelDT(IMCDModel imcdModel) {
        for ( MVCCDElement mvccdElement : imcdModel.getChilds()){
            if ( mvccdElement instanceof MLDRModelDT){
                return (MLDRModelDT) mvccdElement ;
            }
        }
        return null;
    }

    public static MLDRModelTI getMLDRModelTI(IMCDModel imcdModel) {
        for ( MVCCDElement mvccdElement : imcdModel.getChilds()){
            if ( mvccdElement instanceof MLDRModelTI){
                return (MLDRModelTI) mvccdElement ;
            }
        }
        return null;}

}
