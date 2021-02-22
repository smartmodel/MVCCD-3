package mdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MDRFKNature {
    NOID (Preferences.MCD_ASSOCIATION_NATURE_NO_ID),
    IDCOMP (Preferences.MCD_ASSOCIATION_NATURE_ID_COMP),
    IDNATURAL (Preferences.MCD_ASSOCIATION_NATURE_ID_NATURAL);

    private final String name;

    MDRFKNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MDRFKNature findByText(String text){
        for (MDRFKNature element: MDRFKNature.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
