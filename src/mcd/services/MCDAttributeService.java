package mcd.services;

import mcd.MCDAttribute;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import preferences.Preferences;

import java.util.ArrayList;

public class MCDAttributeService {


    public static ArrayList<String> check(MCDAttribute mcdAttribute) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.addAll(checkName(mcdAttribute.getName()));
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        return MCDUtilService.checkName(name, Preferences.ENTITY_NAME_LENGTH, "entity.and.name");
    }

}
