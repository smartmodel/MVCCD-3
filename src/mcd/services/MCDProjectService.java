package mcd.services;

import mcd.MCDEntity;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.Project;

import java.util.ArrayList;

public class MCDProjectService {


    public static ArrayList<String> check(Project project) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.addAll(checkName(project.getName()));
        return messages;
    }

    public static ArrayList<String> checkName(String name) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty("project.and.name");
        if (StringUtils.isEmpty(name)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[] {message1}));
        }
        if (!name.matches(Preferences.NAME_REGEXPR)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[] {message1, Preferences.NAME_REGEXPR}));
        }
        if (name.length() > Preferences.PROJECT_NAME_LENGTH){
            messages.add( MessagesBuilder.getMessagesProperty("editor.length.error"
                    , new String[] {message1, String.valueOf(Preferences.PROJECT_NAME_LENGTH)}));
        }
        return messages;
    }

}
