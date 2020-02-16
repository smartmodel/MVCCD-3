package mcd.services;

import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;

import java.util.ArrayList;

public class MCDUtilService {

    public static void isEmpty(ArrayList<String> messages, String text,  String partMessage) {
        if (StringUtils.isEmpty(text)) {
            messages.add(MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[]{partMessage}));
        }
    }



    public static ArrayList<String> checkName(String name, Integer lengthMax, String contextMessage) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(contextMessage);
        if (StringUtils.isEmpty(name)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[] {message1}));
        }
        if (!name.matches(Preferences.NAME_REGEXPR)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[] {message1, Preferences.NAME_REGEXPR}));
        }
        if (name.length() > lengthMax){
            messages.add( MessagesBuilder.getMessagesProperty("editor.length.error"
                    , new String[] {message1, String.valueOf(lengthMax)}));
        }
        return messages;
    }

}
