package mcd.services;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MCDEntityNature {
    IND (Preferences.MCD_ENTITY_NATURE_IND),
    DEP(Preferences.MCD_ENTITY_NATURE_DEP),
    ENTASS(Preferences.MCD_ENTITY_NATURE_ENTASS),
    ENTASSDEP(Preferences.MCD_ENTITY_NATURE_ENTASSDEP),
    NAIRE(Preferences.MCD_ENTITY_NATURE_NAIRE),
    NAIREDEP(Preferences.MCD_ENTITY_NATURE_NAIREDEP),
    SPEC(Preferences.MCD_ENTITY_NATURE_SPEC),
    PSEUDOENTASS(Preferences.MCD_ENTITY_NATURE_PSEUDOENTASS);

    private final String name;

    MCDEntityNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MCDEntityNature findByText(String text){
        for (MCDEntityNature element: MCDEntityNature.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
