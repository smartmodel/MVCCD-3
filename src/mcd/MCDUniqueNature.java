package mcd;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MCDUniqueNature {
    NOID (Preferences.MCD_UNIQUE_NATURE_NO_ID),
    NID (Preferences.MCD_UNIQUE_NATURE_NID);


    private final String name;

    MCDUniqueNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MCDUniqueNature findByText(String text){
        for (MCDUniqueNature element: MCDUniqueNature.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
