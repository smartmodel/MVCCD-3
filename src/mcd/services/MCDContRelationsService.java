package mcd.services;

import mcd.MCDContEntities;
import mcd.MCDContRelations;
import mcd.MCDElement;
import mcd.interfaces.IMCDModel;

import java.util.ArrayList;

public class MCDContRelationsService {


    public static ArrayList<MCDContRelations> getMCDContRelationsInIModel(IMCDModel iMCDModel) {
        ArrayList<MCDContRelations> resultat =  new ArrayList<MCDContRelations>();
        for (MCDElement element :  IMCDModelService.getMCDElementsByClassName(
                iMCDModel, false,MCDContRelations.class.getName())){
            resultat.add((MCDContRelations) element);

        }
        return resultat;
    }

    public static MCDContRelations getMCDContRelationsByNamePath(IMCDModel model,
                                                   int pathMode,
                                                   String namePath){
        return (MCDContRelations) IMCDModelService.getMCDElementByClassAndNamePath(model, false,
                MCDContRelations.class.getName(), pathMode, namePath);
    }

    public static ArrayList<MCDElement> toMCDElements(ArrayList<MCDContRelations> mcdConRelations) {
        ArrayList<MCDElement> resultat = new ArrayList<MCDElement>();
        for (MCDContRelations aMCDContRelations : mcdConRelations){
            resultat.add(aMCDContRelations);
        }
        return resultat;
    }
}
