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
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty("entity.and.name");
        if (StringUtils.isEmpty(name)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[] {message1}));
        }
        if (!name.matches(Preferences.NAME_REGEXPR)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[] {message1, Preferences.NAME_REGEXPR}));
        }
        if (name.length() > Preferences.ENTITY_NAME_LENGTH){
            messages.add( MessagesBuilder.getMessagesProperty("editor.length.error"
                    , new String[] {message1, String.valueOf(Preferences.ENTITY_NAME_LENGTH)}));
        }
        return messages;
    }

}
