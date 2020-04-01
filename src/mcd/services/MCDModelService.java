package mcd.services;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContAttributes;
import mcd.MCDContEndRels;
import mcd.MCDEntity;
import mcd.MCDModel;
import messages.MessagesBuilder;
import preferences.Preferences;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCDModelService {


    public static ArrayList<String> check(MCDModel mcdModel) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.addAll(checkName(mcdModel.getName()));
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.MODEL_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "modele.and.name");

    }

    public static ArrayList<String> checkShortName(String name) {
        return MCDUtilService.checkString(name, true, Preferences.MODEL_SHORT_NAME_LENGTH,
                Preferences.NAME_REGEXPR, "modele.and.short.name");

    }


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
