package mcd.services;

import mcd.MCDElement;
import mcd.MCDEntity;
import mcd.interfaces.IMCDModel;
import messages.MessagesBuilder;
import preferences.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDEntityService {


    public static ArrayList<String> check(MCDEntity mcdEntity) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.addAll(checkName(mcdEntity.getName()));
        messages.addAll(checkShortName(mcdEntity.getShortName()));
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.ENTITY_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "entity.and.name");

    }

    public static ArrayList<String> checkShortName(String shortName) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty("entity.and.shortname");

        MCDUtilService.isEmpty(messages, shortName, message1);
        return messages;
    }

    public static void sortNameAsc(ArrayList<MCDEntity> entities){

        Collections.sort(entities, NAME_ASC);
    }

    static final Comparator<MCDEntity> NAME_ASC =
            new Comparator<MCDEntity>() {
                public int compare(MCDEntity e1, MCDEntity e2) {
                    return e1.getName().compareTo(e2.getName());
                }
    };

    public static ArrayList<MCDEntity> getMCDEntitiesByClassName(IMCDModel iMCDModel,
                                                                 String className) {
        ArrayList<MCDEntity> resultat =  new ArrayList<MCDEntity>();
        for (MCDElement element :  IMCDModelService.getMCDElementsByClassName(
                iMCDModel, className)){
            resultat.add((MCDEntity) element);
        }
        return resultat;
    }

    public static MCDEntity getMCDEntityByNamePath(IMCDModel model,
                                                    String namePath){
        return (MCDEntity) IMCDModelService.getMCDElementByClassAndNamePath(model,
                MCDEntity.class.getName(), namePath);
    }






    /* Intégré direcement dans Factory
    public static void completeNew(MCDEntity mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        MCDContAttributes mcdContAttributes = MVCCDElementFactory.instance().createMCDAttributes(mcdEntity,
                Preferences.REPOSITORY_MCD_ATTRIBUTES_NAME);
        MCDContEndRels mcdContEndRels = MVCCDElementFactory.instance().createMCDRelations(mcdEntity,
                Preferences.REPOSITORY_MCD_RELATIONS_NAME);
    }

     */
}
