package mcd.services;

import m.MElement;
import mcd.*;
import preferences.Preferences;
import preferences.PreferencesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDModelService  {

/*
    public static ArrayList<String> checkInput(MCDModel mcdModel) {
        System.out.println("Check Input Model");
        return MCDUtilService.checkNamingIdBatch(mcdModel,
                PreferencesManager.instance().preferences().MODEL_NAME_LENGTH,
                PreferencesManager.instance().preferences().MODEL_SHORT_NAME_LENGTH,
                PreferencesManager.instance().preferences().MODEL_SHORT_NAME_LENGTH);


    }
    */


/*
    public static ArrayList<String> checkName(MCDContModels parent, MCDModel child, String name) {
        ArrayList<String> messages = MCDUtilService.checkString(name, true, Preferences.MODEL_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "modele.and.name");
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistNameInChilds(parent, child, name, true,"naming.brother.model"));
        }
        return messages;
    }

    public static ArrayList<String> checkShortName(MCDContModels parent, MCDModel child, String shortName) {
        ArrayList<String> messages = MCDUtilService.checkString(shortName, true, Preferences.MODEL_SHORT_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "modele.and.short.name");
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.checkExistShortNameInChilds(parent, child, shortName, true,"naming.brother.model"));
        }
        if (messages.size() == 0) {
            messages.addAll(MCDUtilService.namingExistNameInChilds(parent, child,
                    "naming.short.name",shortName, true,
                    "naming.brother.model"));
        }
        return messages;
    }
*/

    public static void sortNameAsc(ArrayList<MCDModel> models){
        Collections.sort(models, NAME_ASC);
    }

    static final Comparator<MCDModel> NAME_ASC =
            new Comparator<MCDModel>() {
                public int compare(MCDModel e1, MCDModel e2) {
                    return e1.getName().compareTo(e2.getName());
                }
    };


 }
