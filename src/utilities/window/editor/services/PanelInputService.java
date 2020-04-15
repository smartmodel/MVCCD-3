package utilities.window.editor.services;

import main.MVCCDManager;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.scomponents.SComboBox;

public class PanelInputService {

    public static  void createComboBoxYesNoFree(SComboBox sComboBox) {
        String text = MessagesBuilder.getMessagesProperty(
                Preferences.OPTION_YES);
        sComboBox.addItem(text);
        text = MessagesBuilder.getMessagesProperty(
                Preferences.OPTION_FREE);
        sComboBox.addItem(text);
        text = MessagesBuilder.getMessagesProperty(
                Preferences.OPTION_NO);
        sComboBox.addItem(text);
    }

    public static  void createComboBoxYesNo(SComboBox sComboBox) {
        String text = MessagesBuilder.getMessagesProperty(
                Preferences.OPTION_YES);
        sComboBox.addItem(text);
        text = MessagesBuilder.getMessagesProperty(
                Preferences.OPTION_NO);
        sComboBox.addItem(text);
    }

    public static String prefComboBoxOption(SComboBox sComboBox){
        String text = (String) sComboBox.getSelectedItem();

        if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.OPTION_YES))){
            return Preferences.OPTION_YES;
        }
        if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.OPTION_NO))){
            return Preferences.OPTION_NO;
        }
        if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.OPTION_FREE))){
            return Preferences.OPTION_FREE;
        }
        if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.OPTION_ALWAYS))){
            return Preferences.OPTION_ALWAYS;
        }
        if (text.equals(MessagesBuilder.getMessagesProperty(Preferences.OPTION_NEVER))){
            return Preferences.OPTION_NEVER;
        }
        return null;
    }
}
