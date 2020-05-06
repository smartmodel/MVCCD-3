package mcd;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MCDAssociationNature {
    NOID (Preferences.MCD_ASSOCIATION_NATURE_NO_ID),
    IDCOMP (Preferences.MCD_ASSOCIATION_NATURE_ID_COMP),
    IDNATURAL (Preferences.MCD_ASSOCIATION_NATURE_ID_NATURAL),
    CP(Preferences.MCD_ASSOCIATION_NATURE_SIM_CP);

    private final String name;

    MCDAssociationNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MCDAssociationNature findByText(String text){
        for (MCDAssociationNature element: MCDAssociationNature.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
