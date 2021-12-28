package mpdr;

import messages.MessagesBuilder;
import preferences.Preferences;

public enum MPDRDBPK {
    SEQUENCE (Preferences.DB_PK_SEQUENCE),
    IDENTITY (Preferences.DB_PK_IDENTITY);

    private final String name;

    MPDRDBPK(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return MessagesBuilder.getMessagesProperty(name);
    }

    public static MPDRDBPK findByText(String text){
        for (MPDRDBPK element: MPDRDBPK.values()){
            if (element.getText().equals(text)) {
                return element;
            }
        }
        return null;
    }

}
