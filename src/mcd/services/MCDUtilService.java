package mcd.services;

import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;

import java.util.ArrayList;

public class MCDUtilService {

    public static void isEmpty(ArrayList<String> messages, String text,  String partMessage) {
        if (StringUtils.isEmpty(text)) {
            messages.add(MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                    , new String[]{partMessage}));
        }
    }



    public static ArrayList<String> checkString(String text,
                                                boolean mandatory,
                                                Integer lengthMax,
                                                String regularExpr,
                                                String contextMessage) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(contextMessage);
        Boolean error = false;

        if (!text.matches(regularExpr)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[] {message1, regularExpr}));
            error = true;
        }
        if (text.length() > lengthMax){
            messages.add( MessagesBuilder.getMessagesProperty("editor.length.error"
                    , new String[] {message1, String.valueOf(lengthMax)}));
            error = true;
        }

        if (mandatory ){
            if (StringUtils.isEmpty(text)  || error) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1}));
            }
        }
        return messages;
    }


    public static ArrayList<String> checkInteger(String integerInText,
                                                 boolean mandatory,
                                                 Integer min,
                                                 Integer max,
                                                 String contextMessage) {
        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(contextMessage);
        Boolean error = false;
        if (!integerInText.matches(Preferences.INTEGER_REGEXPR)){
            messages.add ( MessagesBuilder.getMessagesProperty("editor.format.error"
                    , new String[] {message1, Preferences.INTEGER_REGEXPR}));
            error = true;
        } else {
            Integer value = Integer.valueOf(integerInText);
            if (value < min){
                messages.add ( MessagesBuilder.getMessagesProperty("integer.min.error"
                        , new String[] {message1, "" + min}));
                error = true;
            }
            if (value > max){
                messages.add ( MessagesBuilder.getMessagesProperty("integer.max.error"
                        , new String[] {message1, "" + max}));
                error = true;
            }
        }

        if (mandatory){
            if (StringUtils.isEmpty(integerInText)  || error) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1}));
            }
        }
        return messages;
    }


    public static ArrayList<String> checkEmptyComboBox(SComboBox comboBox,
                                                       boolean mandatory,
                                                       String contextMessage) {

        ArrayList<String> messages = new ArrayList<String>();
        String message1 = MessagesBuilder.getMessagesProperty(contextMessage);

        if (mandatory){
            if (comboBox.isSelectedEmpty()) {
                messages.add(0, MessagesBuilder.getMessagesProperty("editor.mandatory.error"
                        , new String[]{message1}));
            }
        }
        return messages;
    }
}
