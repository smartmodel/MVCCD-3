package mdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MDRUniqueNature {
    SIMPK(Preferences.MDR_UNIQUE_NATURE_SIMPK),
    SIMCP(Preferences.MDR_UNIQUE_NATURE_SIMCP),
    NID(Preferences.MDR_UNIQUE_NATURE_NID),
    UNIQUE (Preferences.MDR_UNIQUE_NATURE_UNIQUE),
    LP(Preferences.MDR_UNIQUE_NATURE_LP),
    FKMAXONECHILD(Preferences.MDR_UNIQUE_NATURE_FKMAXONECHILD);

    private final String name;

    MDRUniqueNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MDRUniqueNature findByText(String text){
        for (MDRUniqueNature element: MDRUniqueNature.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }


    public static int getIndex(MDRUniqueNature nature){
        int i = -1 ;
        for (MDRUniqueNature element: MDRUniqueNature.values()){
            i++;
            if (element == nature) {
                return i;
            }
        }
        return i;
    }

}
