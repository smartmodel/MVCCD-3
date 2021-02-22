package mdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MDRNamingFormat {
    NOTHING (Preferences.MDR_NAMING_FORMAT_NOTHING),
    UPPERCASE (Preferences.MDR_NAMING_FORMAT_UPPERCASE),
    LOWERCASE (Preferences.MDR_NAMING_FORMAT_LOWERCASE),
    CAPITALIZE (Preferences.MDR_NAMING_FORMAT_CAPITALIZE);

    private final String name;

    MDRNamingFormat(String name) {
        this.name = name;
     }

    public String getName() {
        return name;
    }


    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MDRNamingFormat findByText(String text){
        for (MDRNamingFormat element: MDRNamingFormat.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }
}
