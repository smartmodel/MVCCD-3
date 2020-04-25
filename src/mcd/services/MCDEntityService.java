package mcd.services;

import mcd.MCDElement;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDEntityService {

/*
    public static ArrayList<String> checkInput(MCDEntity mcdEntity) {
        return MCDUtilService.checkNamingIdBatch(mcdEntity,
                PreferencesManager.instance().preferences().ENTITY_NAME_LENGTH,
                PreferencesManager.instance().preferences().ENTITY_SHORT_NAME_LENGTH,
                PreferencesManager.instance().preferences().ENTITY_SHORT_NAME_LENGTH);
    }


    public static ArrayList<String> checkCompliant(MCDEntity mcdEntity) {

        return checkInput(mcdEntity);
    }
*/

    public static void sortNameAsc(ArrayList<MCDEntity> entities){

        Collections.sort(entities, NAME_ASC);
    }

    static final Comparator<MCDEntity> NAME_ASC =
            new Comparator<MCDEntity>() {
                public int compare(MCDEntity e1, MCDEntity e2) {
                    return e1.getName().compareTo(e2.getName());
                }
    };

    public static ArrayList<MCDEntity> getMCDEntitiesInIModel(IMCDModel iMCDModel) {
        ArrayList<MCDEntity> resultat =  new ArrayList<MCDEntity>();
        for (MCDElement element :  IMCDModelService.getMCDElementsByClassName(
                iMCDModel, false, MCDEntity.class.getName())){
            resultat.add((MCDEntity) element);
        }
        return resultat;
    }

    public static MCDEntity getMCDEntityByNamePath(IMCDModel model,
                                                   int pathMode,
                                                    String namePath){
        return (MCDEntity) IMCDModelService.getMCDElementByClassAndNamePath(model, false,
                MCDEntity.class.getName(), pathMode, namePath);
    }


    public static MCDEntity getMCDEntityByNamePath(int pathMode, String namePath){
        for (MCDElement mcdElement : ProjectService.getAllMCDElementsByNamePath(pathMode, namePath)){
            if (mcdElement instanceof MCDEntity){
                return (MCDEntity) mcdElement;
            }
        }
        return null;
    }

}
