package mcd.services;

import mcd.MCDEntity;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.DialogMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
}
