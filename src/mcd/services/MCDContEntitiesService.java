package mcd.services;

import mcd.MCDContEntities;
import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;

import java.util.ArrayList;

public class MCDContEntitiesService {


    public static ArrayList<MCDContEntities> getMCDContEntitiesInIModel(IMCDModel iMCDModel) {
        ArrayList<MCDContEntities> resultat =  new ArrayList<MCDContEntities>();
        for (MCDElement element :  IMCDModelService.getMCDElementsByClassName(
                iMCDModel, false,MCDContEntities.class.getName())){
        }
        return resultat;
    }

    public static MCDContEntities getMCDContEntitiesByNamePath(IMCDModel model,
                                                   int pathMode,
                                                   String namePath){
        return (MCDContEntities) IMCDModelService.getMCDElementByClassAndNamePath(model, false,
                MCDContEntities.class.getName(), pathMode, namePath);
    }

    public static ArrayList<MCDElement> toMCDElements(ArrayList<MCDContEntities> mcdConEntities) {
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        for (MCDContEntities aMCDContEntities : mcdConEntities){
            resultat.add(aMCDContEntities);
        }
        return resultat;
    }
}
