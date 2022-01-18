package mdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MDRTableNature {
    IND (Preferences.MCD_ENTITY_NATURE_IND),
    DEP(Preferences.MCD_ENTITY_NATURE_DEP),
    ASS(Preferences.MCD_ENTITY_NATURE_ENTASS),
    ASSDEP(Preferences.MCD_ENTITY_NATURE_ENTASSDEP),
    NAIRE(Preferences.MCD_ENTITY_NATURE_NAIRE),
    NAIREDEP(Preferences.MCD_ENTITY_NATURE_NAIREDEP),
    SPEC(Preferences.MCD_ENTITY_NATURE_SPEC);

    private final String name;

    MDRTableNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MDRTableNature findByText(String text){
        for (MDRTableNature element: MDRTableNature.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
